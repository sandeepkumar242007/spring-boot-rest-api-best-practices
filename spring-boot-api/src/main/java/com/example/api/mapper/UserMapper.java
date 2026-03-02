package com.example.api.mapper;

import com.example.api.dto.CreateUserRequest;
import com.example.api.dto.UpdateUserRequest;
import com.example.api.dto.UserDTO;
import com.example.api.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {
    
    UserDTO toDTO(User user);
    
    User toEntity(CreateUserRequest request);
    
    void updateEntityFromRequest(UpdateUserRequest request, @MappingTarget User user);
}
