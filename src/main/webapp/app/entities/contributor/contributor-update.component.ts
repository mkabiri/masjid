import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IContributor, Contributor } from 'app/shared/model/contributor.model';
import { ContributorService } from './contributor.service';
import { IMembership } from 'app/shared/model/membership.model';
import { MembershipService } from 'app/entities/membership/membership.service';
import { IPeriod } from 'app/shared/model/period.model';
import { PeriodService } from 'app/entities/period/period.service';

type SelectableEntity = IMembership | IPeriod;

@Component({
  selector: 'jhi-contributor-update',
  templateUrl: './contributor-update.component.html'
})
export class ContributorUpdateComponent implements OnInit {
  isSaving = false;
  memberships: IMembership[] = [];
  periods: IPeriod[] = [];

  editForm = this.fb.group({
    id: [],
    registrationNumber: [null, [Validators.required]],
    fullNameArabic: [],
    fullNameLatin: [null, [Validators.required]],
    email: [],
    phoneNumber: [],
    contributionStatus: [],
    periodicContribution: [],
    contributionMonth: [],
    memberships: [],
    period: []
  });

  constructor(
    protected contributorService: ContributorService,
    protected membershipService: MembershipService,
    protected periodService: PeriodService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contributor }) => {
      this.updateForm(contributor);

      this.membershipService.query().subscribe((res: HttpResponse<IMembership[]>) => (this.memberships = res.body || []));

      this.periodService.query().subscribe((res: HttpResponse<IPeriod[]>) => (this.periods = res.body || []));
    });
  }

  updateForm(contributor: IContributor): void {
    this.editForm.patchValue({
      id: contributor.id,
      registrationNumber: contributor.registrationNumber,
      fullNameArabic: contributor.fullNameArabic,
      fullNameLatin: contributor.fullNameLatin,
      email: contributor.email,
      phoneNumber: contributor.phoneNumber,
      contributionStatus: contributor.contributionStatus,
      periodicContribution: contributor.periodicContribution,
      contributionMonth: contributor.contributionMonth,
      memberships: contributor.memberships,
      period: contributor.period
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contributor = this.createFromForm();
    if (contributor.id !== undefined) {
      this.subscribeToSaveResponse(this.contributorService.update(contributor));
    } else {
      this.subscribeToSaveResponse(this.contributorService.create(contributor));
    }
  }

  private createFromForm(): IContributor {
    return {
      ...new Contributor(),
      id: this.editForm.get(['id'])!.value,
      registrationNumber: this.editForm.get(['registrationNumber'])!.value,
      fullNameArabic: this.editForm.get(['fullNameArabic'])!.value,
      fullNameLatin: this.editForm.get(['fullNameLatin'])!.value,
      email: this.editForm.get(['email'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      contributionStatus: this.editForm.get(['contributionStatus'])!.value,
      periodicContribution: this.editForm.get(['periodicContribution'])!.value,
      contributionMonth: this.editForm.get(['contributionMonth'])!.value,
      memberships: this.editForm.get(['memberships'])!.value,
      period: this.editForm.get(['period'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContributor>>): void {
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

  getSelected(selectedVals: IMembership[], option: IMembership): IMembership {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
