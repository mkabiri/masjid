import { IMembership } from 'app/shared/model/membership.model';
import { IPeriod } from 'app/shared/model/period.model';
import { ContributieStatus } from 'app/shared/model/enumerations/contributie-status.model';

export interface IContributor {
  id?: number;
  registrationNumber?: number;
  fullNameArabic?: string;
  fullNameLatin?: string;
  email?: string;
  phoneNumber?: string;
  contributionStatus?: ContributieStatus;
  periodicContribution?: boolean;
  contributionMonth?: number;
  memberships?: IMembership[];
  period?: IPeriod;
}

export class Contributor implements IContributor {
  constructor(
    public id?: number,
    public registrationNumber?: number,
    public fullNameArabic?: string,
    public fullNameLatin?: string,
    public email?: string,
    public phoneNumber?: string,
    public contributionStatus?: ContributieStatus,
    public periodicContribution?: boolean,
    public contributionMonth?: number,
    public memberships?: IMembership[],
    public period?: IPeriod
  ) {
    this.periodicContribution = this.periodicContribution || false;
  }
}
