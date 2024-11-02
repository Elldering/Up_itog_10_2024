package org.example.up_itog_10_2024.Models;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project implements Serializable {
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
    @Size(max = 100)
    private String name;
    @NotBlank
    private String status;
    @NotNull
    @Min(1)
    @Max(5)
    private Integer priority;

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Task> tasks;


    public Project(String name, String status, Integer priority, List<Task> tasks) {
        this.name = name;
        this.status = status;
        this.priority = priority;
        this.tasks = tasks;
    }

    public Project() {

    }

    public @NotBlank @Size(max = 100) String getName() {
        return name;
    }

    public void setName(@NotBlank @Size(max = 100) String name) {
        this.name = name;
    }

    public @NotBlank String getStatus() {
        return status;
    }

    public void setStatus(@NotBlank String status) {
        this.status = status;
    }

    public @NotNull @Min(1) @Max(5) Integer getPriority() {
        return priority;
    }

    public void setPriority(@NotNull @Min(1) @Max(5) Integer priority) {
        this.priority = priority;
    }

}