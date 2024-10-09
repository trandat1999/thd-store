import {Injectable} from '@angular/core';
import {BaseService} from "../../../services/base.service";

@Injectable({
  providedIn: 'root'
})
export class PermissionService {
  private readonly URL = "/api/v1/permissions";
  constructor(private base : BaseService) {
  }
  getAll() {
    return this.base.get(this.URL);
  }
  search(search: any) {
    return this.base.post(this.URL+"/pages", search);
  }
  get(id:number){
    return this.base.get(this.URL+"/"+id);
  }
  save(object:any){
    return this.base.post(this.URL,object);
  }
  update(object:any,id:number){
    return this.base.put(this.URL+"/"+id,object);
  }
  delete(id:number){
    return this.base.delete(this.URL+"/"+id);
  }
  getAllModule(){
    return this.base.get(this.URL+"/all-module");
  }
}
