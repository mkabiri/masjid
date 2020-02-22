import { Moment } from 'moment';
import { IContributor } from 'app/shared/model/contributor.model';

export interface IPeriod {
  id?: number;
  year?: string;
  startDate?: Moment;
  endDate?: Moment;
  description?: string;
  contributors?: IContributor[];
}

export class Period implements IPeriod {
  constructor(
    public id?: number,
    public year?: string,
    public startDate?: Moment,
    public endDate?: Moment,
    public description?: string,
    public contributors?: IContributor[]
  ) {}
}
