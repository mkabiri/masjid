package com.jimprove.oms.web.rest;

import com.jimprove.oms.domain.Contributor;
import com.jimprove.oms.service.ContributorService;
import com.jimprove.oms.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.jimprove.oms.domain.Contributor}.
 */
@RestController
@RequestMapping("/api")
public class ContributorResource {

    private final Logger log = LoggerFactory.getLogger(ContributorResource.class);

    private static final String ENTITY_NAME = "contributor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContributorService contributorService;

    public ContributorResource(ContributorService contributorService) {
        this.contributorService = contributorService;
    }

    /**
     * {@code POST  /contributors} : Create a new contributor.
     *
     * @param contributor the contributor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contributor, or with status {@code 400 (Bad Request)} if the contributor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contributors")
    public ResponseEntity<Contributor> createContributor(@Valid @RequestBody Contributor contributor) throws URISyntaxException {
        log.debug("REST request to save Contributor : {}", contributor);
        if (contributor.getId() != null) {
            throw new BadRequestAlertException("A new contributor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Contributor result = contributorService.save(contributor);
        return ResponseEntity.created(new URI("/api/contributors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contributors} : Updates an existing contributor.
     *
     * @param contributor the contributor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contributor,
     * or with status {@code 400 (Bad Request)} if the contributor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contributor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contributors")
    public ResponseEntity<Contributor> updateContributor(@Valid @RequestBody Contributor contributor) throws URISyntaxException {
        log.debug("REST request to update Contributor : {}", contributor);
        if (contributor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Contributor result = contributorService.save(contributor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contributor.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contributors} : get all the contributors.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contributors in body.
     */
    @GetMapping("/contributors")
    public ResponseEntity<List<Contributor>> getAllContributors(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Contributors");
        Page<Contributor> page;
        if (eagerload) {
            page = contributorService.findAllWithEagerRelationships(pageable);
        } else {
            page = contributorService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contributors/:id} : get the "id" contributor.
     *
     * @param id the id of the contributor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contributor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contributors/{id}")
    public ResponseEntity<Contributor> getContributor(@PathVariable Long id) {
        log.debug("REST request to get Contributor : {}", id);
        Optional<Contributor> contributor = contributorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contributor);
    }

    /**
     * {@code DELETE  /contributors/:id} : delete the "id" contributor.
     *
     * @param id the id of the contributor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contributors/{id}")
    public ResponseEntity<Void> deleteContributor(@PathVariable Long id) {
        log.debug("REST request to delete Contributor : {}", id);
        contributorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
