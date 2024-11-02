package org.example.up_itog_10_2024.Repositories;
import org.example.up_itog_10_2024.Models.Project;
import org.example.up_itog_10_2024.Models.RolePermission;
import org.springframework.data.repository.CrudRepository;

public interface RolePermissionsRepository extends CrudRepository<RolePermission,Long> {

    RolePermission deleteByPermissionId(Long permission_id);
}
