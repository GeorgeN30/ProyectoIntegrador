
package com.proyecto.proyecto.service;

import com.proyecto.proyecto.model.dto.BookResponse;
import com.proyecto.proyecto.model.dto.CreateReadingListRequest;
import com.proyecto.proyecto.model.dto.ReadingListResponse;

import java.util.List;

public interface ReadingListService {

    ReadingListResponse save(CreateReadingListRequest request);
    ReadingListResponse findById(Long id);
    List<ReadingListResponse> findAll();
    List<BookResponse> findAllByUserId(Long userId);
    void deleteById(Long id);



    void deleteAllByUserIdAndBookIds(Long userId, List<Long> bookIds);

}
