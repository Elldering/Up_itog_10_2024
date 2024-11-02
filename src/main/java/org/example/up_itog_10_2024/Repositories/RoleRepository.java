package org.example.up_itog_10_2024.Repositories;

import org.example.up_itog_10_2024.Models.*;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role,Long> {
    Role getRoleById(long id);
}