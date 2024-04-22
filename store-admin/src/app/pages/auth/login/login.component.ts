import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../auth.service";
import {StorageService} from "../../../services/storage.service";
import {Router} from "@angular/router";
import {getErrorMessage} from "../../../utils/ConstUtil";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'thd-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  constructor(private authService: AuthService,
              private router: Router,
              private translate: TranslateService,
              private storageService: StorageService) {
  }
  ngOnInit(): void {
    this.formGroup = new FormGroup({
      username: new FormControl("",[Validators.required]),
      password: new FormControl("",[Validators.required]),
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
    return getErrorMessage(control,this.formGroup,this.translate)
  }
}
