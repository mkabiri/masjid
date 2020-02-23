package com.jimprove.oms.web.rest;

import com.jimprove.oms.MasjidApp;
import com.jimprove.oms.domain.Titulo;
import com.jimprove.oms.repository.TituloRepository;
import com.jimprove.oms.service.TitulosQueryService;
import com.jimprove.oms.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.jimprove.oms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jimprove.oms.domain.enumeration.EstatusTitulo;
/**
 * Integration tests for the {@link TituloResource} REST controller.
 */
@SpringBootTest(classes = MasjidApp.class)
public class TituloResourceIT {

    private static final Integer DEFAULT_REGISTRATION_NUMBER = 1;
    private static final Integer UPDATED_REGISTRATION_NUMBER = 2;

    private static final String DEFAULT_FULL_NAME_ARABIC = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME_ARABIC = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_NAME_LATIN = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME_LATIN = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final EstatusTitulo DEFAULT_ESTATUS_TITULO = EstatusTitulo.EXPEDIDO;
    private static final EstatusTitulo UPDATED_ESTATUS_TITULO = EstatusTitulo.FIRMADO;

    @Autowired
    private TituloRepository tituloRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTituloMockMvc;

    private Titulo titulo;
    private TitulosQueryService titulosQueryService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TituloResource tituloResource = new TituloResource(tituloRepository, titulosQueryService);
        this.restTituloMockMvc = MockMvcBuilders.standaloneSetup(tituloResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Titulo createEntity(EntityManager em) {
        Titulo titulo = new Titulo()
            .registrationNumber(DEFAULT_REGISTRATION_NUMBER)
            .fullNameArabic(DEFAULT_FULL_NAME_ARABIC)
            .fullNameLatin(DEFAULT_FULL_NAME_LATIN)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .estatusTitulo(DEFAULT_ESTATUS_TITULO);
        return titulo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Titulo createUpdatedEntity(EntityManager em) {
        Titulo titulo = new Titulo()
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .fullNameArabic(UPDATED_FULL_NAME_ARABIC)
            .fullNameLatin(UPDATED_FULL_NAME_LATIN)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .estatusTitulo(UPDATED_ESTATUS_TITULO);
        return titulo;
    }

    @BeforeEach
    public void initTest() {
        titulo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTitulo() throws Exception {
        int databaseSizeBeforeCreate = tituloRepository.findAll().size();

        // Create the Titulo
        restTituloMockMvc.perform(post("/api/titulos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(titulo)))
            .andExpect(status().isCreated());

        // Validate the Titulo in the database
        List<Titulo> tituloList = tituloRepository.findAll();
        assertThat(tituloList).hasSize(databaseSizeBeforeCreate + 1);
        Titulo testTitulo = tituloList.get(tituloList.size() - 1);
        assertThat(testTitulo.getRegistrationNumber()).isEqualTo(DEFAULT_REGISTRATION_NUMBER);
        assertThat(testTitulo.getFullNameArabic()).isEqualTo(DEFAULT_FULL_NAME_ARABIC);
        assertThat(testTitulo.getFullNameLatin()).isEqualTo(DEFAULT_FULL_NAME_LATIN);
        assertThat(testTitulo.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testTitulo.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testTitulo.getEstatusTitulo()).isEqualTo(DEFAULT_ESTATUS_TITULO);
    }

    @Test
    @Transactional
    public void createTituloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tituloRepository.findAll().size();

        // Create the Titulo with an existing ID
        titulo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTituloMockMvc.perform(post("/api/titulos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(titulo)))
            .andExpect(status().isBadRequest());

        // Validate the Titulo in the database
        List<Titulo> tituloList = tituloRepository.findAll();
        assertThat(tituloList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRegistrationNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = tituloRepository.findAll().size();
        // set the field null
        titulo.setRegistrationNumber(null);

        // Create the Titulo, which fails.

        restTituloMockMvc.perform(post("/api/titulos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(titulo)))
            .andExpect(status().isBadRequest());

        List<Titulo> tituloList = tituloRepository.findAll();
        assertThat(tituloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFullNameLatinIsRequired() throws Exception {
        int databaseSizeBeforeTest = tituloRepository.findAll().size();
        // set the field null
        titulo.setFullNameLatin(null);

        // Create the Titulo, which fails.

        restTituloMockMvc.perform(post("/api/titulos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(titulo)))
            .andExpect(status().isBadRequest());

        List<Titulo> tituloList = tituloRepository.findAll();
        assertThat(tituloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTitulos() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get all the tituloList
        restTituloMockMvc.perform(get("/api/titulos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(titulo.getId().intValue())))
            .andExpect(jsonPath("$.[*].registrationNumber").value(hasItem(DEFAULT_REGISTRATION_NUMBER)))
            .andExpect(jsonPath("$.[*].fullNameArabic").value(hasItem(DEFAULT_FULL_NAME_ARABIC)))
            .andExpect(jsonPath("$.[*].fullNameLatin").value(hasItem(DEFAULT_FULL_NAME_LATIN)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].estatusTitulo").value(hasItem(DEFAULT_ESTATUS_TITULO.toString())));
    }
    
    @Test
    @Transactional
    public void getTitulo() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        // Get the titulo
        restTituloMockMvc.perform(get("/api/titulos/{id}", titulo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(titulo.getId().intValue()))
            .andExpect(jsonPath("$.registrationNumber").value(DEFAULT_REGISTRATION_NUMBER))
            .andExpect(jsonPath("$.fullNameArabic").value(DEFAULT_FULL_NAME_ARABIC))
            .andExpect(jsonPath("$.fullNameLatin").value(DEFAULT_FULL_NAME_LATIN))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.estatusTitulo").value(DEFAULT_ESTATUS_TITULO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTitulo() throws Exception {
        // Get the titulo
        restTituloMockMvc.perform(get("/api/titulos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTitulo() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        int databaseSizeBeforeUpdate = tituloRepository.findAll().size();

        // Update the titulo
        Titulo updatedTitulo = tituloRepository.findById(titulo.getId()).get();
        // Disconnect from session so that the updates on updatedTitulo are not directly saved in db
        em.detach(updatedTitulo);
        updatedTitulo
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .fullNameArabic(UPDATED_FULL_NAME_ARABIC)
            .fullNameLatin(UPDATED_FULL_NAME_LATIN)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .estatusTitulo(UPDATED_ESTATUS_TITULO);

        restTituloMockMvc.perform(put("/api/titulos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTitulo)))
            .andExpect(status().isOk());

        // Validate the Titulo in the database
        List<Titulo> tituloList = tituloRepository.findAll();
        assertThat(tituloList).hasSize(databaseSizeBeforeUpdate);
        Titulo testTitulo = tituloList.get(tituloList.size() - 1);
        assertThat(testTitulo.getRegistrationNumber()).isEqualTo(UPDATED_REGISTRATION_NUMBER);
        assertThat(testTitulo.getFullNameArabic()).isEqualTo(UPDATED_FULL_NAME_ARABIC);
        assertThat(testTitulo.getFullNameLatin()).isEqualTo(UPDATED_FULL_NAME_LATIN);
        assertThat(testTitulo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTitulo.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testTitulo.getEstatusTitulo()).isEqualTo(UPDATED_ESTATUS_TITULO);
    }

    @Test
    @Transactional
    public void updateNonExistingTitulo() throws Exception {
        int databaseSizeBeforeUpdate = tituloRepository.findAll().size();

        // Create the Titulo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTituloMockMvc.perform(put("/api/titulos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(titulo)))
            .andExpect(status().isBadRequest());

        // Validate the Titulo in the database
        List<Titulo> tituloList = tituloRepository.findAll();
        assertThat(tituloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTitulo() throws Exception {
        // Initialize the database
        tituloRepository.saveAndFlush(titulo);

        int databaseSizeBeforeDelete = tituloRepository.findAll().size();

        // Delete the titulo
        restTituloMockMvc.perform(delete("/api/titulos/{id}", titulo.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Titulo> tituloList = tituloRepository.findAll();
        assertThat(tituloList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
