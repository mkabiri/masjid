package com.jimprove.oms.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

public class TituloCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;
    private IntegerFilter registrationNumber;
    private StringFilter fullNameArabic;
    private StringFilter fullNameLatin;

    public TituloCriteria() {
    }

    public TituloCriteria(TituloCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.registrationNumber = other.registrationNumber == null ? null : other.registrationNumber.copy();
        this.fullNameArabic = other.fullNameArabic == null ? null : other.fullNameArabic.copy();
        this.fullNameLatin = other.fullNameLatin == null ? null : other.fullNameLatin.copy();

    }

    @Override
    public TituloCriteria copy() {
        return new TituloCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(IntegerFilter registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public StringFilter getFullNameArabic() {
        return fullNameArabic;
    }

    public void setFullNameArabic(StringFilter fullNameArabic) {
        this.fullNameArabic = fullNameArabic;
    }

    public StringFilter getFullNameLatin() {
        return fullNameLatin;
    }

    public void setFullNameLatin(StringFilter fullNameLatin) {
        this.fullNameLatin = fullNameLatin;
    }
}
