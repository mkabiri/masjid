import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MasjidSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [MasjidSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent]
})
export class MasjidHomeModule {}
