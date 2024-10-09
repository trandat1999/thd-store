import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../auth.service";
import {StorageService} from "../../../services/storage.service";
import {Router} from "@angular/router";
import {getErrorMessageValidator} from "../../../utils/ConstUtil";
import {TranslateService} from "@ngx-translate/core";
import {SocialAuthService, SocialUser} from "@abacritt/angularx-social-login";

@Component({
  selector: 'thd-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  constructor(private authService: AuthService,
              private router: Router,
              private translate: TranslateService,
              private socialAuthService: SocialAuthService,
              private storageService: StorageService) {
  }
  ngOnInit(): void {
    this.formGroup = new FormGroup({
      username: new FormControl("",[Validators.required]),
      password: new FormControl("",[Validators.required]),
    })
    this.socialAuthService.authState.subscribe(socialUser => {
      if(socialUser){
        let rs = socialUser as SocialUser;
        localStorage.setItem('google_auth_token', rs.authToken);
        this.authService.loginGoogle(rs).subscribe(data => {
          if(data.code==200){
            this.storageService.saveToken(data.body);
            this.router.navigate(["/welcome"])
          }
        })
      }
    })
  }
  formGroup : FormGroup;
  submitForm(): void {
    if (!this.formGroup.valid) {
      Object.values(this.formGroup.controls).forEach(control => {
        if (control.invalid) {
          control.markAsDirty();
          control.updateValueAndValidity({ onlySelf: true });
        }
      });
    }
    this.authService.login(this.formGroup.value).subscribe(data => {
      if(data.code==200){
        this.storageService.saveToken(data.body);
        this.router.navigate(["/welcome"])
      }
    })
  }
  getErrorMessage(control : string): string {
    return getErrorMessageValidator(control,this.formGroup,this.translate)
  }
}
