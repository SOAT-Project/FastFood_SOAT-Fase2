package soat.project.fastfoodsoat.application.gateway;

import soat.project.fastfoodsoat.domain.staff.Staff;
import soat.project.fastfoodsoat.domain.staff.StaffCpf;
import soat.project.fastfoodsoat.domain.staff.StaffId;

import java.util.Optional;

public interface StaffRepositoryGateway {
    Optional<Staff> findById(StaffId id);
    Optional<Staff> findByEmail(String email);
    Optional<Staff> findByCpf(StaffCpf cpf);
}
