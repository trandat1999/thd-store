import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../auth.service";
import {TranslateService} from "@ngx-translate/core";
import {getErrorMessage} from "../../../utils/ConstUtil";
import {Router} from "@angular/router";
import {NzModalService} from "ng-zorro-antd/modal";

@Component({
  selector: 'thd-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  passwordVisible: boolean = false
  formGroup : FormGroup;
  registerSuccess : boolean = false
  constructor(
    private authService : AuthService,
    private translate : TranslateService,
    private router: Router,
    private modal: NzModalService
  ) { }

  ngOnInit(): void {
    this.initForm();
  }
  initForm(): void {
    this.formGroup = new FormGroup({
      username: new FormControl("",[Validators.required]),
      email: new FormControl("",[Validators.required,Validators.email]),
      password: new FormControl("",[Validators.required]),
      confirmPassword: new FormControl("",[Validators.required]),
      fullName: new FormControl("",[Validators.required]),
    })
  }
  submitForm(){
    if(this.formGroup.invalid){
      Object.values(this.formGroup.controls).forEach(control => {
        if (control.invalid) {
          control.markAsDirty();
          control.updateValueAndValidity({ onlySelf: true });
        }
      });
    }
    this.authService.register(this.formGroup.value).subscribe(data => {
      if(data.code==400){
        if (data.body) {
          Object.keys(data.body).forEach(key => {
            let field = key.replace("]","");
            field = field.replace("[",".");
            this.formGroup.get(field)?.setErrors({'serverError': true, 'serverErrorMess': data.body[key]});
          });
          return;
        }
      }else{
        this.redirectLogin(data.message)
      }
    })
  }
  getErrorMessage(control:string):string{
    return getErrorMessage(control,this.formGroup,this.translate);
  }
  redirectLogin(content: string){
    this.modal.info({
      nzOnOk: ()=>{
        this.router.navigate(["/login"]);
      },
      nzTitle: this.translate.instant("common.notification"),
      nzContent: content
    })

  }
}
