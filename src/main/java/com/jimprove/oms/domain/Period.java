package com.jimprove.oms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Period.
 */
@Entity
@Table(name = "period")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Period implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 4, max = 4)
    @Column(name = "year", length = 4, nullable = false)
    private String year;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "period")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Contributor> contributors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public Period year(String year) {
        this.year = year;
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Period startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public Period endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public Period description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Contributor> getContributors() {
        return contributors;
    }

    public Period contributors(Set<Contributor> contributors) {
        this.contributors = contributors;
        return this;
    }

    public Period addContributor(Contributor contributor) {
        this.contributors.add(contributor);
        contributor.setPeriod(this);
        return this;
    }

    public Period removeContributor(Contributor contributor) {
        this.contributors.remove(contributor);
        contributor.setPeriod(null);
        return this;
    }

    public void setContributors(Set<Contributor> contributors) {
        this.contributors = contributors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Period)) {
            return false;
        }
        return id != null && id.equals(((Period) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Period{" +
            "id=" + getId() +
            ", year='" + getYear() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
