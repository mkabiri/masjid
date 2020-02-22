package com.jimprove.oms.web.rest;

import com.jimprove.oms.domain.Membership;
import com.jimprove.oms.service.MembershipService;
import com.jimprove.oms.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.jimprove.oms.domain.Membership}.
 */
@RestController
@RequestMapping("/api")
public class MembershipResource {

    private final Logger log = LoggerFactory.getLogger(MembershipResource.class);

    private static final String ENTITY_NAME = "membership";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MembershipService membershipService;

    public MembershipResource(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    /**
     * {@code POST  /memberships} : Create a new membership.
     *
     * @param membership the membership to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new membership, or with status {@code 400 (Bad Request)} if the membership has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/memberships")
    public ResponseEntity<Membership> createMembership(@Valid @RequestBody Membership membership) throws URISyntaxException {
        log.debug("REST request to save Membership : {}", membership);
        if (membership.getId() != null) {
            throw new BadRequestAlertException("A new membership cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Membership result = membershipService.save(membership);
        return ResponseEntity.created(new URI("/api/memberships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /memberships} : Updates an existing membership.
     *
     * @param membership the membership to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated membership,
     * or with status {@code 400 (Bad Request)} if the membership is not valid,
     * or with status {@code 500 (Internal Server Error)} if the membership couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/memberships")
    public ResponseEntity<Membership> updateMembership(@Valid @RequestBody Membership membership) throws URISyntaxException {
        log.debug("REST request to update Membership : {}", membership);
        if (membership.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Membership result = membershipService.save(membership);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, membership.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /memberships} : get all the memberships.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of memberships in body.
     */
    @GetMapping("/memberships")
    public List<Membership> getAllMemberships() {
        log.debug("REST request to get all Memberships");
        return membershipService.findAll();
    }

    /**
     * {@code GET  /memberships/:id} : get the "id" membership.
     *
     * @param id the id of the membership to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the membership, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/memberships/{id}")
    public ResponseEntity<Membership> getMembership(@PathVariable Long id) {
        log.debug("REST request to get Membership : {}", id);
        Optional<Membership> membership = membershipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(membership);
    }

    /**
     * {@code DELETE  /memberships/:id} : delete the "id" membership.
     *
     * @param id the id of the membership to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/memberships/{id}")
    public ResponseEntity<Void> deleteMembership(@PathVariable Long id) {
        log.debug("REST request to delete Membership : {}", id);
        membershipService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
