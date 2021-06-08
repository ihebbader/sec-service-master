package org.sid.entities;

import com.sun.mail.imap.protocol.ID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class UserNotification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @OneToMany
    private List<AppUser> appUsers = new ArrayList<>();
    private String NotificationMessage;
    private Date dateNotification ;
    private boolean sended=false;
}
