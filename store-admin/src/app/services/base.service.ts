import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {TranslateService} from "@ngx-translate/core";
import { NgxSpinnerService } from 'ngx-spinner';
import {ToastrService} from "ngx-toastr";
import {catchError, map, of} from "rxjs";
import {BaseResponse} from "../utils/BaseResponse";
import {ApplicationConfigService} from "./application-config.service";

@Injectable({
  providedIn: 'root'
})
export class BaseService {

  private serverUrl
  constructor(private http : HttpClient,
              private translate : TranslateService,
              private loading: NgxSpinnerService,
              private toast : ToastrService,
              private appConfig: ApplicationConfigService) {
    this.serverUrl = this.appConfig.apiBaseUrl;
  }
  get(url:string){
    this.loading.show();
    return this.http.get(this.serverUrl+url).pipe(
      map(value => {
        this.loading.hide();
        return value as BaseResponse;
      }),catchError(error => {
        this.loading.hide();
        this.toast.error(error?.error?.message? error?.error?.message : this.translate.instant("common.commonError"),
          this.translate.instant("common.error") +(error?.error?.code? " "+error?.error?.code:""))
        return of(error)
      })
    );
  }
  delete(url: string){
    this.loading.show();
    return this.http.delete(this.serverUrl+url).pipe(
      map(value => {
        this.loading.hide();
        return value as BaseResponse;
      }),catchError(error => {
        this.loading.hide();
        this.toast.error(error?.error?.message? error.error?.message : this.translate.instant("common.commonError"), this.translate.instant("common.error") +(error?.error?.code? " "+error?.error?.code:""))
        return of(error)
      })
    );
  }
  put(url : string,request :any ){
    this.loading.show();
    return this.http.put(this.serverUrl+url, request).pipe(
      map(value => {
        this.loading.hide();
        return value as BaseResponse;
      }),catchError(error => {
        this.loading.hide();
        this.toast.error(error?.error?.message? error.error?.message : this.translate.instant("common.commonError"), this.translate.instant("common.error") +(error?.error?.code? " "+error?.error?.code:""))
        return of(error)
      })
    );
  }

  post(url : string,request :any ){
    this.loading.show();
    return this.http.post(this.serverUrl+url, request).pipe(
      map(value => {
        this.loading.hide();
        return value as BaseResponse;
      }),catchError(error => {
        console.log(error);
        this.loading.hide();
        this.toast.error(error?.error?.message? error.error?.message : this.translate.instant("common.commonError"), this.translate.instant("common.error") +(error?.error?.code? " "+error?.error?.code:""))
        return of(error)
      })
    );
  }
  search(url : string,request :any ){
    return this.http.post(this.serverUrl+url, request).pipe(
      map(value => {
        return value as BaseResponse;
      }),catchError(error => {
        console.log(error);
        this.toast.error(error?.error?.message? error.error?.message : this.translate.instant("common.commonError"), this.translate.instant("common.error") +(error?.error?.code? " "+error?.error?.code:""))
        return of(error)
      })
    );
  }
}
