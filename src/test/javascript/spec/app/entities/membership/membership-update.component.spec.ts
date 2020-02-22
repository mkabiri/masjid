import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MasjidTestModule } from '../../../test.module';
import { MembershipUpdateComponent } from 'app/entities/membership/membership-update.component';
import { MembershipService } from 'app/entities/membership/membership.service';
import { Membership } from 'app/shared/model/membership.model';

describe('Component Tests', () => {
  describe('Membership Management Update Component', () => {
    let comp: MembershipUpdateComponent;
    let fixture: ComponentFixture<MembershipUpdateComponent>;
    let service: MembershipService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MasjidTestModule],
        declarations: [MembershipUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MembershipUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MembershipUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MembershipService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Membership(123);
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
        const entity = new Membership();
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
