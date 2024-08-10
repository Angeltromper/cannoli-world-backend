package nl.novi.cannoliworld.controllers;

import nl.novi.cannoliworld.dtos.UserDto;
import nl.novi.cannoliworld.exeptions.UsernameNotFoundException;
import nl.novi.cannoliworld.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    private UserController(UserServiceImpl userService) { this.userService = userService; }

    @GetMapping("")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> userDtos = userService.getUsers();
        return ResponseEntity.ok().body(userDtos);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        try {
            UserDto userDto = userService.getUser(username);
            return ResponseEntity.ok().body(userDto);
        } catch (UsernameNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping(value = "/createUser")
    public ResponseEntity<Object> createUser(@RequestBody UserDto dto) {
        String newUsername = userService.createUser(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(newUsername).toUri();
        return ResponseEntity.created(location).build();
    }
    @PutMapping(value = "/{username}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("username") String username, @RequestBody UserDto dto) {
        userService.updateUser(username, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{username]")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username, @RequestBody UserDto dto) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }
}

