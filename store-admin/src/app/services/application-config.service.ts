import { Injectable } from '@angular/core';
import {HttpBackend, HttpClient} from "@angular/common/http";
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ApplicationConfigService {
  private appConfig: any;
  private httpClient: HttpClient;

  constructor( handler: HttpBackend) {
    this.httpClient = new HttpClient(handler);
  }
  loadAppConfig() {
    return this.httpClient.get(environment.fileConfig)
      .toPromise().then(config => {
        this.appConfig = config;
      });
  }
  get apiBaseUrl(): string {
    return this.appConfig.server_url;
  }
  get googleClientID():string {
    return this.appConfig.google_client_id
  }
}
