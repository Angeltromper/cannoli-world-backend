package nl.novi.cannoliworld.controllers;
import nl.novi.cannoliworld.dtos.UserDto;
import nl.novi.cannoliworld.service.PhotoService;
import nl.novi.cannoliworld.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PhotoService photoService;
    public UserController(UserService userService, PhotoService photoService) {
        this.userService = userService;
        this.photoService = photoService;
    }
    @GetMapping("/all")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        return userService.getUser(username)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping(path = "/create", consumes = "application/json")
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserDto dto) {
        String newUsername = userService.createUser(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/{username}")
                .buildAndExpand(newUsername)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{username}/{personId}")
    public ResponseEntity<Void> assignPersonToUser(@PathVariable String username,
                                                   @PathVariable Long personId) {
        userService.assignPersonToUser(personId, username);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{username}/image/{fileName}")
    public ResponseEntity<Void> assignImageToUser(@PathVariable String username,
                                                  @PathVariable String fileName) {
        userService.assignImageToUser(username, fileName);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{username}/image", consumes = "multipart/form-data")
    public ResponseEntity<Void> uploadImageToUser(@PathVariable String username,
                                                  @RequestParam MultipartFile file) {

        String storedFileName = photoService.storeFile(file, "/users/" + username);
        userService.assignImageToUser(username, storedFileName);
        return ResponseEntity.ok().build();
    }
}
