package com.jimprove.oms.web.rest;

import com.jimprove.oms.MasjidApp;
import com.jimprove.oms.domain.Period;
import com.jimprove.oms.repository.PeriodRepository;
import com.jimprove.oms.service.PeriodService;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.jimprove.oms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PeriodResource} REST controller.
 */
@SpringBootTest(classes = MasjidApp.class)
public class PeriodResourceIT {

    private static final String DEFAULT_YEAR = "AAAA";
    private static final String UPDATED_YEAR = "BBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PeriodRepository periodRepository;

    @Autowired
    private PeriodService periodService;

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

    private MockMvc restPeriodMockMvc;

    private Period period;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PeriodResource periodResource = new PeriodResource(periodService);
        this.restPeriodMockMvc = MockMvcBuilders.standaloneSetup(periodResource)
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
    public static Period createEntity(EntityManager em) {
        Period period = new Period()
            .year(DEFAULT_YEAR)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .description(DEFAULT_DESCRIPTION);
        return period;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Period createUpdatedEntity(EntityManager em) {
        Period period = new Period()
            .year(UPDATED_YEAR)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .description(UPDATED_DESCRIPTION);
        return period;
    }

    @BeforeEach
    public void initTest() {
        period = createEntity(em);
    }

    @Test
    @Transactional
    public void createPeriod() throws Exception {
        int databaseSizeBeforeCreate = periodRepository.findAll().size();

        // Create the Period
        restPeriodMockMvc.perform(post("/api/periods")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(period)))
            .andExpect(status().isCreated());

        // Validate the Period in the database
        List<Period> periodList = periodRepository.findAll();
        assertThat(periodList).hasSize(databaseSizeBeforeCreate + 1);
        Period testPeriod = periodList.get(periodList.size() - 1);
        assertThat(testPeriod.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testPeriod.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPeriod.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPeriod.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPeriodWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = periodRepository.findAll().size();

        // Create the Period with an existing ID
        period.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeriodMockMvc.perform(post("/api/periods")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(period)))
            .andExpect(status().isBadRequest());

        // Validate the Period in the database
        List<Period> periodList = periodRepository.findAll();
        assertThat(periodList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodRepository.findAll().size();
        // set the field null
        period.setYear(null);

        // Create the Period, which fails.

        restPeriodMockMvc.perform(post("/api/periods")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(period)))
            .andExpect(status().isBadRequest());

        List<Period> periodList = periodRepository.findAll();
        assertThat(periodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPeriods() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList
        restPeriodMockMvc.perform(get("/api/periods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(period.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getPeriod() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get the period
        restPeriodMockMvc.perform(get("/api/periods/{id}", period.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(period.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingPeriod() throws Exception {
        // Get the period
        restPeriodMockMvc.perform(get("/api/periods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePeriod() throws Exception {
        // Initialize the database
        periodService.save(period);

        int databaseSizeBeforeUpdate = periodRepository.findAll().size();

        // Update the period
        Period updatedPeriod = periodRepository.findById(period.getId()).get();
        // Disconnect from session so that the updates on updatedPeriod are not directly saved in db
        em.detach(updatedPeriod);
        updatedPeriod
            .year(UPDATED_YEAR)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .description(UPDATED_DESCRIPTION);

        restPeriodMockMvc.perform(put("/api/periods")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPeriod)))
            .andExpect(status().isOk());

        // Validate the Period in the database
        List<Period> periodList = periodRepository.findAll();
        assertThat(periodList).hasSize(databaseSizeBeforeUpdate);
        Period testPeriod = periodList.get(periodList.size() - 1);
        assertThat(testPeriod.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testPeriod.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPeriod.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPeriod.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPeriod() throws Exception {
        int databaseSizeBeforeUpdate = periodRepository.findAll().size();

        // Create the Period

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodMockMvc.perform(put("/api/periods")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(period)))
            .andExpect(status().isBadRequest());

        // Validate the Period in the database
        List<Period> periodList = periodRepository.findAll();
        assertThat(periodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePeriod() throws Exception {
        // Initialize the database
        periodService.save(period);

        int databaseSizeBeforeDelete = periodRepository.findAll().size();

        // Delete the period
        restPeriodMockMvc.perform(delete("/api/periods/{id}", period.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Period> periodList = periodRepository.findAll();
        assertThat(periodList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
