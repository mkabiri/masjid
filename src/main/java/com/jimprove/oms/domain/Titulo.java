package com.jimprove.oms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.jimprove.oms.domain.enumeration.EstatusTitulo;

/**
 * A Titulo.
 */
@Entity
@Table(name = "titulo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Titulo implements Serializable {

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
    @Column(name = "estatus_titulo")
    private EstatusTitulo estatusTitulo;

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

    public Titulo registrationNumber(Integer registrationNumber) {
        this.registrationNumber = registrationNumber;
        return this;
    }

    public void setRegistrationNumber(Integer registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getFullNameArabic() {
        return fullNameArabic;
    }

    public Titulo fullNameArabic(String fullNameArabic) {
        this.fullNameArabic = fullNameArabic;
        return this;
    }

    public void setFullNameArabic(String fullNameArabic) {
        this.fullNameArabic = fullNameArabic;
    }

    public String getFullNameLatin() {
        return fullNameLatin;
    }

    public Titulo fullNameLatin(String fullNameLatin) {
        this.fullNameLatin = fullNameLatin;
        return this;
    }

    public void setFullNameLatin(String fullNameLatin) {
        this.fullNameLatin = fullNameLatin;
    }

    public String getEmail() {
        return email;
    }

    public Titulo email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Titulo phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public EstatusTitulo getEstatusTitulo() {
        return estatusTitulo;
    }

    public Titulo estatusTitulo(EstatusTitulo estatusTitulo) {
        this.estatusTitulo = estatusTitulo;
        return this;
    }

    public void setEstatusTitulo(EstatusTitulo estatusTitulo) {
        this.estatusTitulo = estatusTitulo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Titulo)) {
            return false;
        }
        return id != null && id.equals(((Titulo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Titulo{" +
            "id=" + getId() +
            ", registrationNumber=" + getRegistrationNumber() +
            ", fullNameArabic='" + getFullNameArabic() + "'" +
            ", fullNameLatin='" + getFullNameLatin() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", estatusTitulo='" + getEstatusTitulo() + "'" +
            "}";
    }
}
