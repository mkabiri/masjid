import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPeriod } from 'app/shared/model/period.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PeriodService } from './period.service';
import { PeriodDeleteDialogComponent } from './period-delete-dialog.component';

@Component({
  selector: 'jhi-period',
  templateUrl: './period.component.html'
})
export class PeriodComponent implements OnInit, OnDestroy {
  periods: IPeriod[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected periodService: PeriodService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.periods = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.periodService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IPeriod[]>) => this.paginatePeriods(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.periods = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPeriods();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPeriod): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPeriods(): void {
    this.eventSubscriber = this.eventManager.subscribe('periodListModification', () => this.reset());
  }

  delete(period: IPeriod): void {
    const modalRef = this.modalService.open(PeriodDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.period = period;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginatePeriods(data: IPeriod[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.periods.push(data[i]);
      }
    }
  }
}
