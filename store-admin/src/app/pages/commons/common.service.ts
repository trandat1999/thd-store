import {Injectable} from '@angular/core';
import {BaseService} from "../../services/base.service";

@Injectable({
  providedIn: 'root'
})
export class CommonService {
  constructor(private base: BaseService) { }
  search(url: string,search: any) {
    return this.base.search(url, search);
  }
}
