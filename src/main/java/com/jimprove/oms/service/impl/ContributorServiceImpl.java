package com.jimprove.oms.service.impl;

import com.jimprove.oms.service.ContributorService;
import com.jimprove.oms.domain.Contributor;
import com.jimprove.oms.repository.ContributorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Contributor}.
 */
@Service
@Transactional
public class ContributorServiceImpl implements ContributorService {

    private final Logger log = LoggerFactory.getLogger(ContributorServiceImpl.class);

    private final ContributorRepository contributorRepository;

    public ContributorServiceImpl(ContributorRepository contributorRepository) {
        this.contributorRepository = contributorRepository;
    }

    /**
     * Save a contributor.
     *
     * @param contributor the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Contributor save(Contributor contributor) {
        log.debug("Request to save Contributor : {}", contributor);
        return contributorRepository.save(contributor);
    }

    /**
     * Get all the contributors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Contributor> findAll(Pageable pageable) {
        log.debug("Request to get all Contributors");
        return contributorRepository.findAll(pageable);
    }

    /**
     * Get all the contributors with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Contributor> findAllWithEagerRelationships(Pageable pageable) {
        return contributorRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one contributor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Contributor> findOne(Long id) {
        log.debug("Request to get Contributor : {}", id);
        return contributorRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the contributor by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Contributor : {}", id);
        contributorRepository.deleteById(id);
    }
}
