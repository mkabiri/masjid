package com.jimprove.oms.service;

import com.jimprove.oms.domain.Period;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Period}.
 */
public interface PeriodService {

    /**
     * Save a period.
     *
     * @param period the entity to save.
     * @return the persisted entity.
     */
    Period save(Period period);

    /**
     * Get all the periods.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Period> findAll(Pageable pageable);

    /**
     * Get the "id" period.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Period> findOne(Long id);

    /**
     * Delete the "id" period.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
