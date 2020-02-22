package com.jimprove.oms.web.rest;

import com.jimprove.oms.domain.Titulo;
import com.jimprove.oms.repository.TituloRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.jimprove.oms.domain.Titulo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TituloResource {

    private final Logger log = LoggerFactory.getLogger(TituloResource.class);

    private static final String ENTITY_NAME = "titulo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TituloRepository tituloRepository;

    public TituloResource(TituloRepository tituloRepository) {
        this.tituloRepository = tituloRepository;
    }

    /**
     * {@code POST  /titulos} : Create a new titulo.
     *
     * @param titulo the titulo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new titulo, or with status {@code 400 (Bad Request)} if the titulo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/titulos")
    public ResponseEntity<Titulo> createTitulo(@Valid @RequestBody Titulo titulo) throws URISyntaxException {
        log.debug("REST request to save Titulo : {}", titulo);
        if (titulo.getId() != null) {
            throw new BadRequestAlertException("A new titulo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Titulo result = tituloRepository.save(titulo);
        return ResponseEntity.created(new URI("/api/titulos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /titulos} : Updates an existing titulo.
     *
     * @param titulo the titulo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated titulo,
     * or with status {@code 400 (Bad Request)} if the titulo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the titulo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/titulos")
    public ResponseEntity<Titulo> updateTitulo(@Valid @RequestBody Titulo titulo) throws URISyntaxException {
        log.debug("REST request to update Titulo : {}", titulo);
        if (titulo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Titulo result = tituloRepository.save(titulo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, titulo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /titulos} : get all the titulos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of titulos in body.
     */
    @GetMapping("/titulos")
    public ResponseEntity<List<Titulo>> getAllTitulos(Pageable pageable) {
        log.debug("REST request to get a page of Titulos");
        Page<Titulo> page = tituloRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /titulos/:id} : get the "id" titulo.
     *
     * @param id the id of the titulo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the titulo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/titulos/{id}")
    public ResponseEntity<Titulo> getTitulo(@PathVariable Long id) {
        log.debug("REST request to get Titulo : {}", id);
        Optional<Titulo> titulo = tituloRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(titulo);
    }

    /**
     * {@code DELETE  /titulos/:id} : delete the "id" titulo.
     *
     * @param id the id of the titulo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/titulos/{id}")
    public ResponseEntity<Void> deleteTitulo(@PathVariable Long id) {
        log.debug("REST request to delete Titulo : {}", id);
        tituloRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
