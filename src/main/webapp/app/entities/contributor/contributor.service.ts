import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContributor } from 'app/shared/model/contributor.model';

type EntityResponseType = HttpResponse<IContributor>;
type EntityArrayResponseType = HttpResponse<IContributor[]>;

@Injectable({ providedIn: 'root' })
export class ContributorService {
  public resourceUrl = SERVER_API_URL + 'api/contributors';

  constructor(protected http: HttpClient) {}

  create(contributor: IContributor): Observable<EntityResponseType> {
    return this.http.post<IContributor>(this.resourceUrl, contributor, { observe: 'response' });
  }

  update(contributor: IContributor): Observable<EntityResponseType> {
    return this.http.put<IContributor>(this.resourceUrl, contributor, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContributor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContributor[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
