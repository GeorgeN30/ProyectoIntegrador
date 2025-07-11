package com.proyecto.proyecto.service;

import com.proyecto.proyecto.exception.BookNotFoundException;
import com.proyecto.proyecto.exception.ReservationNotFoundException;
import com.proyecto.proyecto.mapper.BookMapper;
import com.proyecto.proyecto.mapper.ReservationDetailMapper;
import com.proyecto.proyecto.model.dto.BookResponse;
import com.proyecto.proyecto.model.dto.CreateReservationDetailRequest;
import com.proyecto.proyecto.model.dto.ReservationDetailResponse;
import com.proyecto.proyecto.model.entity.ReservationDetail;
import com.proyecto.proyecto.repository.BookRepository;
import com.proyecto.proyecto.repository.ReservationDetailRepository;
import com.proyecto.proyecto.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationDetailServiceImpl implements ReservationDetailService {

    private final ReservationDetailRepository reservationDetailRepository;
    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;
    private final ReservationDetailMapper reservationDetailMapper;
    private final BookMapper bookMapper;


    @Override
    public ReservationDetailResponse save(CreateReservationDetailRequest request) {
        return bookRepository.findById(request.getBookId())
                .map(book -> reservationRepository.findById(request.getReservationId())
                        .map(reservation -> {
                            ReservationDetail reservationDetail = new ReservationDetail();
                            reservationDetail.setBook(book);
                            reservationDetail.setReservation(reservation);
                            return reservationDetailRepository.save(reservationDetail);
                        })
                        .orElseThrow(ReservationNotFoundException::new))
                .map(reservationDetailMapper::toReservationDetailResponse)
                .orElseThrow(BookNotFoundException::new);
    }

    @Override
    public List<BookResponse> findAllByReservationId(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .map(reservation -> reservationDetailRepository
                        .findAllByReservation_Id(reservationId))
                .map(reservationDetails -> reservationDetails.stream()
                        .map(ReservationDetail::getBook)
                        .map(bookMapper::toBookResponse)
                        .collect(Collectors.toList()))
                .orElseThrow(ReservationNotFoundException::new);
    }

    @Override
    public List<ReservationDetailResponse> findAll() {
        return reservationDetailRepository.findAll()
                .stream()
                .map(reservationDetailMapper::toReservationDetailResponse)
                .collect(Collectors.toList());
    }
}
