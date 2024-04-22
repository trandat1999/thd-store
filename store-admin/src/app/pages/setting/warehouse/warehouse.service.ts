import { Injectable } from '@angular/core';
import {BaseService} from "../../../services/base.service";
import {CategorySearch} from "../../../utils/search-object";
import {Warehouse} from "../setting.model";

@Injectable({
  providedIn: 'root'
})
export class WarehouseService {
  private readonly URL = "/api/v1/warehouses"
  constructor(private base : BaseService) {
  }
  getAll() {
    return this.base.get(this.URL);
  }
  search(search: CategorySearch) {
    return this.base.post(this.URL+"/pages", search);
  }
  get(id:number){
    return this.base.get(this.URL+"/"+id);
  }
  save(entity:Warehouse){
    return this.base.post(this.URL,entity);
  }
  update(entity:Warehouse,id:number){
    return this.base.put(this.URL+"/"+id,entity);
  }
  delete(id:number){
    return this.base.delete(this.URL+"/"+id);
  }
}
