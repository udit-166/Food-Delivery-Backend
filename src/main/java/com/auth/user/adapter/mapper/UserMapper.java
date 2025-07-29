package com.auth.user.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.auth.user.core.entity.User;
import com.auth.user.core.model.UserDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface UserMapper {
	 UserDto entityToDto(User user);
	 User dtoToEntity(UserDto userDto);
}
