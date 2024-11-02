package org.example.up_itog_10_2024.Models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

import javax.validation.constraints.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus(@NotNull Boolean status) {
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 100)
    private String name;
    private String username;
    private boolean active;
    @NotBlank
    @Size(min = 8, max = 100)
    private String password;
    @NotNull
    private Boolean status;

    @ManyToOne()
    @JoinColumn(name = "role_id")
    private Role role;

    public User(String name, String username, boolean active, String password, Boolean status, Role role) {
        this.name = name;
        this.username = username;
        this.active = active;
        this.password = password;
        this.status = status;
        this.role = role;
    }

    public User() {

    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public @NotBlank @Size(max = 100) String getName() {
        return name;
    }

    public void setName(@NotBlank @Size(max = 100) String name) {
        this.name = name;
    }

    public @NotBlank @Size(min = 8, max = 100) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Size(min = 8, max = 100) String password) {
        this.password = password;
    }

    public @NotNull Boolean getStatus() {
        return status;
    }


    public void setStatus(boolean status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}