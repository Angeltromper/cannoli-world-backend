package nl.novi.cannoliworld.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;

/*
import java.util.Map;
*/

@Setter
@Getter
@Entity
public class DeliveryRequest {

 @Getter
 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private Long id;


/*
 @Getter
 @SuppressWarnings("JpaAttributeTypeInspection")
 private Map<Long, String> cannoliList;
*/


 private String cannoliList;


 @Getter
 private Status status;

 @Getter
 private String comment;

 @Getter
 @JsonIgnore
 @ManyToOne(cascade = CascadeType.PERSIST)
 private Person applier;

 @JsonIgnore
 @ManyToOne
 private Person deliverer;

 public DeliveryRequest() {
 }

}






