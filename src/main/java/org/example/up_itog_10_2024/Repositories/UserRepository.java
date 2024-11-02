package org.example.up_itog_10_2024.Repositories;
import org.example.up_itog_10_2024.Models.*;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
    User findByUsername(String username);

    User getUserById(long l);
}