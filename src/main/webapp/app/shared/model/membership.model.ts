import { IContributor } from 'app/shared/model/contributor.model';

export interface IMembership {
  id?: number;
  title?: string;
  description?: string;
  contributionAmount?: number;
  contributors?: IContributor[];
}

export class Membership implements IMembership {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public contributionAmount?: number,
    public contributors?: IContributor[]
  ) {}
}
