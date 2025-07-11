package com.proyecto.proyecto.service;

import com.proyecto.proyecto.exception.BookNotFoundException;
import com.proyecto.proyecto.exception.ReservationNotFoundException;
import com.proyecto.proyecto.exception.ReservationStatusNotFoundException;
import com.proyecto.proyecto.exception.UserNotFoundException;
import com.proyecto.proyecto.mapper.ReservationMapper;
import com.proyecto.proyecto.model.dto.CreateReservationRequest;
import com.proyecto.proyecto.model.dto.ReservationResponse;
import com.proyecto.proyecto.model.entity.Book;
import com.proyecto.proyecto.model.entity.Reservation;
import com.proyecto.proyecto.model.entity.ReservationDetail;
import com.proyecto.proyecto.model.entity.ReservationStatus;
import com.proyecto.proyecto.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService{

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ReservationStatusRepository res;
    private final ReservationDetailRepository reservationDetailRepository;
    private final ReservationMapper reservationMapper;
    private final ReservationStatusRepository reservationStatusRepository;


    @Override
    public ReservationResponse save(CreateReservationRequest request) {
        return userRepository.findById(request.getUserId())
                .map(user -> res.findById(request.getReservationStatusId())
                        .map(reservationStatus -> {
                            // Creando la reservación principal
                            Reservation reservation = new Reservation();
                            reservation.setUser(user);
                            reservation.setReservationDate(LocalDate.now());
                            reservation.setReturnDate(request.getReturnDate());
                            reservation.setPickupDate(request.getPickupDate());
                            reservation.setReservationStatus(reservationStatus);
                            Reservation savedReservation = reservationRepository.save(reservation);
                            // Válida los libros y crea el reservationDetail
                            request.getBookId().forEach(bookId -> {
                                Book book = bookRepository.findById(bookId)
                                        .orElseThrow(BookNotFoundException::new);

                                ReservationDetail detail = new ReservationDetail();
                                detail.setBook(book);
                                detail.setReservation(reservation);
                                reservationDetailRepository.save(detail);
                            });
                            return savedReservation;
                        })
                        .orElseThrow(ReservationStatusNotFoundException::new))
                .map(reservationMapper::toReservationResponse)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll()
                .stream()
                .map(reservationMapper::toReservationResponse)
                .collect(Collectors.toList());
    }

    //metodo modificado para cancelar 
    @Override
    public void cancel(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new ReservationNotFoundException());

        ReservationStatus cancelledStatus = reservationStatusRepository.findById(5L) // ID de CANCELLED
            .orElseThrow(() -> new ReservationStatusNotFoundException());

        reservation.setReservationStatus(cancelledStatus);
        reservationRepository.save(reservation);
    }


    //metodo modificado para confirmar 
    @Override
    public void confirmed(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new ReservationNotFoundException());

        ReservationStatus cancelledStatus = reservationStatusRepository.findById(2L) // ID de Confirmed
            .orElseThrow(() -> new ReservationStatusNotFoundException());

        reservation.setReservationStatus(cancelledStatus);
        reservationRepository.save(reservation);
    }

}
