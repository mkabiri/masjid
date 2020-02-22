package com.jimprove.oms.service.impl;

import com.jimprove.oms.service.PeriodService;
import com.jimprove.oms.domain.Period;
import com.jimprove.oms.repository.PeriodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Period}.
 */
@Service
@Transactional
public class PeriodServiceImpl implements PeriodService {

    private final Logger log = LoggerFactory.getLogger(PeriodServiceImpl.class);

    private final PeriodRepository periodRepository;

    public PeriodServiceImpl(PeriodRepository periodRepository) {
        this.periodRepository = periodRepository;
    }

    /**
     * Save a period.
     *
     * @param period the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Period save(Period period) {
        log.debug("Request to save Period : {}", period);
        return periodRepository.save(period);
    }

    /**
     * Get all the periods.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Period> findAll(Pageable pageable) {
        log.debug("Request to get all Periods");
        return periodRepository.findAll(pageable);
    }

    /**
     * Get one period by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Period> findOne(Long id) {
        log.debug("Request to get Period : {}", id);
        return periodRepository.findById(id);
    }

    /**
     * Delete the period by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Period : {}", id);
        periodRepository.deleteById(id);
    }
}
