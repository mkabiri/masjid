import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMembership } from 'app/shared/model/membership.model';
import { MembershipService } from './membership.service';
import { MembershipDeleteDialogComponent } from './membership-delete-dialog.component';

@Component({
  selector: 'jhi-membership',
  templateUrl: './membership.component.html'
})
export class MembershipComponent implements OnInit, OnDestroy {
  memberships?: IMembership[];
  eventSubscriber?: Subscription;

  constructor(protected membershipService: MembershipService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.membershipService.query().subscribe((res: HttpResponse<IMembership[]>) => (this.memberships = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMemberships();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMembership): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMemberships(): void {
    this.eventSubscriber = this.eventManager.subscribe('membershipListModification', () => this.loadAll());
  }

  delete(membership: IMembership): void {
    const modalRef = this.modalService.open(MembershipDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.membership = membership;
  }
}
