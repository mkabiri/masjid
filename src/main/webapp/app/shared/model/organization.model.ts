export interface IOrganization {
  id?: number;
  name?: string;
  address?: string;
  postalCode?: string;
  phone?: string;
  city?: string;
  bankName?: string;
  bankAccount?: string;
  logoContentType?: string;
  logo?: any;
}

export class Organization implements IOrganization {
  constructor(
    public id?: number,
    public name?: string,
    public address?: string,
    public postalCode?: string,
    public phone?: string,
    public city?: string,
    public bankName?: string,
    public bankAccount?: string,
    public logoContentType?: string,
    public logo?: any
  ) {}
}
