import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrganization } from 'app/shared/model/organization.model';
import { OrganizationService } from './organization.service';
import { OrganizationDeleteDialogComponent } from './organization-delete-dialog.component';

@Component({
  selector: 'jhi-organization',
  templateUrl: './organization.component.html'
})
export class OrganizationComponent implements OnInit, OnDestroy {
  organizations?: IOrganization[];
  eventSubscriber?: Subscription;

  constructor(
    protected organizationService: OrganizationService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.organizationService.query().subscribe((res: HttpResponse<IOrganization[]>) => (this.organizations = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOrganizations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOrganization): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInOrganizations(): void {
    this.eventSubscriber = this.eventManager.subscribe('organizationListModification', () => this.loadAll());
  }

  delete(organization: IOrganization): void {
    const modalRef = this.modalService.open(OrganizationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.organization = organization;
  }
}
