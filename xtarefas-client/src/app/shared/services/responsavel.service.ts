import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Table } from 'primeng';
import { Observable } from 'rxjs';
import { ResponsavelDocument } from 'src/app/models/responsavel';
import { environment } from 'src/environments/environment';
import { Filter } from '../filters/filter';
import { Page } from '../page';
import { RequestUtil } from '../request-util';

@Injectable({
    providedIn: 'root'
})
export class ResponsavelService {

    url = environment.apiUrl + '/responsaveis';

    constructor(private httpClient: HttpClient) { }

    search(responsavelFilter: Filter, table: Table): Observable<Page<ResponsavelDocument>> {
        return this.httpClient.post<Page<ResponsavelDocument>>(this.url + '/_search', responsavelFilter, {
            params: RequestUtil.getRequestParamsTable(table)
        });
    }
}
