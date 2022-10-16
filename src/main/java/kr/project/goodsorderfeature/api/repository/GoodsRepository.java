package kr.project.goodsorderfeature.api.repository;

import kr.project.goodsorderfeature.api.domain.entity.GoodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<GoodsEntity, Long> {

}