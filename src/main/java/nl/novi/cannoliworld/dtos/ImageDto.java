package nl.novi.cannoliworld.dtos;

public class ImageDto {
    private String url;
    public ImageDto() {}
    public ImageDto(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
