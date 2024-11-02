package org.example.up_itog_10_2024.Models;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

import javax.validation.constraints.*;
import java.io.Serializable;

@Entity
@Table(name = "audit_log")
public class AuditLog  implements Serializable {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String action;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    public AuditLog(String action, User user) {
        this.action = action;
        this.user = user;
    }

    public AuditLog() {

    }

    public @NotBlank String getAction() {
        return action;
    }

    public void setAction(@NotBlank String action) {
        this.action = action;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}