import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITitulo, Titulo } from 'app/shared/model/titulo.model';
import { TituloService } from './titulo.service';
import { TituloComponent } from './titulo.component';
import { TituloDetailComponent } from './titulo-detail.component';
import { TituloUpdateComponent } from './titulo-update.component';

@Injectable({ providedIn: 'root' })
export class TituloResolve implements Resolve<ITitulo> {
  constructor(private service: TituloService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITitulo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((titulo: HttpResponse<Titulo>) => {
          if (titulo.body) {
            return of(titulo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Titulo());
  }
}

export const tituloRoute: Routes = [
  {
    path: '',
    component: TituloComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'masjidApp.titulo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TituloDetailComponent,
    resolve: {
      titulo: TituloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'masjidApp.titulo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TituloUpdateComponent,
    resolve: {
      titulo: TituloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'masjidApp.titulo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TituloUpdateComponent,
    resolve: {
      titulo: TituloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'masjidApp.titulo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
