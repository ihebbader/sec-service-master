package org.sid.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ValuesForm")
@Data
@AllArgsConstructor @NoArgsConstructor
public class Values {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idValues ;
    private String label;
    private String value;


}
