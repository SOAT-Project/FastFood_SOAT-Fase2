package soat.project.fastfoodsoat.adapter.outbound.jpa.mapper;

import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.RoleJpaEntity;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.StaffJpaEntity;
import soat.project.fastfoodsoat.domain.staff.Staff;
import soat.project.fastfoodsoat.domain.staff.StaffCpf;
import soat.project.fastfoodsoat.domain.staff.StaffId;
import soat.project.fastfoodsoat.domain.staff.role.Role;
import soat.project.fastfoodsoat.domain.staff.role.RoleId;

import java.util.List;
import java.util.Objects;

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

    public static StaffJpaEntity toJpa(final Staff staff) {
        if (Objects.isNull(staff)) return new StaffJpaEntity();

        return new StaffJpaEntity(
                Objects.isNull(staff.getId()) ? null : staff.getId().getValue(),
                staff.getName(),
                staff.getEmail(),
                Objects.isNull(staff.getCpf()) ? null : staff.getCpf().getValue(),
                staff.isActive(),
                staff.getCreatedAt(),
                staff.getUpdatedAt(),
                staff.getDeletedAt()
        );
    }

    public static List<Role> fromJpa(final List<RoleJpaEntity> rolesJpa) {
        return rolesJpa.stream()
                .filter(Objects::nonNull)
                .map(role -> Role.with(
                        RoleId.of(role.getId()),
                        role.getRoleName(),
                        role.getCreatedAt(),
                        role.getUpdateAt(),
                        role.getDeletedAt()
                ))
                .toList();
    }

    public static List<RoleJpaEntity> toJpa(final List<Role> roles) {
        return roles.stream()
                .filter(Objects::nonNull)
                .map(role -> new RoleJpaEntity(
                        Objects.isNull(role.getId()) ? null : role.getId().getValue(),
                        role.getName(),
                        role.getCreatedAt(),
                        role.getUpdatedAt(),
                        role.getDeletedAt()
                ))
                .toList();
    }

}
