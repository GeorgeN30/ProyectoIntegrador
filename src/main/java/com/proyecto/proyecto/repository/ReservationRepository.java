package com.proyecto.proyecto.repository;

import com.proyecto.proyecto.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT FUNCTION('YEAR', r.reservationDate), FUNCTION('MONTH', r.reservationDate), COUNT(r.id) " +
            "FROM Reservation r GROUP BY FUNCTION('YEAR', r.reservationDate), FUNCTION('MONTH', r.reservationDate) " +
            "ORDER BY FUNCTION('YEAR', r.reservationDate) ASC, FUNCTION('MONTH', r.reservationDate) ASC")
    List<Object[]> countReservationsByMonth();


    @Query("SELECT FUNCTION('YEAR', r.reservationDate), FUNCTION('MONTH', r.reservationDate), COUNT(r.id) " +
            "FROM Reservation r WHERE r.reservationDate BETWEEN :startDate AND :endDate " +
            "GROUP BY FUNCTION('YEAR', r.reservationDate), FUNCTION('MONTH', r.reservationDate) " +
            "ORDER BY FUNCTION('YEAR', r.reservationDate) ASC, FUNCTION('MONTH', r.reservationDate) ASC")
    List<Object[]> countReservationsByMonthInRange(LocalDate startDate, LocalDate endDate);

    @Query(value = """
        SELECT
            SUM(CASE WHEN YEAR(r.reservation_date) = YEAR(:currentMonthStart) AND MONTH(r.reservation_date) = MONTH(:currentMonthStart) THEN 1 ELSE 0 END) AS currentMonthCount,
            SUM(CASE WHEN YEAR(r.reservation_date) = YEAR(:previousMonthStart) AND MONTH(r.reservation_date) = MONTH(:previousMonthStart) THEN 1 ELSE 0 END) AS previousMonthCount
        FROM
            reservations r
        WHERE
            r.reservation_date BETWEEN :previousMonthStart AND :currentMonthEnd
        """, nativeQuery = true)
    List<Object[]> findCurrentAndPreviousMonthReservationsCounts(@Param("currentMonthStart") LocalDate currentMonthStart,
                                                                 @Param("previousMonthStart") LocalDate previousMonthStart,
                                                                 @Param("currentMonthEnd") LocalDate currentMonthEnd);

}
