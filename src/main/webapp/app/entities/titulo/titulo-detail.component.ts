import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITitulo } from 'app/shared/model/titulo.model';

@Component({
  selector: 'jhi-titulo-detail',
  templateUrl: './titulo-detail.component.html'
})
export class TituloDetailComponent implements OnInit {
  titulo: ITitulo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ titulo }) => (this.titulo = titulo));
  }

  previousState(): void {
    window.history.back();
  }
}
