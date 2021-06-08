package org.sid.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class DataModel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String DataModelName;
    private String DataModelDescrip;
    private Date StartDate;
    private Date EndDate;
    private String Status;
    @OneToMany
    private List<EntityModel> entity;


}
