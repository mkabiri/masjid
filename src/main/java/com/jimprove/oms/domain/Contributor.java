package com.jimprove.oms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.jimprove.oms.domain.enumeration.ContributieStatus;

/**
 * A Contributor.
 */
@Entity
@Table(name = "contributor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Contributor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "registration_number", nullable = false)
    private Integer registrationNumber;

    @Column(name = "full_name_arabic")
    private String fullNameArabic;

    @NotNull
    @Column(name = "full_name_latin", nullable = false)
    private String fullNameLatin;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "contribution_status")
    private ContributieStatus contributionStatus;

    @Column(name = "periodic_contribution")
    private Boolean periodicContribution;

    @Column(name = "contribution_month")
    private Integer contributionMonth;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "contributor_membership",
               joinColumns = @JoinColumn(name = "contributor_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "membership_id", referencedColumnName = "id"))
    private Set<Membership> memberships = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("contributors")
    private Period period;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRegistrationNumber() {
        return registrationNumber;
    }

    public Contributor registrationNumber(Integer registrationNumber) {
        this.registrationNumber = registrationNumber;
        return this;
    }

    public void setRegistrationNumber(Integer registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getFullNameArabic() {
        return fullNameArabic;
    }

    public Contributor fullNameArabic(String fullNameArabic) {
        this.fullNameArabic = fullNameArabic;
        return this;
    }

    public void setFullNameArabic(String fullNameArabic) {
        this.fullNameArabic = fullNameArabic;
    }

    public String getFullNameLatin() {
        return fullNameLatin;
    }

    public Contributor fullNameLatin(String fullNameLatin) {
        this.fullNameLatin = fullNameLatin;
        return this;
    }

    public void setFullNameLatin(String fullNameLatin) {
        this.fullNameLatin = fullNameLatin;
    }

    public String getEmail() {
        return email;
    }

    public Contributor email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Contributor phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ContributieStatus getContributionStatus() {
        return contributionStatus;
    }

    public Contributor contributionStatus(ContributieStatus contributionStatus) {
        this.contributionStatus = contributionStatus;
        return this;
    }

    public void setContributionStatus(ContributieStatus contributionStatus) {
        this.contributionStatus = contributionStatus;
    }

    public Boolean isPeriodicContribution() {
        return periodicContribution;
    }

    public Contributor periodicContribution(Boolean periodicContribution) {
        this.periodicContribution = periodicContribution;
        return this;
    }

    public void setPeriodicContribution(Boolean periodicContribution) {
        this.periodicContribution = periodicContribution;
    }

    public Integer getContributionMonth() {
        return contributionMonth;
    }

    public Contributor contributionMonth(Integer contributionMonth) {
        this.contributionMonth = contributionMonth;
        return this;
    }

    public void setContributionMonth(Integer contributionMonth) {
        this.contributionMonth = contributionMonth;
    }

    public Set<Membership> getMemberships() {
        return memberships;
    }

    public Contributor memberships(Set<Membership> memberships) {
        this.memberships = memberships;
        return this;
    }

    public Contributor addMembership(Membership membership) {
        this.memberships.add(membership);
        membership.getContributors().add(this);
        return this;
    }

    public Contributor removeMembership(Membership membership) {
        this.memberships.remove(membership);
        membership.getContributors().remove(this);
        return this;
    }

    public void setMemberships(Set<Membership> memberships) {
        this.memberships = memberships;
    }

    public Period getPeriod() {
        return period;
    }

    public Contributor period(Period period) {
        this.period = period;
        return this;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contributor)) {
            return false;
        }
        return id != null && id.equals(((Contributor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Contributor{" +
            "id=" + getId() +
            ", registrationNumber=" + getRegistrationNumber() +
            ", fullNameArabic='" + getFullNameArabic() + "'" +
            ", fullNameLatin='" + getFullNameLatin() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", contributionStatus='" + getContributionStatus() + "'" +
            ", periodicContribution='" + isPeriodicContribution() + "'" +
            ", contributionMonth=" + getContributionMonth() +
            "}";
    }
}
