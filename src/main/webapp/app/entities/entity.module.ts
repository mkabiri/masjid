import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'contributor',
        loadChildren: () => import('./contributor/contributor.module').then(m => m.MasjidContributorModule)
      },
      {
        path: 'payment',
        loadChildren: () => import('./payment/payment.module').then(m => m.MasjidPaymentModule)
      },
      {
        path: 'period',
        loadChildren: () => import('./period/period.module').then(m => m.MasjidPeriodModule)
      },
      {
        path: 'organization',
        loadChildren: () => import('./organization/organization.module').then(m => m.MasjidOrganizationModule)
      },
      {
        path: 'titulo',
        loadChildren: () => import('./titulo/titulo.module').then(m => m.MasjidTituloModule)
      },
      {
        path: 'membership',
        loadChildren: () => import('./membership/membership.module').then(m => m.MasjidMembershipModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class MasjidEntityModule {}
