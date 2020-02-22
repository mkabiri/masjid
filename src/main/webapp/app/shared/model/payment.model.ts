import { Moment } from 'moment';
import { IContributor } from 'app/shared/model/contributor.model';
import { IPeriod } from 'app/shared/model/period.model';
import { PaymentMethod } from 'app/shared/model/enumerations/payment-method.model';

export interface IPayment {
  id?: number;
  amount?: number;
  paymentDate?: Moment;
  description?: string;
  paymentMethod?: PaymentMethod;
  contributor?: IContributor;
  period?: IPeriod;
}

export class Payment implements IPayment {
  constructor(
    public id?: number,
    public amount?: number,
    public paymentDate?: Moment,
    public description?: string,
    public paymentMethod?: PaymentMethod,
    public contributor?: IContributor,
    public period?: IPeriod
  ) {}
}
