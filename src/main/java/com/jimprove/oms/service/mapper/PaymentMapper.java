package com.jimprove.oms.service.mapper;


import com.jimprove.oms.domain.*;
import com.jimprove.oms.service.dto.PaymentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Payment} and its DTO {@link PaymentDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContributorMapper.class, PeriodMapper.class})
public interface PaymentMapper extends EntityMapper<PaymentDTO, Payment> {

    @Mapping(source = "contributor.id", target = "contributorId")
    @Mapping(source = "period.id", target = "periodId")
    @Mapping(source = "period.year", target = "periodYear")
    PaymentDTO toDto(Payment payment);

    @Mapping(source = "contributorId", target = "contributor")
    @Mapping(source = "periodId", target = "period")
    Payment toEntity(PaymentDTO paymentDTO);

    default Payment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Payment payment = new Payment();
        payment.setId(id);
        return payment;
    }
}
