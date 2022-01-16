import { Component } from '@angular/core';
import { Product } from './product';
import { ProductsService } from './products.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  
  searchValue: string;
  products: Product[];

  constructor(private producstService: ProductsService) {}

  getProducts() {
    this.products = [];
    this.producstService.get(this.searchValue).subscribe(
      response => {
        if (response.length==0) {Swal.fire({
            icon: 'warning',
            title: 'Without results',
            text: "try another word"
          })}
        this.products = response; 
      },
      error => {Swal.fire({
        icon: 'error',
        title: 'Bad request',
        text: error.error
      })}
    );
  }
}
