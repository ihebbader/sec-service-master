package org.sid.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data  @AllArgsConstructor @NoArgsConstructor
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProp")
    private Long idProp ;
    private String id;
    private String label;
    private String labelPosition;
    private String allowMultipleMasks;
    private Boolean hidden;
    private Boolean hideLabel;
    private Boolean showWordCount;
    private Boolean showCharCount;
    private Boolean mask;
    private Boolean autofocus;
    private Boolean spellcheck;
    private Boolean disabled;
    private Boolean tableView;
    private Boolean modalEdit;
    private Boolean multiple;
    private Boolean persistent;
    private String inputFormat;
    private Boolean encrypted;
    private Boolean clearOnHide;
    private Boolean calculateServer;
    private String validateOn;
    private String type;
    private Boolean input;
    private Boolean dataGridLabel;
    private String inputType;
    private String defaultValue;
    private int ord;
    @OneToMany
    private List<Values> Values = new ArrayList<>();
    @OneToOne
    private DataValues data;









}
