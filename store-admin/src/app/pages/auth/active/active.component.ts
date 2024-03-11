import { Component, OnInit } from '@angular/core';
import {AuthService} from "../auth.service";
import {ActivatedRoute} from "@angular/router";
import {BaseResponse} from "../../../utils/BaseResponse";
import {NzModalService} from "ng-zorro-antd/modal";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {TranslateService} from "@ngx-translate/core";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'thd-active',
  templateUrl: './active.component.html',
  styleUrls: ['./active.component.scss']
})
export class ActiveComponent implements OnInit {

  activeToken: string;
  activeResult: BaseResponse;
  isVisible = false;
  isLoading = false;
  formGroup: FormGroup
  constructor(private autService : AuthService,
              private route : ActivatedRoute,
              private toast : ToastrService,
              private translate :TranslateService) {
    this.activeToken = this.route.snapshot.paramMap.get("token");
  }
  ngOnInit(): void {
    this.formGroup = new FormGroup({
      username: new FormControl("", Validators.required)
    })
    if (this.activeToken){
      this.autService.activeAccount(this.activeToken).subscribe(data => {
        this.activeResult = data;
      })
    }
  }
  generateToken(){
    this.isVisible =true;
  }
  handleOk(): void {
    this.isLoading = true;
    this.autService.generateActiveToken(this.formGroup.get("username").value).subscribe(data => {
      this.isLoading = false;
      if(data.code == 200){
        this.isVisible = false;
        this.toast.info(data.message,this.translate.instant("common.notification"));
        return;
      }else{
        Object.keys(data.body).forEach(key => {
          this.formGroup.controls[key].setErrors({'serverError': true, 'serverErrorMess': data.body[key]});
        });
        return;
      }
    })
  }
  getErrorMessage(control:string){
    if (this.formGroup && control) {
      if (control == "username" && this.formGroup.controls[control].errors) {
        if (this.formGroup.controls[control].errors?.['required']) {
          return this.translate.instant("common.fieldRequired");
        } else if (this.formGroup.controls[control].errors?.['serverError'] ||
          this.formGroup.controls[control].errors?.['serverErrorMess']) {
          return this.formGroup.controls[control].errors?.['serverErrorMess'];
        }
      }
    }
    return "";
  }

}
