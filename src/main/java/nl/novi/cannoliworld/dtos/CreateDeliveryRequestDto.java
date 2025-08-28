package nl.novi.cannoliworld.dtos;


import javax.validation.constraints.NotEmpty;
import java.util.List;

public class CreateDeliveryRequestDto {


    @NotEmpty
    private List<ItemDto> items;
    private String comment;

    // getters/setters
    public List<ItemDto> getItems() {
        return items; }
    public void setItems(List<ItemDto> items) {
        this.items = items; }
    public String getComment() {
        return comment; }
    public void setComment(String comment) {
        this.comment = comment; }

    public static class ItemDto {
//        @NotNull
        private Long cannoliId;

//        @NotNull
//        @Min(1)
//        @Max(500)
        private Integer quantity;

        // getters/setters
        public Long getCannoliId() {
            return cannoliId; }
        public void setCannoliId(Long cannoliId) {
            this.cannoliId = cannoliId; }
        public Integer getQuantity() {
            return quantity; }
        public void setQuantity(Integer quantity) {
            this.quantity = quantity; }
    }
}

