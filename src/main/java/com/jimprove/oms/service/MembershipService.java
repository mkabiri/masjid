package com.jimprove.oms.service;

import com.jimprove.oms.domain.Membership;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Membership}.
 */
public interface MembershipService {

    /**
     * Save a membership.
     *
     * @param membership the entity to save.
     * @return the persisted entity.
     */
    Membership save(Membership membership);

    /**
     * Get all the memberships.
     *
     * @return the list of entities.
     */
    List<Membership> findAll();

    /**
     * Get the "id" membership.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Membership> findOne(Long id);

    /**
     * Delete the "id" membership.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
