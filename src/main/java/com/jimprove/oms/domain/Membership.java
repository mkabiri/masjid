package com.jimprove.oms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Membership.
 */
@Entity
@Table(name = "membership")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Membership implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "contribution_amount")
    private Long contributionAmount;

    @ManyToMany(mappedBy = "memberships")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Contributor> contributors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Membership title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Membership description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getContributionAmount() {
        return contributionAmount;
    }

    public Membership contributionAmount(Long contributionAmount) {
        this.contributionAmount = contributionAmount;
        return this;
    }

    public void setContributionAmount(Long contributionAmount) {
        this.contributionAmount = contributionAmount;
    }

    public Set<Contributor> getContributors() {
        return contributors;
    }

    public Membership contributors(Set<Contributor> contributors) {
        this.contributors = contributors;
        return this;
    }

    public Membership addContributor(Contributor contributor) {
        this.contributors.add(contributor);
        contributor.getMemberships().add(this);
        return this;
    }

    public Membership removeContributor(Contributor contributor) {
        this.contributors.remove(contributor);
        contributor.getMemberships().remove(this);
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
        if (!(o instanceof Membership)) {
            return false;
        }
        return id != null && id.equals(((Membership) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Membership{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", contributionAmount=" + getContributionAmount() +
            "}";
    }
}
