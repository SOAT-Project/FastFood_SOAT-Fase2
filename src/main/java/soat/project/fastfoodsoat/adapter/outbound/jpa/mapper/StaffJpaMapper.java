package soat.project.fastfoodsoat.adapter.outbound.jpa.mapper;

import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.RoleJpaEntity;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.StaffJpaEntity;
import soat.project.fastfoodsoat.domain.staff.Staff;
import soat.project.fastfoodsoat.domain.staff.StaffCpf;
import soat.project.fastfoodsoat.domain.staff.StaffId;
import soat.project.fastfoodsoat.domain.staff.role.Role;
import soat.project.fastfoodsoat.domain.staff.role.RoleId;

import java.util.List;

public final class StaffJpaMapper {

    private StaffJpaMapper() {
        // Prevent instantiation
    }

    public static Staff fromJpa(final StaffJpaEntity staffJpa, final List<RoleJpaEntity> rolesJpa) {
        return Staff.with(
                StaffId.of(staffJpa.getId()),
                staffJpa.getName(),
                staffJpa.getEmail(),
                StaffCpf.of(staffJpa.getCpf()),
                fromJpa(rolesJpa),
                staffJpa.getIsActive(),
                staffJpa.getCreatedAt(),
                staffJpa.getUpdateAt(),
                staffJpa.getDeletedAt()
        );
    }

    public static List<Role> fromJpa(final List<RoleJpaEntity> rolesJpa) {
        return rolesJpa.stream()
                .map(role -> Role.with(
                        RoleId.of(role.getId()),
                        role.getRoleName(),
                        role.getCreatedAt(),
                        role.getUpdateAt(),
                        role.getDeletedAt()
                ))
                .toList();
    }

}
