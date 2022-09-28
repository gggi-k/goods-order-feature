package kr.submit.goodsorderfeature.api.repository;

import kr.submit.goodsorderfeature.api.domain.entity.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {
}