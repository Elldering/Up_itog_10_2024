package org.example.up_itog_10_2024.Repositories;

import org.example.up_itog_10_2024.Models.Permission;
import org.example.up_itog_10_2024.Models.Role;
import org.example.up_itog_10_2024.Models.User;
import org.springframework.data.repository.CrudRepository;

public interface PermissionRepository extends CrudRepository<Permission,Long> {
    Permission getPermissionById(long id);


}
