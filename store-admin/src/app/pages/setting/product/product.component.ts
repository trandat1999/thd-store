import { Component } from '@angular/core';
import {Category, Product} from "../setting.model";
import {AbstractControl, FormControl, FormGroup, ValidatorFn, Validators} from "@angular/forms";
import {VOIDED_CHOICE, getErrorMessageValidator} from "../../../utils/ConstUtil";
import {CategorySearch} from "../../../utils/search-object";
import {TranslateService} from "@ngx-translate/core";
import {ProductService} from "./product.service";
import {Router} from "@angular/router";

@Component({
  selector: 'thd-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent {
  deleting = false;
  isVisible= false;
  entity:Category;
  formGroup: FormGroup;
  searchObject : CategorySearch ={
    pageIndex: 1,
    pageSize: 10,
  }
  totalElement = 0;
  categories: Category[] = [];
  constructor(private productService: ProductService,
              private router : Router,
              private translate : TranslateService) { }

  ngOnInit(): void {
    // this.loadTable();
  }
  submitSearch(){
    this.searchObject.pageIndex = 1;
    this.loadTable();
  }
  loadTable(){
    let search = {...this.searchObject}
    search.pageIndex=search.pageIndex-1;
    this.productService.search(search).subscribe(data=>{
      if(data.body){
        this.categories = data.body.content;
        this.totalElement = data.body.totalElements;
      }
    })
  }
  onCreateOrUpdate(data:Product){
    if(data && data.id){
      this.router.navigate([this.router.url+"/"+data.id]);
      return;
    }
    this.router.navigate([this.router.url+"/create"]);
  }

  pageChange(page:any){
    this.loadTable();
  }
  delete(id:number){
    this.deleting = true;
    this.productService.delete(id).subscribe(data =>{
      this.deleting = false;
      this.loadTable();
    })
  }
}
