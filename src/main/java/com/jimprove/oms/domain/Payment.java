package com.jimprove.oms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.jimprove.oms.domain.enumeration.PaymentMethod;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "payment_date")
    private Instant paymentDate;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JsonIgnoreProperties("payments")
    private Contributor contributor;

    @ManyToOne
    @JsonIgnoreProperties("payments")
    private Period period;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public Payment amount(Long amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Instant getPaymentDate() {
        return paymentDate;
    }

    public Payment paymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public void setPaymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getDescription() {
        return description;
    }

    public Payment description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public Payment paymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Contributor getContributor() {
        return contributor;
    }

    public Payment contributor(Contributor contributor) {
        this.contributor = contributor;
        return this;
    }

    public void setContributor(Contributor contributor) {
        this.contributor = contributor;
    }

    public Period getPeriod() {
        return period;
    }

    public Payment period(Period period) {
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
        if (!(o instanceof Payment)) {
            return false;
        }
        return id != null && id.equals(((Payment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Payment{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            "}";
    }
}
