import {Injectable} from '@angular/core';
import {ACCESS_TOKEN, LANGUAGE, LANGUAGE_EN, REFRESH_TOKEN} from "../utils/ConstUtil";
import {AuthResponse} from "../pages/auth/auth.model";
@Injectable({
  providedIn: 'root'
})
export class StorageService {
  constructor() { }
  signOut(): void {
    localStorage.removeItem(ACCESS_TOKEN);
    localStorage.removeItem(REFRESH_TOKEN);
  }
  public saveToken(authResponse:AuthResponse): void {
    localStorage.setItem(ACCESS_TOKEN,authResponse.accessToken);
    localStorage.setItem(REFRESH_TOKEN,authResponse.refreshToken);
  }
  public getToken(): string | null {
    return localStorage.getItem(ACCESS_TOKEN);
  }

  public getRefreshToken(): string | null {
    return localStorage.getItem(REFRESH_TOKEN);
  }
  public setLanguage(language : string) {
    localStorage.removeItem(LANGUAGE);
    if(language) {
      localStorage.setItem(LANGUAGE,language);
    }else{
      localStorage.setItem(LANGUAGE,LANGUAGE_EN);
    }
  }
  public getLanguage() {
    let lang = localStorage.getItem(LANGUAGE);
    if(lang){
      return lang;
    }else{
      localStorage.setItem(LANGUAGE,LANGUAGE_EN);
      return LANGUAGE_EN;
    }
  }
}
