package org.example.up_itog_10_2024.Repositories;

import org.example.up_itog_10_2024.Models.AuditLog;
import org.example.up_itog_10_2024.Models.User;
import org.springframework.data.repository.CrudRepository;

public interface AuditLogRepository extends CrudRepository<AuditLog,Long> {
}
