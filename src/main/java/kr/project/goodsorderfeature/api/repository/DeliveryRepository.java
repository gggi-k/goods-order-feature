package kr.project.goodsorderfeature.api.repository;

import kr.project.goodsorderfeature.api.domain.entity.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {
}