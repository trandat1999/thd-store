import {Injectable} from '@angular/core';
import {BaseService} from "../../../services/base.service";
import {ProductSearch} from "../../../utils/search-object";

@Injectable({
  providedIn: 'root'
})
export class ProductShowService {
  private readonly URL = "/api/v1/product-shows"
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
  save(obj:any){
    return this.base.post(this.URL,obj);
  }
  update(obj:any,id:number){
    return this.base.put(this.URL+"/"+id,obj);
  }
  delete(id:number){
    return this.base.delete(this.URL+"/"+id);
  }
}
