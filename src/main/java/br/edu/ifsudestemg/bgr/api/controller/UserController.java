package br.edu.ifsudestemg.bgr.api.controller;

import br.edu.ifsudestemg.bgr.api.dto.request.UserRequestDTO;
import br.edu.ifsudestemg.bgr.api.dto.response.UserResponseDTO;
import br.edu.ifsudestemg.bgr.exception.BusinessRuleException;
import br.edu.ifsudestemg.bgr.model.entity.User;
import br.edu.ifsudestemg.bgr.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity createUser(@Valid @RequestBody UserRequestDTO requestDTO) {
        try {
            User user = requestDTO.dtoToEntity();
            service.createUser(user);
            return new ResponseEntity(user, HttpStatus.CREATED);
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity searchForAllUsers() {
        List<User> users = service.searchForAllUsers();
        return ResponseEntity
                .ok(users
                        .stream()
                        .map(UserResponseDTO::entityToDto)
                .collect(Collectors
                        .toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity searchForUserById(@PathVariable("id") String id) {
        UUID idUser = UUID.fromString(id);
        Optional<User> user = service.searchForUserById(idUser);

        if (!user.isPresent()) {
            return new ResponseEntity("Usuário não encontado", HttpStatus.NOT_FOUND);
        }

        Optional<UserResponseDTO> responseDTO = user.map(UserResponseDTO::entityToDto);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity updateUser(@PathVariable("id") String id, @Valid @RequestBody UserRequestDTO requestDTO) {
        UUID idUser = UUID.fromString(id);
        if (!service.searchForUserById(idUser).isPresent()) {
            return new ResponseEntity("Usuário não encontrado!", HttpStatus.NOT_FOUND);
        }
        try {
            User user = requestDTO.dtoToEntity();
            user.setId(idUser);
            service.updateUser(user);
            return ResponseEntity.ok(user);
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@PathVariable("id") String id) {
        UUID idUser = UUID.fromString(id);
        if (!service.searchForUserById(idUser).isPresent()) {
            return new ResponseEntity("Usuário não encontrado!", HttpStatus.NOT_FOUND);
        }
        service.deleteUser(idUser);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
