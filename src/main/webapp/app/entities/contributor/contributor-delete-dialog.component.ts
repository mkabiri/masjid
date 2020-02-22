import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContributor } from 'app/shared/model/contributor.model';
import { ContributorService } from './contributor.service';

@Component({
  templateUrl: './contributor-delete-dialog.component.html'
})
export class ContributorDeleteDialogComponent {
  contributor?: IContributor;

  constructor(
    protected contributorService: ContributorService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contributorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('contributorListModification');
      this.activeModal.close();
    });
  }
}
