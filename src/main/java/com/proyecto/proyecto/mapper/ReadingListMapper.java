package com.proyecto.proyecto.mapper;

import com.proyecto.proyecto.model.dto.CreateReadingListRequest;
import com.proyecto.proyecto.model.dto.ReadingListResponse;
import com.proyecto.proyecto.model.entity.ReadingList;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, BookMapper.class})
public interface ReadingListMapper {

    ReadingListResponse toReadingListResponse(ReadingList readingList);

    ReadingList toReadingList(CreateReadingListRequest request);
}
