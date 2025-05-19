package com.auth.user.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import com.auth.user.core.entity.User;
import com.auth.user.core.model.UserDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
	 UserDto entityToDto(User user);
	 User dtoToEntity(UserDto userDto);
}
