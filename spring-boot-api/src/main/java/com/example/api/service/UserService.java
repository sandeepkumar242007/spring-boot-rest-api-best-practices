package com.example.api.service;

import com.example.api.dto.CreateUserRequest;
import com.example.api.dto.UpdateUserRequest;
import com.example.api.dto.UserDTO;
import com.example.api.exception.DuplicateEmailException;
import com.example.api.exception.UserNotFoundException;
import com.example.api.mapper.UserMapper;
import com.example.api.model.User;
import com.example.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllUsersPaginated(Pageable pageable) {
        log.info("Fetching users with pagination: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return userRepository.findAll(pageable).map(userMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        log.info("Fetching user with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toDTO(user);
    }

    public UserDTO createUser(CreateUserRequest request) {
        log.info("Creating user with email: {}", request.getEmail());
        
        User user = userMapper.toEntity(request);
        User savedUser = userRepository.save(user);
        log.info("User created successfully with id: {}", savedUser.getId());
        return userMapper.toDTO(savedUser);
    }

    public UserDTO updateUser(Long id, UpdateUserRequest request) {
        log.info("Updating user with id: {}", id);
        
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        
        if (!existingUser.getEmail().equals(request.getEmail()) && 
            userRepository.existsByEmail(request.getEmail())) {
            log.warn("Attempt to update user with duplicate email: {}", request.getEmail());
            throw new DuplicateEmailException(request.getEmail());
        }
        
        userMapper.updateEntityFromRequest(request, existingUser);
        User updatedUser = userRepository.save(existingUser);
        log.info("User updated successfully with id: {}", id);
        return userMapper.toDTO(updatedUser);
    }

    public void deleteUser(Long id) {
        log.info("Deleting user with id: {}", id);
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        log.info("User deleted successfully with id: {}", id);
    }
}
