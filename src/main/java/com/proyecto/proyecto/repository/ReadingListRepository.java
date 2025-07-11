package com.proyecto.proyecto.repository;

import com.proyecto.proyecto.model.entity.ReadingList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReadingListRepository extends JpaRepository<ReadingList, Long> {

    List<ReadingList> findAllByUser_Id(Long userId);
}
