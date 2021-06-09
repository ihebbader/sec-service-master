package org.sid.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class EntityModel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String EntityModelName;
    private String EntityModelDescrip;
    private Date StartDate;
    private Date EndDate;
    private String DelaiDexecutionEnHeure;
    private Boolean importantes =false;
    private String status;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Notification> notification;
    private int EtapeOrd;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Property> properties;
    @ManyToMany
    private Set<AppUser> user = new HashSet<>();
    private Date dateDeCreation;
    private String creator;
    private String etat;
    private Boolean actived=false;
    private Boolean finished;
    @ManyToOne
    private Groupe groupe;

}
