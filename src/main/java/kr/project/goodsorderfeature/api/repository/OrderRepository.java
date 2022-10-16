package kr.project.goodsorderfeature.api.repository;

import kr.project.goodsorderfeature.api.domain.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}