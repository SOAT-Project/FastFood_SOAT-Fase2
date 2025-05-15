package soat.project.fastfoodsoat.adapter.outbound.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.StaffRoleJpaEntity;

import java.util.List;

public interface StaffRoleRepository extends JpaRepository<StaffRoleJpaEntity, Integer> {
    List<StaffRoleJpaEntity> findAllByStaff_Id(Integer staffId);
}