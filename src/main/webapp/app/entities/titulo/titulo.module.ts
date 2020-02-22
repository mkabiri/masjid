import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MasjidSharedModule } from 'app/shared/shared.module';
import { TituloComponent } from './titulo.component';
import { TituloDetailComponent } from './titulo-detail.component';
import { TituloUpdateComponent } from './titulo-update.component';
import { TituloDeleteDialogComponent } from './titulo-delete-dialog.component';
import { tituloRoute } from './titulo.route';

@NgModule({
  imports: [MasjidSharedModule, RouterModule.forChild(tituloRoute)],
  declarations: [TituloComponent, TituloDetailComponent, TituloUpdateComponent, TituloDeleteDialogComponent],
  entryComponents: [TituloDeleteDialogComponent]
})
export class MasjidTituloModule {}
