import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MasjidSharedModule } from 'app/shared/shared.module';
import { ContributorComponent } from './contributor.component';
import { ContributorDetailComponent } from './contributor-detail.component';
import { ContributorUpdateComponent } from './contributor-update.component';
import { ContributorDeleteDialogComponent } from './contributor-delete-dialog.component';
import { contributorRoute } from './contributor.route';

@NgModule({
  imports: [MasjidSharedModule, RouterModule.forChild(contributorRoute)],
  declarations: [ContributorComponent, ContributorDetailComponent, ContributorUpdateComponent, ContributorDeleteDialogComponent],
  entryComponents: [ContributorDeleteDialogComponent]
})
export class MasjidContributorModule {}
