import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import {HttpClient,HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
    providedIn: 'root',
 })
export class RoleService{
    private apiGetRole = `${environment.apiBaseUrl}/roles/all`;

    constructor(private http: HttpClient) { }

    getRoles():Observable<any>{
        return this.http.get<any[]>(this.apiGetRole); 
    }
      
}