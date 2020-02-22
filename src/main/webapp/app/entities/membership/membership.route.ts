import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMembership, Membership } from 'app/shared/model/membership.model';
import { MembershipService } from './membership.service';
import { MembershipComponent } from './membership.component';
import { MembershipDetailComponent } from './membership-detail.component';
import { MembershipUpdateComponent } from './membership-update.component';

@Injectable({ providedIn: 'root' })
export class MembershipResolve implements Resolve<IMembership> {
  constructor(private service: MembershipService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMembership> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((membership: HttpResponse<Membership>) => {
          if (membership.body) {
            return of(membership.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Membership());
  }
}

export const membershipRoute: Routes = [
  {
    path: '',
    component: MembershipComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'masjidApp.membership.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MembershipDetailComponent,
    resolve: {
      membership: MembershipResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'masjidApp.membership.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MembershipUpdateComponent,
    resolve: {
      membership: MembershipResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'masjidApp.membership.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MembershipUpdateComponent,
    resolve: {
      membership: MembershipResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'masjidApp.membership.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
