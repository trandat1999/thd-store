import { Injectable } from '@angular/core';
import {BaseService} from "../../services/base.service";
import {AuthRequest, RegisterRequest} from "./auth.model";

@Injectable({
  providedIn: 'root'
})
export class AuthService{
  private readonly apiUrl: string = "/api/v1/auth"
  constructor(private base : BaseService) {
  }
  login(request:AuthRequest){
    return this.base.post(this.apiUrl+"/login",request);
  }
  activeAccount(token:string){
    return this.base.get(this.apiUrl+"/verification/"+token);
  }
  generateActiveToken(username:string){
    return this.base.get(this.apiUrl+"/generation-active-token/"+username)
  }
  forgotPassword(request:AuthRequest){
    return this.base.post(this.apiUrl+"/forgot-password",request);
  }
  activePassword(token : string){
    return this.base.get(this.apiUrl + "/forgot-password/"+token);
  }
  generateNewToken(token : string){
    return this.base.get(this.apiUrl + "/forgot-password/new-token/"+token);
  }
  logout(){
    return this.base.get(this.apiUrl + "/logout");
  }
  refreshToken(token : string){
    return this.base.get(this.apiUrl+"/refresh-token/"+token);
  }
  register(request: RegisterRequest){
    return this.base.post(this.apiUrl+"/register",request);
  }

}
