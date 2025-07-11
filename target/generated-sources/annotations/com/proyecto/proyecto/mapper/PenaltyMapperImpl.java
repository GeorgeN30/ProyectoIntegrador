package com.proyecto.proyecto.mapper;

import com.proyecto.proyecto.model.dto.CreatePenaltyRequest;
import com.proyecto.proyecto.model.dto.PenaltyResponse;
import com.proyecto.proyecto.model.entity.Penalty;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-11T17:31:04-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class PenaltyMapperImpl implements PenaltyMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public PenaltyResponse toPenaltyResponse(Penalty penalty) {
        if ( penalty == null ) {
            return null;
        }

        PenaltyResponse.PenaltyResponseBuilder penaltyResponse = PenaltyResponse.builder();

        penaltyResponse.amount( penalty.getAmount() );
        penaltyResponse.description( penalty.getDescription() );
        penaltyResponse.id( penalty.getId() );
        penaltyResponse.paid( penalty.getPaid() );
        penaltyResponse.reason( penalty.getReason() );
        penaltyResponse.user( userMapper.toUserResponse( penalty.getUser() ) );

        penaltyResponse.suspensionDate( mapFormatSuspensionDate(penalty) );
        penaltyResponse.suspensionEndDate( mapFormatSuspensionEndDate(penalty) );

        return penaltyResponse.build();
    }

    @Override
    public Penalty toPenalty(CreatePenaltyRequest request) {
        if ( request == null ) {
            return null;
        }

        Penalty penalty = new Penalty();

        penalty.setAmount( request.getAmount() );
        penalty.setDescription( request.getDescription() );
        penalty.setPaid( request.getPaid() );
        penalty.setReason( request.getReason() );
        if ( request.getSuspensionDate() != null ) {
            penalty.setSuspensionDate( LocalDate.parse( request.getSuspensionDate() ) );
        }
        if ( request.getSuspensionEndDate() != null ) {
            penalty.setSuspensionEndDate( LocalDate.parse( request.getSuspensionEndDate() ) );
        }

        return penalty;
    }
}
