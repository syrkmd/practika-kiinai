package com.yvl.vorstu.mapper;

import com.yvl.vorstu.dto.registrationInvitation.response.RegistrationInvitationSummaryResponse;
import com.yvl.vorstu.entities.RegistrationInvitation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegistrationInvitationMapper {

    @Mapping(target = "group", source = "group.name")
    RegistrationInvitationSummaryResponse toResponse(RegistrationInvitation invitation);
}
