package org.example.up_itog_10_2024.Models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "task_participants")
public class TaskParticipant  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public TaskParticipant(User user, Task task, String roleInTask) {
        this.user = user;
        this.task = task;
        this.roleInTask = roleInTask;
    }

    @ManyToOne
    @JoinColumn(name = "task_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Task task;

    @NotBlank
    private String roleInTask;

    public TaskParticipant() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public @NotBlank String getRoleInTask() {
        return roleInTask;
    }

    public void setRoleInTask(@NotBlank String roleInTask) {
        this.roleInTask = roleInTask;
    }


}