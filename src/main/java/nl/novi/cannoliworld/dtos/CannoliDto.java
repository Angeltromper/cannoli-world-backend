package nl.novi.cannoliworld.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import nl.novi.cannoliworld.models.Cannoli;
import nl.novi.cannoliworld.models.FileUploadResponse;
import org.apache.tomcat.util.http.fileupload.FileUpload;

@Getter @Setter
public class CannoliDto {

    public Long id;
    public String cannoliName;
    public String cannoliType;
    public String description;
    public String ingedrients;
    public double price;

    @JsonSerialize
    FileUploadResponse picture;

    public static CannoliDto fromCannoli(Cannoli cannoli) {

        var dto= new CannoliDto();

        dto.id = cannoli.getId();

        dto.cannoliName = cannoli.getCannoliName();

        dto.cannoliType = cannoli.getCannoliType();

        dto.description = cannoli.getDescription();

        dto.ingedrients = cannoli.getIngredients();

        dto.price = cannoli.getPrice();

        dto.picture = cannoli.getPicture();

        return dto;
    }
}

