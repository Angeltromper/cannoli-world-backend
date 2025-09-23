package nl.novi.cannoliworld.models;

import javax.persistence.*;


@Entity
@Table(name = "image")
public class FileUploadResponse {

    @Id
    @Column(name = "file_name", nullable = false)
    String fileName;
    @Column(name = "content_type" )
    String contentType;

    @Column(name = "url")
    String url;

    public FileUploadResponse(String fileName, String contentType, String url) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.url =url;
    }

    public FileUploadResponse() {
    }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getContentType() { return contentType; }

    public void setContentType(String contentType) { this.contentType = contentType; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

}
