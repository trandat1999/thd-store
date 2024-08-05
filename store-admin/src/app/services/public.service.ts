import { Injectable } from '@angular/core';
import {BaseService} from "./base.service";

@Injectable({
  providedIn: 'root'
})
export class PublicService {

  private readonly URL = "/api/v1/publish"
  constructor(private base : BaseService) { }

  getPageProduct(search: any){
    return this.base.post(this.URL+"/products", search);
  }
}
