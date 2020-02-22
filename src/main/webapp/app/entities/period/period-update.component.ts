import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPeriod, Period } from 'app/shared/model/period.model';
import { PeriodService } from './period.service';

@Component({
  selector: 'jhi-period-update',
  templateUrl: './period-update.component.html'
})
export class PeriodUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    year: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(4)]],
    startDate: [],
    endDate: [],
    description: []
  });

  constructor(protected periodService: PeriodService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ period }) => {
      if (!period.id) {
        const today = moment().startOf('day');
        period.startDate = today;
        period.endDate = today;
      }

      this.updateForm(period);
    });
  }

  updateForm(period: IPeriod): void {
    this.editForm.patchValue({
      id: period.id,
      year: period.year,
      startDate: period.startDate ? period.startDate.format(DATE_TIME_FORMAT) : null,
      endDate: period.endDate ? period.endDate.format(DATE_TIME_FORMAT) : null,
      description: period.description
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const period = this.createFromForm();
    if (period.id !== undefined) {
      this.subscribeToSaveResponse(this.periodService.update(period));
    } else {
      this.subscribeToSaveResponse(this.periodService.create(period));
    }
  }

  private createFromForm(): IPeriod {
    return {
      ...new Period(),
      id: this.editForm.get(['id'])!.value,
      year: this.editForm.get(['year'])!.value,
      startDate: this.editForm.get(['startDate'])!.value ? moment(this.editForm.get(['startDate'])!.value, DATE_TIME_FORMAT) : undefined,
      endDate: this.editForm.get(['endDate'])!.value ? moment(this.editForm.get(['endDate'])!.value, DATE_TIME_FORMAT) : undefined,
      description: this.editForm.get(['description'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPeriod>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
