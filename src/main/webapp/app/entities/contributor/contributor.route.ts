import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IContributor, Contributor } from 'app/shared/model/contributor.model';
import { ContributorService } from './contributor.service';
import { ContributorComponent } from './contributor.component';
import { ContributorDetailComponent } from './contributor-detail.component';
import { ContributorUpdateComponent } from './contributor-update.component';

@Injectable({ providedIn: 'root' })
export class ContributorResolve implements Resolve<IContributor> {
  constructor(private service: ContributorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContributor> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((contributor: HttpResponse<Contributor>) => {
          if (contributor.body) {
            return of(contributor.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Contributor());
  }
}

export const contributorRoute: Routes = [
  {
    path: '',
    component: ContributorComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'masjidApp.contributor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ContributorDetailComponent,
    resolve: {
      contributor: ContributorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'masjidApp.contributor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ContributorUpdateComponent,
    resolve: {
      contributor: ContributorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'masjidApp.contributor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ContributorUpdateComponent,
    resolve: {
      contributor: ContributorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'masjidApp.contributor.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
