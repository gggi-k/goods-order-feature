package kr.submit.goodsorderfeature.api.application;


import kr.submit.goodsorderfeature.api.domain.entity.DeliveryEntity;
import kr.submit.goodsorderfeature.api.dto.DeliveryResponse;
import kr.submit.goodsorderfeature.api.repository.DeliveryRepository;
import kr.submit.goodsorderfeature.core.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public DeliveryResponse complete(Long deliveryId) {
        DeliveryEntity deliveryEntity = deliveryRepository.findById(deliveryId).orElseThrow(() -> new NotFoundException("해당하는 배달정보가 없습니다"));
        deliveryEntity.changeComplete();

        return DeliveryResponse.fromEntity(deliveryRepository.save(deliveryEntity));
    }
}