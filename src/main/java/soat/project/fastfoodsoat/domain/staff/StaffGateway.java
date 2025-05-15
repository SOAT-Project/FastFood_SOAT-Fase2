package soat.project.fastfoodsoat.domain.staff;

import java.util.Optional;

public interface StaffGateway {
    Optional<Staff> findById(StaffId id);
    Optional<Staff> findByEmail(String email);
    Optional<Staff> findByCpf(StaffCpf cpf);
}
