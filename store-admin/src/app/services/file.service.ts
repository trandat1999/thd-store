import { Injectable } from '@angular/core';
import {BaseService} from "./base.service";
import {HttpClient} from "@angular/common/http";
import {NgxSpinnerService} from "ngx-spinner";
import {ApplicationConfigService} from "./application-config.service";
import {catchError, map, of} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class FileService {
  private readonly url= "/api/v1/files";
  private serverUrl = this.appConfig.apiBaseUrl;
  constructor(private http : HttpClient,
              private appConfig: ApplicationConfigService,
              private loading: NgxSpinnerService) { }
  get(id:number){
    this.loading.show();
    return this.http.get(this.serverUrl+this.url+"/"+id,{responseType:"blob"}).pipe(
      map(response => {
        this.loading.hide();
        return new Blob([response]);
      }),
      catchError(err => {
        this.loading.hide();
        return of(err);
      })
    )
  }
}
