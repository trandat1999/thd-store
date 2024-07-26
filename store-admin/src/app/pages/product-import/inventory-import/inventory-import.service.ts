import {Injectable} from '@angular/core';
import {BaseService} from "../../../services/base.service";
import {CategorySearch} from "../../../utils/search-object";

@Injectable({
  providedIn: 'root'
})
export class InventoryImportService {
  private readonly URL = "/api/v1/invoice-imports"
  constructor(private base : BaseService) {
  }
  search(search: CategorySearch) {
    return this.base.post(this.URL+"/pages", search);
  }
  get(id:number){
    return this.base.get(this.URL+"/"+id);
  }
  getByCode(code:string){
    return this.base.get(this.URL+"/code/"+code);
  }
  save(entity:any){
    return this.base.post(this.URL,entity);
  }
  update(entity:any,id:number){
    return this.base.put(this.URL+"/"+id,entity);
  }
  delete(id:number){
    return this.base.delete(this.URL+"/"+id);
  }
  changeStatus(code:string,status: 'order'|'approve' | 'paid' | 'ship' | 'warehoused'){
    return this.base.get(this.URL+"/"+code+"/"+status);
  }
  getLastPriceImported(id: number){
    return this.base.get(this.URL+"/last-price-imported/"+id);
  }
}
