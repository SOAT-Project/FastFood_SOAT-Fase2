package soat.project.fastfoodsoat.infrastructure.persistence;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.entity.StaffJpaEntity;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.entity.StaffRoleJpaEntity;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.mapper.StaffJpaMapper;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.repository.StaffRepository;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.repository.StaffRoleRepository;
import soat.project.fastfoodsoat.domain.staff.Staff;
import soat.project.fastfoodsoat.domain.staff.StaffCpf;
import soat.project.fastfoodsoat.application.gateway.StaffRepositoryGateway;
import soat.project.fastfoodsoat.domain.staff.StaffId;

import java.util.Optional;

@Component
public class StaffRepositoryGatewayImpl implements StaffRepositoryGateway {

    private final StaffRepository staffRepository;
    private final StaffRoleRepository staffRoleRepository;

    public StaffRepositoryGatewayImpl(
            final StaffRepository staffRepository,
            final StaffRoleRepository staffRoleRepository
    ) {
        this.staffRepository = staffRepository;
        this.staffRoleRepository = staffRoleRepository;
    }

    @Override
    public Optional<Staff> findById(final StaffId id) {
        final var staffJpa = this.staffRepository.findById(id.getValue());

        return composeStaff(staffJpa);
    }

    @Override
    public Optional<Staff> findByEmail(final String email) {
        final var staffJpa = this.staffRepository.findByEmail(email);

        return composeStaff(staffJpa);
    }

    @Override
    public Optional<Staff> findByCpf(final StaffCpf cpf) {
        final var staffJpa = this.staffRepository.findByCpf(cpf.getValue());

        return composeStaff(staffJpa);
    }

    private Optional<Staff> composeStaff(
            final Optional<StaffJpaEntity> staffJpa
    ) {
        if (staffJpa.isEmpty()) return Optional.empty();

        final var staffRolesJpa = this.staffRoleRepository.findAllByStaff_Id(staffJpa.get().getId());

        final var rolesJpa = staffRolesJpa.stream()
                .map(StaffRoleJpaEntity::getRole)
                .toList();

        final var staff = StaffJpaMapper.fromJpa(staffJpa.get(), rolesJpa);

        return Optional.of(staff);
    }

}
