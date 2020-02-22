import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITitulo, Titulo } from 'app/shared/model/titulo.model';
import { TituloService } from './titulo.service';

@Component({
  selector: 'jhi-titulo-update',
  templateUrl: './titulo-update.component.html'
})
export class TituloUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    registrationNumber: [null, [Validators.required]],
    fullNameArabic: [],
    fullNameLatin: [null, [Validators.required]],
    email: [],
    phoneNumber: [],
    estatusTitulo: []
  });

  constructor(protected tituloService: TituloService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ titulo }) => {
      this.updateForm(titulo);
    });
  }

  updateForm(titulo: ITitulo): void {
    this.editForm.patchValue({
      id: titulo.id,
      registrationNumber: titulo.registrationNumber,
      fullNameArabic: titulo.fullNameArabic,
      fullNameLatin: titulo.fullNameLatin,
      email: titulo.email,
      phoneNumber: titulo.phoneNumber,
      estatusTitulo: titulo.estatusTitulo
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const titulo = this.createFromForm();
    if (titulo.id !== undefined) {
      this.subscribeToSaveResponse(this.tituloService.update(titulo));
    } else {
      this.subscribeToSaveResponse(this.tituloService.create(titulo));
    }
  }

  private createFromForm(): ITitulo {
    return {
      ...new Titulo(),
      id: this.editForm.get(['id'])!.value,
      registrationNumber: this.editForm.get(['registrationNumber'])!.value,
      fullNameArabic: this.editForm.get(['fullNameArabic'])!.value,
      fullNameLatin: this.editForm.get(['fullNameLatin'])!.value,
      email: this.editForm.get(['email'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      estatusTitulo: this.editForm.get(['estatusTitulo'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITitulo>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
