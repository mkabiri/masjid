import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITitulo } from 'app/shared/model/titulo.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TituloService } from './titulo.service';
import { TituloDeleteDialogComponent } from './titulo-delete-dialog.component';

@Component({
  selector: 'jhi-titulo',
  templateUrl: './titulo.component.html'
})
export class TituloComponent implements OnInit, OnDestroy {
  titulos?: ITitulo[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  estatusFilter: any;
  paginateTitulos: any;
  registrationNumber = 1;
  fullNameLatin = 'kabiri';

  constructor(
    protected tituloService: TituloService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    /*this.tituloService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ITitulo[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );*/

    this.tituloService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        //...(this.estatusFilter && { 'estatus.equals': this.estatusFilter }),
        ...(this.registrationNumber && { 'fullNameLatin.contains': this.fullNameLatin })
      })
      .subscribe(
        (res: HttpResponse<ITitulo[]>) => this.paginateTitulos(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError() //res.message)
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInTitulos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITitulo): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTitulos(): void {
    this.eventSubscriber = this.eventManager.subscribe('tituloListModification', () => this.loadPage());
  }

  delete(titulo: ITitulo): void {
    const modalRef = this.modalService.open(TituloDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.titulo = titulo;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ITitulo[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/titulo'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.titulos = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
