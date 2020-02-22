import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MasjidTestModule } from '../../../test.module';
import { ContributorUpdateComponent } from 'app/entities/contributor/contributor-update.component';
import { ContributorService } from 'app/entities/contributor/contributor.service';
import { Contributor } from 'app/shared/model/contributor.model';

describe('Component Tests', () => {
  describe('Contributor Management Update Component', () => {
    let comp: ContributorUpdateComponent;
    let fixture: ComponentFixture<ContributorUpdateComponent>;
    let service: ContributorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MasjidTestModule],
        declarations: [ContributorUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ContributorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContributorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContributorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Contributor(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Contributor();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
