package nl.novi.cannoliworld.controllers;

import nl.novi.cannoliworld.models.User;
import nl.novi.cannoliworld.service.PersonService;
import nl.novi.cannoliworld.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    private final PersonService personService;

    private final PhotoController photoController;

    @Autowired
    public UserController(UserService userService, PhotoController photoController, PersonService personService) {
        this.userService = userService;
        this.personService = personService;
        this.photoController = photoController;
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getUsers() {

        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<Object> getUser(@PathVariable("username") String username) {

        return ResponseEntity.ok().body(userService.getUser(username));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> createUser(@RequestBody User user) {

        String newUsername = userService.createUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(newUsername).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/delete/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username) {

        userService.deleteUser(username);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{username}/{personId}")
    public void assignPersonToUser(@PathVariable("username") String username,
                                   @PathVariable("personId") Long personId) {

        userService.assignPersonToUser(personId, username);
    }

    @PutMapping("/{username}/image/{fileName}")
    public void assignImageToUser(@PathVariable("username") String username,
                                    @PathVariable("fileName") String fileName) {

        userService.assignImageToUser(username, fileName);
    }

    @PutMapping("/{username}/image")
    public void uploadImageToUser(@PathVariable("username") String username,
                                    @RequestBody MultipartFile file) {

        photoController.singleFileUpload(file);
        userService.assignImageToUser(file.getOriginalFilename(), username);
    }
}







