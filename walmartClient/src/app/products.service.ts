import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Product } from './product';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  private serviceUrl: string;

  constructor(private http: HttpClient) {
    this.serviceUrl = 'http://localhost:8080/api/v1.0/products/{value}';
  }

  public get(value: string): Observable<Product[]> {
    return this.http.get<Product[]>(this.serviceUrl.replace("{value}", value));
  }
}
