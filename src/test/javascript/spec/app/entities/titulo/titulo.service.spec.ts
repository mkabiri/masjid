import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TituloService } from 'app/entities/titulo/titulo.service';
import { ITitulo, Titulo } from 'app/shared/model/titulo.model';
import { EstatusTitulo } from 'app/shared/model/enumerations/estatus-titulo.model';

describe('Service Tests', () => {
  describe('Titulo Service', () => {
    let injector: TestBed;
    let service: TituloService;
    let httpMock: HttpTestingController;
    let elemDefault: ITitulo;
    let expectedResult: ITitulo | ITitulo[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TituloService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Titulo(0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', EstatusTitulo.EXPEDIDO);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Titulo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Titulo()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Titulo', () => {
        const returnedFromService = Object.assign(
          {
            registrationNumber: 1,
            fullNameArabic: 'BBBBBB',
            fullNameLatin: 'BBBBBB',
            email: 'BBBBBB',
            phoneNumber: 'BBBBBB',
            estatusTitulo: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Titulo', () => {
        const returnedFromService = Object.assign(
          {
            registrationNumber: 1,
            fullNameArabic: 'BBBBBB',
            fullNameLatin: 'BBBBBB',
            email: 'BBBBBB',
            phoneNumber: 'BBBBBB',
            estatusTitulo: 'BBBBBB'
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

      it('should delete a Titulo', () => {
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
