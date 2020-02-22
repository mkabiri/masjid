import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPayment, Payment } from 'app/shared/model/payment.model';
import { PaymentService } from './payment.service';
import { IContributor } from 'app/shared/model/contributor.model';
import { ContributorService } from 'app/entities/contributor/contributor.service';
import { IPeriod } from 'app/shared/model/period.model';
import { PeriodService } from 'app/entities/period/period.service';

type SelectableEntity = IContributor | IPeriod;

@Component({
  selector: 'jhi-payment-update',
  templateUrl: './payment-update.component.html'
})
export class PaymentUpdateComponent implements OnInit {
  isSaving = false;
  contributors: IContributor[] = [];
  periods: IPeriod[] = [];

  editForm = this.fb.group({
    id: [],
    amount: [null, [Validators.required]],
    paymentDate: [],
    description: [],
    paymentMethod: [],
    contributor: [],
    period: []
  });

  constructor(
    protected paymentService: PaymentService,
    protected contributorService: ContributorService,
    protected periodService: PeriodService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ payment }) => {
      if (!payment.id) {
        const today = moment().startOf('day');
        payment.paymentDate = today;
      }

      this.updateForm(payment);

      this.contributorService.query().subscribe((res: HttpResponse<IContributor[]>) => (this.contributors = res.body || []));

      this.periodService.query().subscribe((res: HttpResponse<IPeriod[]>) => (this.periods = res.body || []));
    });
  }

  updateForm(payment: IPayment): void {
    this.editForm.patchValue({
      id: payment.id,
      amount: payment.amount,
      paymentDate: payment.paymentDate ? payment.paymentDate.format(DATE_TIME_FORMAT) : null,
      description: payment.description,
      paymentMethod: payment.paymentMethod,
      contributor: payment.contributor,
      period: payment.period
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const payment = this.createFromForm();
    if (payment.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentService.update(payment));
    } else {
      this.subscribeToSaveResponse(this.paymentService.create(payment));
    }
  }

  private createFromForm(): IPayment {
    return {
      ...new Payment(),
      id: this.editForm.get(['id'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      paymentDate: this.editForm.get(['paymentDate'])!.value
        ? moment(this.editForm.get(['paymentDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      description: this.editForm.get(['description'])!.value,
      paymentMethod: this.editForm.get(['paymentMethod'])!.value,
      contributor: this.editForm.get(['contributor'])!.value,
      period: this.editForm.get(['period'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPayment>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
