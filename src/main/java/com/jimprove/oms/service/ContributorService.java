package com.jimprove.oms.service;

import com.jimprove.oms.domain.Contributor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Contributor}.
 */
public interface ContributorService {

    /**
     * Save a contributor.
     *
     * @param contributor the entity to save.
     * @return the persisted entity.
     */
    Contributor save(Contributor contributor);

    /**
     * Get all the contributors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Contributor> findAll(Pageable pageable);

    /**
     * Get all the contributors with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Contributor> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" contributor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Contributor> findOne(Long id);

    /**
     * Delete the "id" contributor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
