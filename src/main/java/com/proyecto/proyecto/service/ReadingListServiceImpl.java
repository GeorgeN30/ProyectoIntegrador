package com.proyecto.proyecto.service;

import com.proyecto.proyecto.exception.BookNotFoundException;
import com.proyecto.proyecto.exception.ReadingListNotFound;
import com.proyecto.proyecto.exception.UserNotFoundException;
import com.proyecto.proyecto.mapper.BookMapper;
import com.proyecto.proyecto.mapper.ReadingListMapper;
import com.proyecto.proyecto.model.dto.BookResponse;
import com.proyecto.proyecto.model.dto.CreateReadingListRequest;
import com.proyecto.proyecto.model.dto.ReadingListResponse;
import com.proyecto.proyecto.model.entity.ReadingList;
import com.proyecto.proyecto.repository.BookRepository;
import com.proyecto.proyecto.repository.ReadingListRepository;
import com.proyecto.proyecto.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReadingListServiceImpl implements ReadingListService {

    private final ReadingListRepository repository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ReadingListMapper mapper;
    private final BookMapper bookMapper;
    private final ReadingListRepository readingListRepository;

    @Override
    public ReadingListResponse save(CreateReadingListRequest request) {
        return userRepository.findById(request.getUserId())
                .map(user -> bookRepository.findById(request.getBookId())
                        .map(book -> {
                            ReadingList readingList = new ReadingList();
                            readingList.setUser(user);
                            readingList.setBook(book);
                            return repository.save(readingList);
                        })
                        .orElseThrow(BookNotFoundException::new))
                .map(mapper::toReadingListResponse)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public ReadingListResponse findById(Long id) {
        return readingListRepository.findById(id)
                .map(mapper::toReadingListResponse)
                .orElseThrow(ReadingListNotFound::new);
    }

    @Override
    public List<ReadingListResponse> findAll() {
        return readingListRepository.findAll()
                .stream()
                .map(mapper::toReadingListResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> findAllByUserId(Long userId) {
        return userRepository.findById(userId)
                .map(user -> readingListRepository.findAllByUser_Id(userId))
                .map(readingLists -> readingLists.stream()
                        .map(ReadingList::getBook)
                        .map(bookMapper::toBookResponse)
                        .collect(Collectors.toList()))
                .orElseThrow(UserNotFoundException::new);

    }

    @Override
    public void deleteById(Long id) {
        if (readingListRepository.findById(id).isEmpty()) {
            throw new ReadingListNotFound();
        }
        readingListRepository.deleteById(id);
    }

    @Override
    public void deleteAllByUserIdAndBookIds(Long userId, List<Long> bookIds) {
        List<ReadingList> items = readingListRepository.findAllByUser_Id(userId);

        List<ReadingList> toDelete = items.stream()
                .filter(item -> bookIds.contains(item.getBook().getId()))
                .collect(Collectors.toList());

        readingListRepository.deleteAll(toDelete);
    }

}
