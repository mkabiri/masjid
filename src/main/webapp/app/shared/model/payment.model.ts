import { Moment } from 'moment';
import { PaymentMethod } from 'app/shared/model/enumerations/payment-method.model';

export interface IPayment {
  id?: number;
  amount?: number;
  paymentDate?: Moment;
  description?: string;
  paymentMethod?: PaymentMethod;
  contributorId?: number;
  periodYear?: string;
  periodId?: number;
}

export class Payment implements IPayment {
  constructor(
    public id?: number,
    public amount?: number,
    public paymentDate?: Moment,
    public description?: string,
    public paymentMethod?: PaymentMethod,
    public contributorId?: number,
    public periodYear?: string,
    public periodId?: number
  ) {}
}
