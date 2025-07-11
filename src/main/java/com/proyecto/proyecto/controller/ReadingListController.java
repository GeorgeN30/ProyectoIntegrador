package com.proyecto.proyecto.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.proyecto.model.dto.BookResponse;
import com.proyecto.proyecto.model.dto.CreateReadingListRequest;
import com.proyecto.proyecto.model.dto.ReadingListResponse;
import com.proyecto.proyecto.service.ReadingListService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reading-list")
public class ReadingListController {

    private final ReadingListService readingListService;

    @PostMapping
    public ResponseEntity<ReadingListResponse> save(@Valid @RequestBody CreateReadingListRequest request) {
        ReadingListResponse readingList = readingListService.save(request);
        return ResponseEntity.created(URI.create("/api/reading-list/" + readingList.getId()))
                .body(readingList);
    }

    @GetMapping("/{id}")
    public ReadingListResponse findById(@PathVariable Long id) {
        return readingListService.findById(id);
    }

    @GetMapping
    public List<ReadingListResponse> findAll() {
        return readingListService.findAll();
    }

    @GetMapping("/user/{id}")
    public List<BookResponse> findAllByUserId(@PathVariable Long id) {
        return readingListService.findAllByUserId(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        readingListService.deleteById(id);
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteBooksFromReadingList(@RequestParam Long userId,
            @RequestBody List<Long> bookIds) {
        System.out.println("userId = " + userId);
        System.out.println("bookIds = " + bookIds);
        readingListService.deleteAllByUserIdAndBookIds(userId, bookIds);
        return ResponseEntity.ok().build();
    }

}
