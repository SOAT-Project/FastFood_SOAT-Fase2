package soat.project.fastfoodsoat.domain.staff.role;

import java.util.Optional;

public interface RoleGateway {
    Optional<Role> findById(RoleId id);
    Optional<Role> findByName(String name);
}
