package nl.novi.cannoliworld.controllers;

import nl.novi.cannoliworld.dtos.PersonDto;
import nl.novi.cannoliworld.dtos.PersonInputDto;
import nl.novi.cannoliworld.models.FileUploadResponse;
import nl.novi.cannoliworld.models.Person;
import nl.novi.cannoliworld.service.PersonService;
import nl.novi.cannoliworld.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) { this.personService = personService; }

    @GetMapping("/users")
    @Transactional
    public List<PersonDto> getPersonList(@RequestParam(value = "firstname", required = false, defaultValue = "")String personFirstname,
                                         @RequestParam(value = "lastname", required = false, defaultValue = "")String personLastname) {

        var dtos = new ArrayList<PersonDto>();

        List<Person> personList;

        if (personFirstname == null && personLastname == null) {
            personList = personService.getPersonList();

        } else if (personFirstname != null && personLastname == null) {
            personList = personService.findPersonListByPersonFirstname(personFirstname);

        } else {
            personList = personService.findPersonListByPersonLastname(personLastname);
        }
        for (Person person : personList) {
         dtos.add(PersonDto.fromPerson(person));
        }
        return dtos;
    }

    @GetMapping("/{id}")
    public PersonDto getPerson(@PathVariable("id")Long id) {

        var person = personService.getPerson(id);

        return PersonDto.fromPerson(person);
    }

    @PostMapping
    public PersonDto savePerson(@RequestBody PersonInputDto dto) {

        var person = personService.savePerson(dto.toPerson());

        return PersonDto.fromPerson(person);
    }

    @PutMapping("/{id}")
    public PersonDto updatePerson(@PathVariable Long id,
                                  @RequestBody Person person) {
        personService.updatePerson(id, person);

        return PersonDto.fromPerson(person);
    }

    @DeleteMapping(path = "{id}")
    public void deletePerson(
            @PathVariable("id") Long personId) {
        personService.deletePerson(personId);
    }

    @RestController
    @CrossOrigin
    @RequestMapping("/images")
    public static class PhotoController {

        private final PhotoService service;

        public PhotoController(PhotoService service) { this.service = service; }

        @PutMapping("/upload")
        FileUploadResponse singleFileUpload(@RequestParam("file") MultipartFile file){

            String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/images/download/").path(Objects.requireNonNull(file.getOriginalFilename())).toUriString();

            String contentType = file.getContentType();

            String fileName = service.storeFile(file, url);

            return new FileUploadResponse(fileName, contentType, url);
        }

        @GetMapping("/download/{fileName}")
        ResponseEntity<Resource> downLoadSingleFile(@PathVariable String fileName, HttpServletRequest request) {

            Resource resource = (Resource) service.downLoadFile(fileName);
            String mimeType;

            try{
                mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException e) {
                mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename()).body(resource);
        }

        @DeleteMapping("/delete/")
        ResponseEntity<Objects> deleteImage(@PathVariable String fileName) {
            service.deleteImage(fileName);

            return ResponseEntity.noContent().build();
        }

    }
}



