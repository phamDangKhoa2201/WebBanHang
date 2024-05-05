import { Component, OnInit } from '@angular/core';
import { FooterComponent } from "../footer/footer.component";
import { HeaderComponent } from "../header/header.component";
import { Product } from '../../../models/product';
import { Category } from '../../../models/category';
import { ProductService } from '../../services/product.service';
import { environment } from '../../environments/environment';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { CategoryService } from '../../services/category.service';
import { TokenService } from '../../services/token.service';

@Component({
    selector: 'app-home',
    standalone: true,
    templateUrl: './home.component.html',
    styleUrl: './home.component.scss',
    imports: [FooterComponent, HeaderComponent, CommonModule, FormsModule]
})
export class HomeComponent implements OnInit {
    products: Product[] = [];
    categories: Category[] = []; // Dữ liệu động từ categoryService
    selectedCategoryId: number  = 0; // Giá trị category được chọn
    currentPage: number = 0;
    itemsPerPage: number = 12;
    page: number[] = [];
    totalPages: number = 0;
    visiblePages: number[] = [];
    keyword: string = "";
    constructor(
        private productService: ProductService,
        private categoryService: CategoryService,
        private tokenService: TokenService,
        private router: Router
    ) { }
    ngOnInit(): void {
        if (typeof localStorage !== 'undefined') {
            this.currentPage = Number(localStorage.getItem('currentProductPage')) || 0; 
            this.getProducts(this.keyword, this.selectedCategoryId,this.currentPage, this.itemsPerPage);
        }
    }
    searchProducts(){
        this.currentPage =1;
        this.itemsPerPage = 12;
        debugger
        this.getProducts(this.keyword, this.selectedCategoryId, this.currentPage, this.itemsPerPage)
    }
    getProducts(keyword:string, selectedCategoryId: number,page: number, limit: number) {
        debugger
        this.productService.getProducts(keyword,selectedCategoryId,page, limit).subscribe({
            next: (response: any) => {
                debugger
                response.products.forEach((product: Product) => {
                    product.url = `${environment.apiBaseUrl}/products/images/${product.thumbnail}`;
                });
                this.products = response.products;
                this.totalPages = response.totalPages;
                this.visiblePages = this.generateVisiblePageArray(this.currentPage, this.totalPages);
            },
            complete: () => {
                debugger;
            },
            error: (error: any) => {
                debugger;
                console.error('Error fetching products:', error);
            }
        });
    }
    onPageChange(page: number) {
        debugger;
        this.currentPage = page;
        this.getProducts(this.keyword, this.selectedCategoryId,this.currentPage, this.itemsPerPage);
    }
    generateVisiblePageArray(currentPage: number, totalPages: number): number[] {
        const maxVisiblePages = 5;
        const halfVisiblePages = Math.floor(maxVisiblePages / 2);

        let startPage = Math.max(currentPage - halfVisiblePages, 1);
        let endPage = Math.min(startPage + maxVisiblePages - 1, totalPages);

        if (endPage - startPage + 1 < maxVisiblePages) {
            startPage = Math.max(endPage - maxVisiblePages + 1, 1);
        }

        return new Array(maxVisiblePages).fill(0)
            .map((_, index) => startPage + index);
    }
    // Hàm xử lý sự kiện khi sản phẩm được bấm vào
    onProductClick(productId: number) {
        debugger
        // Điều hướng đến trang detail-product với productId là tham số
        this.router.navigate(['/detail-product', productId]);
    }

}
