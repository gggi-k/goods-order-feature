package kr.submit.goodsorderfeature.api.repository;

import kr.submit.goodsorderfeature.api.domain.entity.GoodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GoodsRepository extends JpaRepository<GoodsEntity, Long> {

}