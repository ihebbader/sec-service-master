package org.sid.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;


public class Validate {
    private Long id;
    private Boolean required;
    private Boolean customPrivate;
    private int minLength;
    private int maxLength;
    private Boolean strictDateValidation;
    private Boolean multiple;
    private Boolean unique;


}
