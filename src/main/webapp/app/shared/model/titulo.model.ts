import { EstatusTitulo } from 'app/shared/model/enumerations/estatus-titulo.model';

export interface ITitulo {
  id?: number;
  registrationNumber?: number;
  fullNameArabic?: string;
  fullNameLatin?: string;
  email?: string;
  phoneNumber?: string;
  estatusTitulo?: EstatusTitulo;
}

export class Titulo implements ITitulo {
  constructor(
    public id?: number,
    public registrationNumber?: number,
    public fullNameArabic?: string,
    public fullNameLatin?: string,
    public email?: string,
    public phoneNumber?: string,
    public estatusTitulo?: EstatusTitulo
  ) {}
}
