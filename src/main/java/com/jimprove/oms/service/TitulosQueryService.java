package com.jimprove.oms.service;

import io.github.jhipster.service.QueryService;
import com.jimprove.oms.domain.Titulo;
import com.jimprove.oms.domain.Titulo_;
import com.jimprove.oms.repository.TituloRepository;
import com.jimprove.oms.service.dto.TituloCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TitulosQueryService extends QueryService<Titulo> {
    private final static Logger log = LoggerFactory.getLogger(TitulosQueryService.class);

    private final TituloRepository titulosRepository;

    public TitulosQueryService(TituloRepository titulosRepository) {
        this.titulosRepository = titulosRepository;
    }

    @Transactional(readOnly = true)
    public Page<Titulo> findByCriteria(TituloCriteria criteria, Pageable page) {
        log.debug("find by Criteria: {}, Page: {}", criteria, page);
        final Specification<Titulo> specification = createSpecification(criteria);
        return titulosRepository.findAll(specification, page);
    }

    private Specification<Titulo> createSpecification(TituloCriteria criteria) {
        Specification<Titulo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Titulo_.id));
            }

        }
        return specification;
    }

}
