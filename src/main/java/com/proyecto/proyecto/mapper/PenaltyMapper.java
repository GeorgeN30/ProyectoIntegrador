package com.proyecto.proyecto.mapper;

import com.proyecto.proyecto.model.dto.CreatePenaltyRequest;
import com.proyecto.proyecto.model.dto.PenaltyResponse;
import com.proyecto.proyecto.model.entity.Penalty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PenaltyMapper {

    @Mapping(target = "suspensionDate", expression = "java(mapFormatSuspensionDate(penalty))")
    @Mapping(target = "suspensionEndDate", expression = "java(mapFormatSuspensionEndDate(penalty))")
    PenaltyResponse toPenaltyResponse(Penalty penalty);

    Penalty toPenalty(CreatePenaltyRequest request);

    default String mapFormatSuspensionDate(Penalty penalty) {
        return penalty
                .getSuspensionDate()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    default String mapFormatSuspensionEndDate(Penalty penalty) {
        return penalty
                .getSuspensionEndDate()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

}
