package org.example.up_itog_10_2024.Models;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Entity
@Table(name = "statuses")
public class Status  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotBlank
    @Size(max = 50)
    private String name;

    public Status(String name) {
        this.name = name;
    }

    public Status() {

    }

    public @NotBlank @Size(max = 50) String getName() {
        return name;
    }
    public void setName(@NotBlank @Size(max = 50) String name) {
        this.name = name;
    }
}