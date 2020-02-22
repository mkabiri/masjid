import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MasjidTestModule } from '../../../test.module';
import { TituloDetailComponent } from 'app/entities/titulo/titulo-detail.component';
import { Titulo } from 'app/shared/model/titulo.model';

describe('Component Tests', () => {
  describe('Titulo Management Detail Component', () => {
    let comp: TituloDetailComponent;
    let fixture: ComponentFixture<TituloDetailComponent>;
    const route = ({ data: of({ titulo: new Titulo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MasjidTestModule],
        declarations: [TituloDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TituloDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TituloDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load titulo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.titulo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
