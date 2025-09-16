package com.xyz.threatre.aggregator.repositories;

import com.xyz.threatre.aggregator.entities.Movie;
import com.xyz.threatre.aggregator.entities.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ShowRepository extends JpaRepository<Show, Long> {

    @Query(value = "select time from shows where date = :date and movie_id = :movieId and theater_id = :theaterId" , nativeQuery = true)
    public List<Time> getShowTimingsOnDate(@Param("date") Date date, @Param("theaterId")Integer theaterId, @Param("movieId")Integer movieId);

    @Query(value = "select movie_id from shows group by movie_id order by count(*) desc limit 1" , nativeQuery = true)
    public Integer getMostShowsMovie();

    @Query(value = "select * from shows where movie_id = :movieId" , nativeQuery = true)
    public List<Show> getAllShowsOfMovie(@Param("movieId")Integer movieId);

    @Query(value = "select s.movie from SHOW s where lower(s.room.theater.city)=lower(:city)")
    List<Movie> findByCityIgnoreCase(String city);

    @Query("""
            select s.* from Show s where
            lower(s.room.theatre.city = lower(:city)
            and lower(s.movie.id) = lower(:movieId) 
             and date(s.showTime) = :date """
    )
    List<Show> findShowByMovieCityAndDate(String city, String movieName, LocalDate localDate);

    Optional<Show> findByRoom_Threatre_IdAndShowTime(Long theatreId, LocalDateTime showTime);

    List<Show> findByRoom_Theatre_IdAndShowTimeBetween(Long theatreId, LocalDateTime dayStart, LocalDateTime dayEnd);

}
