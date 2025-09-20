package com.xyz.threatre.aggregator.service;

import com.xyz.threatre.aggregator.categories.TicketStatus;
import com.xyz.threatre.aggregator.command.BookTicketCommand;
import com.xyz.threatre.aggregator.dto.BookingResponseDTO;
import com.xyz.threatre.aggregator.dto.TicketBookingRequestDTO;
import com.xyz.threatre.aggregator.entities.Show;
import com.xyz.threatre.aggregator.entities.Seat;
import com.xyz.threatre.aggregator.entities.Ticket;
import com.xyz.threatre.aggregator.exceptions.ShowNotFound;
import com.xyz.threatre.aggregator.offer.OfferEngine;
import com.xyz.threatre.aggregator.repositories.SeatRepository;
import com.xyz.threatre.aggregator.repositories.ShowRepository;
import com.xyz.threatre.aggregator.repositories.TicketRepository;
import com.xyz.threatre.aggregator.response.BookingResult;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class TicketBookingServiceImpl implements TicketBookingService{
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;
    private final OfferEngine offerEngine;
    private final ExecutorService executors = Executors.newFixedThreadPool(10);
    // Simulated in-memory lock per seat for atomicity (use distributed lock 1
    private final Map<String, ReentrantLock> seatLocks = new HashMap<>();

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private final RestTemplate restTemplate = new RestTemplate();

    public TicketBookingServiceImpl(ShowRepository showRepository, SeatRepository seatRepository, TicketRepository ticketRepository, OfferEngine offerEngine) {
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
        this.ticketRepository = ticketRepository;
        this.offerEngine = offerEngine;
    }

    @Override
    public BookingResponseDTO bookTickets(TicketBookingRequestDTO request) {

      // 1. Try to lock all requested seats atomically
        List<ReentrantLock> locks = new ArrayList<>();
        try{
            for (String seat : request.getSeatNumbers()) {
                seatLocks.putIfAbsent(seat, new ReentrantLock());
                ReentrantLock lock = seatLocks.get(seat);
                if (!lock.tryLock()) {
                    return fail("Seat " + seat + " is being booked by someone else ");
                }
                locks.add(lock);
            }
    // 2. Check seat availability (including OTC bookings)
            boolean handledExternally = isTheatreHandledByExternalApi(request.getTheaterId());
            boolean available;
            if (handledExternally) {
                available =checkAndBookViaExternalApi(request);
            } else {
                available = checkAndBookViaEvent(request);
            }
            if (!available) {
                return fail("One or more seats are already booked (including OTC)");
            }
      // 3. Success
            BookingResponseDTO response = BookingResponseDTO.builder()
                    .bookedSeats(request.getSeatNumbers())
                    .success(true)
                    .message("Booking successful!")
                    .build();
            return  response;
        }
   finally {
    // Release all locks
                for (ReentrantLock lock : locks) {
                    if (lock.isHeldByCurrentThread()) lock.unlock();
                }
        }
    }

    private BookingResponseDTO fail(String msg){
        BookingResponseDTO responseDTO = new BookingResponseDTO(false,msg,Collections.emptyList());
        return responseDTO;
    }

    private boolean checkAndBookViaEvent(TicketBookingRequestDTO request) {
     // Publish booking request event
        String eventKey = UUID.randomUUID().toString();
        kafkaTemplate.send("ticket-booking-requests", eventKey, request.getTheaterId());
     // In a real system, you would have a consumer that processes the event,
     // Here, simulate immediaty success for demo:
        return true;
    }

    private boolean isTheatreHandledByExternalApi(String theatreId) {
     // Example: check from config/db
        return theatreId.startsWith("EXT");

    }

    // Simulate external API call

    private boolean checkAndBookViaExternalApi(TicketBookingRequestDTO request) {
        String apiUrl = "https://external-theatre.com/api/book";
        try {
          // The external API should atomically check seat availability (inclu
            Map<String, Object> body = new HashMap<>();
            body.put("theatreId", request.getTheaterId());
            body.put("date", request.getDate().toString());
            body.put("showTime", request.getShowTime().toString());
            body.put("seatNumbers", request.getSeatNumbers());
            body.put("customerName", request.getCustomerName());
            Map response = restTemplate.postForObject(apiUrl, body, Map.class);
            return response != null && Boolean.TRUE.equals(response.get("success"));
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public Ticket bookTicket(Long theatreId, LocalDateTime showTime, List<Long> preferredSeatsIds, String customerName, String customerEmail){
      Show show = showRepository.findByRoom_Threatre_IdAndShowTime(theatreId,showTime)
              .orElseThrow(() -> new ShowNotFound());
     List<Seat> seats = seatRepository.findAllById(preferredSeatsIds);
      Ticket ticket = new Ticket();
      ticket.setShow(show);
      ticket.setCustomerName(customerName);
      ticket.setSeat(seats.get(0));
      ticket.setStatus(TicketStatus.BOOKED);
      ticketRepository.save(ticket);
      return ticket;
    }

    public List<Ticket> bookTickets(Long showId, List<Long> seatIds, String customerName, String customerEmail) throws InterruptedException, ExecutionException {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ShowNotFound());
        List<Seat> seats = seatRepository.findAllById(seatIds);
        List<Future<Ticket>> futures = new ArrayList<>();

        List<Ticket> bookedTickets = Collections.synchronizedList(new ArrayList<>());
        for (Seat seat : seats) {
            BookTicketCommand command = new BookTicketCommand(
                    show, seat, customerName, customerEmail, ticketRepository,
                    ticket -> {
                        if (ticket != null) bookedTickets.add(ticket);
                    });
            futures.add(executors.submit(command, null));
        }
        // Wait for all bookings to finish
        for (Future<Ticket> future: futures) {
            future.get();
        }
        if (bookedTickets.size() != seatIds.size()) {
            throw new RuntimeException("Some seats could not be booked (already taken).");
        }
        return bookedTickets;
    }

    public BookingResult bookTicketsWithOffers (Long showId, List<Long> seatIds, String customerName, String customerEmail, String city, String theatre){
    Show show = showRepository.findById(showId)
            .orElseThrow(ShowNotFound::new);
        List<Seat> seats = seatRepository.findAllById(seatIds);
//        Check all seats exist
        if (seats.size() != seatIds.size()) {
            throw new RuntimeException("Some requested seats do not exist.");
        }
// Check all seats are available
        for (Seat seat: seats) {
            if (ticketRepository.findByShowAndSeat(show, seat).isPresent()) {
                throw new RuntimeException("One or more requested seats are already booked.");
            }
        }
            List<Ticket> tickets = seats.stream().map(seat -> {
                Ticket ticket = new Ticket();
                ticket.setShow(show);
                ticket.setSeat(seat);
                ticket.setBookingTime(java.time.LocalDateTime.now());
                ticket.setCustomerName(customerName);
                ticket.setCustomerEmail(customerEmail);
                ticket.setStatus(TicketStatus.BOOKED);
                // Set price if needed, e.g., ticket,setPrice(seat.getSeatType().getRice()
                return ticket;
            }).collect(java.util.stream.Collectors.toList());
            tickets = ticketRepository.saveAll(tickets);
            BigDecimal totalDiscount = offerEngine.applyOffers(tickets, show, city, theatre);
            BigDecimal totalPrice = tickets.stream().map(Ticket::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal finalPrice = totalPrice.subtract(totalDiscount);
            return new BookingResult(tickets, totalPrice, totalDiscount, finalPrice);
        }

    public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}
