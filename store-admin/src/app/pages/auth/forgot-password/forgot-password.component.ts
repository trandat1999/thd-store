import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../auth.service";
import {BaseResponse} from "../../../utils/BaseResponse";
import {TranslateService} from "@ngx-translate/core";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'thd-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent implements OnInit {

  token: string
  formGroup : FormGroup;
  isLoading = false;
  resultForgot : BaseResponse;
  resultActive : BaseResponse
  constructor(private authService: AuthService,
              private route: ActivatedRoute,
              private router: Router,
              private translate: TranslateService) {
    this.token = this.route.snapshot.params['token'];
  }

  ngOnInit(): void {
    if(!this.token){
      this.formGroup = new FormGroup({
        username: new FormControl("", Validators.required),
        email: new FormControl("", [Validators.required,Validators.email])
      });
    }
    if (this.token){
      this.authService.activePassword(this.token).subscribe(data => {
        this.resultActive = data;
      })
    }

  }
  onSubmit(): void {
    this.isLoading = true;
    this.authService.forgotPassword(this.formGroup.value).subscribe(data => {
      this.isLoading = false;
      this.resultForgot = data;
    })
  }
  getErrorMessage(control:string){
    if (this.formGroup && control) {
      if (control == "username" && this.formGroup.controls[control].errors) {
        if (this.formGroup.controls[control].errors?.['required']) {
          return this.translate.instant("common.fieldRequired");
        }
      }
      if (control == "email" && this.formGroup.controls[control].errors) {
        if (this.formGroup.controls[control].errors?.['required']) {
          return this.translate.instant("common.fieldRequired");
        }
        if (this.formGroup.controls[control].errors?.['email']) {
          return this.translate.instant("common.fieldInvalid");
        }
      }
    }
    return "";
  }
  generateNewPassword(){
    this.authService.generateNewToken(this.token).subscribe(data=>{
      this.resultActive =  data;
    })
  }
  login(){
    this.router.navigate(['/login']);
  }
}
