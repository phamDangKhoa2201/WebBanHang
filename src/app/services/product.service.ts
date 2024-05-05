import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../../models/product';
@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private apiGetAllProducts = `${environment.apiBaseUrl}/products`;
  constructor(private http: HttpClient) { }


  getProducts( 
    keyword:string, categoryId: number,
    page: number, limit: number
  ): Observable<Product[]> {
    const params = {
      keyword: keyword,
      category_id: categoryId.toString(),
      page: page.toString(),
      limit: limit.toString()};
    return this.http.get<Product[]>(this.apiGetAllProducts, { params });
  }
}