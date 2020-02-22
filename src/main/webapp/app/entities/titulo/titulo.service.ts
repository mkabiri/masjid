import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITitulo } from 'app/shared/model/titulo.model';

type EntityResponseType = HttpResponse<ITitulo>;
type EntityArrayResponseType = HttpResponse<ITitulo[]>;

@Injectable({ providedIn: 'root' })
export class TituloService {
  public resourceUrl = SERVER_API_URL + 'api/titulos';

  constructor(protected http: HttpClient) {}

  create(titulo: ITitulo): Observable<EntityResponseType> {
    return this.http.post<ITitulo>(this.resourceUrl, titulo, { observe: 'response' });
  }

  update(titulo: ITitulo): Observable<EntityResponseType> {
    return this.http.put<ITitulo>(this.resourceUrl, titulo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITitulo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITitulo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
