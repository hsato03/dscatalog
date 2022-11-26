package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.RoleDTO;
import com.devsuperior.dscatalog.dto.UserDTO;
import com.devsuperior.dscatalog.entities.Role;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.exceptions.DatabaseException;
import com.devsuperior.dscatalog.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.repositories.RoleRepository;
import com.devsuperior.dscatalog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageable) {
        Page<User> result = repository.findAll(pageable);
        return result.map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Optional<User> obj = repository.findById(id);
        return new UserDTO(obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id)));
    }

    @Transactional
    public UserDTO insert(UserDTO dto) {
        User obj = new User();
        copyDtoToEntity(dto, obj);
        obj = repository.save(obj);
        return new UserDTO(obj);
    }

    @Transactional
    public UserDTO update(Long id, UserDTO dto) {
        User obj = repository.getReferenceById(id);
        copyDtoToEntity(dto, obj);
        obj = repository.save(obj);
        return new UserDTO(obj);
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Data integrity violation");
        }
    }

    public void copyDtoToEntity(UserDTO dto, User obj) {
        obj.setFirstName(dto.getFirstName());
        obj.setLastName(dto.getLastName());
        obj.setEmail(dto.getEmail());

        obj.getRoles().clear();
        for (RoleDTO roleDto : dto.getRoles()) {
            Role role = roleRepository.getReferenceById(roleDto.getId());
            obj.getRoles().add(role);
        }
    }
}
