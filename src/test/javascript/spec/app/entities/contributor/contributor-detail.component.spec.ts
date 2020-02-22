import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MasjidTestModule } from '../../../test.module';
import { ContributorDetailComponent } from 'app/entities/contributor/contributor-detail.component';
import { Contributor } from 'app/shared/model/contributor.model';

describe('Component Tests', () => {
  describe('Contributor Management Detail Component', () => {
    let comp: ContributorDetailComponent;
    let fixture: ComponentFixture<ContributorDetailComponent>;
    const route = ({ data: of({ contributor: new Contributor(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MasjidTestModule],
        declarations: [ContributorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ContributorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContributorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load contributor on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contributor).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
