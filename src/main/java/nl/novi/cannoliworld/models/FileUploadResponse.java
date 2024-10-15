package nl.novi.cannoliworld.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "image")
public class FileUploadResponse {

    @Id
    String fileName;

    String contentType;

    String url;

    public FileUploadResponse(String fileName, String contentType, String url) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.url =url;
    }

    public FileUploadResponse() {
    }

}
