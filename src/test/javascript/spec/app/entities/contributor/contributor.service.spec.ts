import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ContributorService } from 'app/entities/contributor/contributor.service';
import { IContributor, Contributor } from 'app/shared/model/contributor.model';
import { ContributieStatus } from 'app/shared/model/enumerations/contributie-status.model';

describe('Service Tests', () => {
  describe('Contributor Service', () => {
    let injector: TestBed;
    let service: ContributorService;
    let httpMock: HttpTestingController;
    let elemDefault: IContributor;
    let expectedResult: IContributor | IContributor[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ContributorService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Contributor(0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', ContributieStatus.OK, false, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Contributor', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Contributor()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Contributor', () => {
        const returnedFromService = Object.assign(
          {
            registrationNumber: 1,
            fullNameArabic: 'BBBBBB',
            fullNameLatin: 'BBBBBB',
            email: 'BBBBBB',
            phoneNumber: 'BBBBBB',
            contributionStatus: 'BBBBBB',
            periodicContribution: true,
            contributionMonth: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Contributor', () => {
        const returnedFromService = Object.assign(
          {
            registrationNumber: 1,
            fullNameArabic: 'BBBBBB',
            fullNameLatin: 'BBBBBB',
            email: 'BBBBBB',
            phoneNumber: 'BBBBBB',
            contributionStatus: 'BBBBBB',
            periodicContribution: true,
            contributionMonth: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Contributor', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
