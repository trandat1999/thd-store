import { Injectable } from '@angular/core';
import {BaseService} from "./base.service";
import {HttpClient} from "@angular/common/http";
import {ApplicationConfigService} from "./application-config.service";

@Injectable({
  providedIn: 'root'
})
export class RootService {
  public currentUser: any;
  readonly URL_API = "/api/v1/users/current"
  constructor(private http : HttpClient,
              private configService: ApplicationConfigService) { }
  getCurrentUser(){
    return this.http.get(this.configService.apiBaseUrl+this.URL_API);
  }
}
