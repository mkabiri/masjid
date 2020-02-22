package com.jimprove.oms.web.rest;

import com.jimprove.oms.MasjidApp;
import com.jimprove.oms.domain.Contributor;
import com.jimprove.oms.repository.ContributorRepository;
import com.jimprove.oms.service.ContributorService;
import com.jimprove.oms.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.jimprove.oms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jimprove.oms.domain.enumeration.ContributieStatus;
/**
 * Integration tests for the {@link ContributorResource} REST controller.
 */
@SpringBootTest(classes = MasjidApp.class)
public class ContributorResourceIT {

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

    private static final ContributieStatus DEFAULT_CONTRIBUTION_STATUS = ContributieStatus.OK;
    private static final ContributieStatus UPDATED_CONTRIBUTION_STATUS = ContributieStatus.OVERDUE;

    private static final Boolean DEFAULT_PERIODIC_CONTRIBUTION = false;
    private static final Boolean UPDATED_PERIODIC_CONTRIBUTION = true;

    private static final Integer DEFAULT_CONTRIBUTION_MONTH = 1;
    private static final Integer UPDATED_CONTRIBUTION_MONTH = 2;

    @Autowired
    private ContributorRepository contributorRepository;

    @Mock
    private ContributorRepository contributorRepositoryMock;

    @Mock
    private ContributorService contributorServiceMock;

    @Autowired
    private ContributorService contributorService;

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

    private MockMvc restContributorMockMvc;

    private Contributor contributor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContributorResource contributorResource = new ContributorResource(contributorService);
        this.restContributorMockMvc = MockMvcBuilders.standaloneSetup(contributorResource)
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
    public static Contributor createEntity(EntityManager em) {
        Contributor contributor = new Contributor()
            .registrationNumber(DEFAULT_REGISTRATION_NUMBER)
            .fullNameArabic(DEFAULT_FULL_NAME_ARABIC)
            .fullNameLatin(DEFAULT_FULL_NAME_LATIN)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .contributionStatus(DEFAULT_CONTRIBUTION_STATUS)
            .periodicContribution(DEFAULT_PERIODIC_CONTRIBUTION)
            .contributionMonth(DEFAULT_CONTRIBUTION_MONTH);
        return contributor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contributor createUpdatedEntity(EntityManager em) {
        Contributor contributor = new Contributor()
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .fullNameArabic(UPDATED_FULL_NAME_ARABIC)
            .fullNameLatin(UPDATED_FULL_NAME_LATIN)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .contributionStatus(UPDATED_CONTRIBUTION_STATUS)
            .periodicContribution(UPDATED_PERIODIC_CONTRIBUTION)
            .contributionMonth(UPDATED_CONTRIBUTION_MONTH);
        return contributor;
    }

    @BeforeEach
    public void initTest() {
        contributor = createEntity(em);
    }

    @Test
    @Transactional
    public void createContributor() throws Exception {
        int databaseSizeBeforeCreate = contributorRepository.findAll().size();

        // Create the Contributor
        restContributorMockMvc.perform(post("/api/contributors")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contributor)))
            .andExpect(status().isCreated());

        // Validate the Contributor in the database
        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeCreate + 1);
        Contributor testContributor = contributorList.get(contributorList.size() - 1);
        assertThat(testContributor.getRegistrationNumber()).isEqualTo(DEFAULT_REGISTRATION_NUMBER);
        assertThat(testContributor.getFullNameArabic()).isEqualTo(DEFAULT_FULL_NAME_ARABIC);
        assertThat(testContributor.getFullNameLatin()).isEqualTo(DEFAULT_FULL_NAME_LATIN);
        assertThat(testContributor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testContributor.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testContributor.getContributionStatus()).isEqualTo(DEFAULT_CONTRIBUTION_STATUS);
        assertThat(testContributor.isPeriodicContribution()).isEqualTo(DEFAULT_PERIODIC_CONTRIBUTION);
        assertThat(testContributor.getContributionMonth()).isEqualTo(DEFAULT_CONTRIBUTION_MONTH);
    }

    @Test
    @Transactional
    public void createContributorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contributorRepository.findAll().size();

        // Create the Contributor with an existing ID
        contributor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContributorMockMvc.perform(post("/api/contributors")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contributor)))
            .andExpect(status().isBadRequest());

        // Validate the Contributor in the database
        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRegistrationNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = contributorRepository.findAll().size();
        // set the field null
        contributor.setRegistrationNumber(null);

        // Create the Contributor, which fails.

        restContributorMockMvc.perform(post("/api/contributors")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contributor)))
            .andExpect(status().isBadRequest());

        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFullNameLatinIsRequired() throws Exception {
        int databaseSizeBeforeTest = contributorRepository.findAll().size();
        // set the field null
        contributor.setFullNameLatin(null);

        // Create the Contributor, which fails.

        restContributorMockMvc.perform(post("/api/contributors")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contributor)))
            .andExpect(status().isBadRequest());

        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContributors() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get all the contributorList
        restContributorMockMvc.perform(get("/api/contributors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contributor.getId().intValue())))
            .andExpect(jsonPath("$.[*].registrationNumber").value(hasItem(DEFAULT_REGISTRATION_NUMBER)))
            .andExpect(jsonPath("$.[*].fullNameArabic").value(hasItem(DEFAULT_FULL_NAME_ARABIC)))
            .andExpect(jsonPath("$.[*].fullNameLatin").value(hasItem(DEFAULT_FULL_NAME_LATIN)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].contributionStatus").value(hasItem(DEFAULT_CONTRIBUTION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].periodicContribution").value(hasItem(DEFAULT_PERIODIC_CONTRIBUTION.booleanValue())))
            .andExpect(jsonPath("$.[*].contributionMonth").value(hasItem(DEFAULT_CONTRIBUTION_MONTH)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllContributorsWithEagerRelationshipsIsEnabled() throws Exception {
        ContributorResource contributorResource = new ContributorResource(contributorServiceMock);
        when(contributorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restContributorMockMvc = MockMvcBuilders.standaloneSetup(contributorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restContributorMockMvc.perform(get("/api/contributors?eagerload=true"))
        .andExpect(status().isOk());

        verify(contributorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllContributorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        ContributorResource contributorResource = new ContributorResource(contributorServiceMock);
            when(contributorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restContributorMockMvc = MockMvcBuilders.standaloneSetup(contributorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restContributorMockMvc.perform(get("/api/contributors?eagerload=true"))
        .andExpect(status().isOk());

            verify(contributorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getContributor() throws Exception {
        // Initialize the database
        contributorRepository.saveAndFlush(contributor);

        // Get the contributor
        restContributorMockMvc.perform(get("/api/contributors/{id}", contributor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contributor.getId().intValue()))
            .andExpect(jsonPath("$.registrationNumber").value(DEFAULT_REGISTRATION_NUMBER))
            .andExpect(jsonPath("$.fullNameArabic").value(DEFAULT_FULL_NAME_ARABIC))
            .andExpect(jsonPath("$.fullNameLatin").value(DEFAULT_FULL_NAME_LATIN))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.contributionStatus").value(DEFAULT_CONTRIBUTION_STATUS.toString()))
            .andExpect(jsonPath("$.periodicContribution").value(DEFAULT_PERIODIC_CONTRIBUTION.booleanValue()))
            .andExpect(jsonPath("$.contributionMonth").value(DEFAULT_CONTRIBUTION_MONTH));
    }

    @Test
    @Transactional
    public void getNonExistingContributor() throws Exception {
        // Get the contributor
        restContributorMockMvc.perform(get("/api/contributors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContributor() throws Exception {
        // Initialize the database
        contributorService.save(contributor);

        int databaseSizeBeforeUpdate = contributorRepository.findAll().size();

        // Update the contributor
        Contributor updatedContributor = contributorRepository.findById(contributor.getId()).get();
        // Disconnect from session so that the updates on updatedContributor are not directly saved in db
        em.detach(updatedContributor);
        updatedContributor
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .fullNameArabic(UPDATED_FULL_NAME_ARABIC)
            .fullNameLatin(UPDATED_FULL_NAME_LATIN)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .contributionStatus(UPDATED_CONTRIBUTION_STATUS)
            .periodicContribution(UPDATED_PERIODIC_CONTRIBUTION)
            .contributionMonth(UPDATED_CONTRIBUTION_MONTH);

        restContributorMockMvc.perform(put("/api/contributors")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedContributor)))
            .andExpect(status().isOk());

        // Validate the Contributor in the database
        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeUpdate);
        Contributor testContributor = contributorList.get(contributorList.size() - 1);
        assertThat(testContributor.getRegistrationNumber()).isEqualTo(UPDATED_REGISTRATION_NUMBER);
        assertThat(testContributor.getFullNameArabic()).isEqualTo(UPDATED_FULL_NAME_ARABIC);
        assertThat(testContributor.getFullNameLatin()).isEqualTo(UPDATED_FULL_NAME_LATIN);
        assertThat(testContributor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContributor.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testContributor.getContributionStatus()).isEqualTo(UPDATED_CONTRIBUTION_STATUS);
        assertThat(testContributor.isPeriodicContribution()).isEqualTo(UPDATED_PERIODIC_CONTRIBUTION);
        assertThat(testContributor.getContributionMonth()).isEqualTo(UPDATED_CONTRIBUTION_MONTH);
    }

    @Test
    @Transactional
    public void updateNonExistingContributor() throws Exception {
        int databaseSizeBeforeUpdate = contributorRepository.findAll().size();

        // Create the Contributor

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContributorMockMvc.perform(put("/api/contributors")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contributor)))
            .andExpect(status().isBadRequest());

        // Validate the Contributor in the database
        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContributor() throws Exception {
        // Initialize the database
        contributorService.save(contributor);

        int databaseSizeBeforeDelete = contributorRepository.findAll().size();

        // Delete the contributor
        restContributorMockMvc.perform(delete("/api/contributors/{id}", contributor.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contributor> contributorList = contributorRepository.findAll();
        assertThat(contributorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
