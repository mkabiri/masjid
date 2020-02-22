import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMembership } from 'app/shared/model/membership.model';
import { MembershipService } from './membership.service';

@Component({
  templateUrl: './membership-delete-dialog.component.html'
})
export class MembershipDeleteDialogComponent {
  membership?: IMembership;

  constructor(
    protected membershipService: MembershipService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.membershipService.delete(id).subscribe(() => {
      this.eventManager.broadcast('membershipListModification');
      this.activeModal.close();
    });
  }
}
