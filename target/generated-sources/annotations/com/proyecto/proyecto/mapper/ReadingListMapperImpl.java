package com.proyecto.proyecto.mapper;

import com.proyecto.proyecto.model.dto.CreateReadingListRequest;
import com.proyecto.proyecto.model.dto.ReadingListResponse;
import com.proyecto.proyecto.model.entity.ReadingList;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-11T12:43:08-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class ReadingListMapperImpl implements ReadingListMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ReadingListResponse toReadingListResponse(ReadingList readingList) {
        if ( readingList == null ) {
            return null;
        }

        ReadingListResponse.ReadingListResponseBuilder readingListResponse = ReadingListResponse.builder();

        readingListResponse.book( readingList.getBook() );
        readingListResponse.id( readingList.getId() );
        readingListResponse.user( userMapper.toUserResponse( readingList.getUser() ) );

        return readingListResponse.build();
    }

    @Override
    public ReadingList toReadingList(CreateReadingListRequest request) {
        if ( request == null ) {
            return null;
        }

        ReadingList readingList = new ReadingList();

        return readingList;
    }
}
