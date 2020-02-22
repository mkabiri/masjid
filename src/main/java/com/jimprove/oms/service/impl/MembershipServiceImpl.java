package com.jimprove.oms.service.impl;

import com.jimprove.oms.service.MembershipService;
import com.jimprove.oms.domain.Membership;
import com.jimprove.oms.repository.MembershipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Membership}.
 */
@Service
@Transactional
public class MembershipServiceImpl implements MembershipService {

    private final Logger log = LoggerFactory.getLogger(MembershipServiceImpl.class);

    private final MembershipRepository membershipRepository;

    public MembershipServiceImpl(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    /**
     * Save a membership.
     *
     * @param membership the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Membership save(Membership membership) {
        log.debug("Request to save Membership : {}", membership);
        return membershipRepository.save(membership);
    }

    /**
     * Get all the memberships.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Membership> findAll() {
        log.debug("Request to get all Memberships");
        return membershipRepository.findAll();
    }

    /**
     * Get one membership by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Membership> findOne(Long id) {
        log.debug("Request to get Membership : {}", id);
        return membershipRepository.findById(id);
    }

    /**
     * Delete the membership by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Membership : {}", id);
        membershipRepository.deleteById(id);
    }
}
