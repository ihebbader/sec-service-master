package org.sid.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data @NoArgsConstructor
@Table(name="widget")
public class Widget {
    @Id   @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String type;

    public Widget(String type) {
        this.type = type;
    }
}
