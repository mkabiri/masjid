import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMembership, Membership } from 'app/shared/model/membership.model';
import { MembershipService } from './membership.service';

@Component({
  selector: 'jhi-membership-update',
  templateUrl: './membership-update.component.html'
})
export class MembershipUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    description: [],
    contributionAmount: []
  });

  constructor(protected membershipService: MembershipService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ membership }) => {
      this.updateForm(membership);
    });
  }

  updateForm(membership: IMembership): void {
    this.editForm.patchValue({
      id: membership.id,
      title: membership.title,
      description: membership.description,
      contributionAmount: membership.contributionAmount
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const membership = this.createFromForm();
    if (membership.id !== undefined) {
      this.subscribeToSaveResponse(this.membershipService.update(membership));
    } else {
      this.subscribeToSaveResponse(this.membershipService.create(membership));
    }
  }

  private createFromForm(): IMembership {
    return {
      ...new Membership(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      contributionAmount: this.editForm.get(['contributionAmount'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMembership>>): void {
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
