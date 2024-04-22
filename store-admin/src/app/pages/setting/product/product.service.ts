import { Injectable } from '@angular/core';
import {BaseService} from "../../../services/base.service";
import {CategorySearch, ProductSearch} from "../../../utils/search-object";
import {Category} from "../setting.model";

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private readonly URL = "/api/v1/products"
  constructor(private base : BaseService) {
  }
  getAll() {
    return this.base.get(this.URL);
  }
  search(search: ProductSearch) {
    return this.base.post(this.URL+"/pages", search);
  }
  get(id:number){
    return this.base.get(this.URL+"/"+id);
  }
  save(category:Category){
    return this.base.post(this.URL,category);
  }
  update(category:Category,id:number){
    return this.base.put(this.URL+"/"+id,category);
  }
  delete(id:number){
    return this.base.delete(this.URL+"/"+id);
  }
}
