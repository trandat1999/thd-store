import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {getDescription, getErrorMessageValidator, PermissionAction, VOIDED_CHOICE} from "../../../utils/ConstUtil";
import {CategorySearch} from "../../../utils/search-object";
import {TranslateService} from "@ngx-translate/core";
import {Permission} from "../management.model";
import {PermissionService} from "./permission.service";

@Component({
  selector: 'thd-permission',
  templateUrl: './permission.component.html',
  styleUrls: ['./permission.component.scss']
})
export class PermissionComponent {
  deleting = false;
  isVisible= false;
  entity:Permission;
  formGroup: FormGroup;
  voidedList = VOIDED_CHOICE
  searchObject : CategorySearch ={
    pageIndex: 1,
    pageSize: 10,
  }
  totalElement = 0;
  entities: Permission[] = [];
  modules = [];
  constructor(private permissionService: PermissionService,
              private translate : TranslateService) {
    this.permissionService.getAllModule().subscribe(data => {
      this.modules = data.body;
    })
  }
  submitSearch(){
    this.searchObject.pageIndex = 1;
    this.loadTable();
  }
  loadTable(){
    let search = {...this.searchObject}
    search.pageIndex=search.pageIndex-1;
    this.permissionService.search(search).subscribe(data=>{
      if(data.body){
        this.entities = data.body.content;
        this.totalElement = data.body.totalElements;
      }
    })
  }
  onCreateOrUpdate(data:any){
    this.isVisible = true;
    this.entity = data?data:{}
    this.formGroup = new FormGroup({
      id : new FormControl(this.entity.id),
      module : new FormControl(this.entity.module,[Validators.required]),
      action : new FormControl(this.entity.action,[Validators.required]),
      voided : new FormControl(this.entity.voided?this.entity.voided:false),
    })
  }
  getErrorMessage(control:string):string{
    return getErrorMessageValidator(control,this.formGroup,this.translate);
  }
  onSubmit(){
    this.permissionService.save(this.formGroup.getRawValue()).subscribe(data=>{
      if(data.code==200 || data.code==201){
        this.isVisible=false;
        this.submitSearch();
      }else{
        if(data.code==400){
          if (data.body) {
            Object.keys(data.body).forEach(key => {
              this.formGroup.controls[key]?.setErrors({ 'serverError': true, 'serverErrorMess': data.body[key] });
            });
            return;
          }
        }
      }

    })
  }
  pageChange(page:any){
    this.loadTable();
  }
  delete(id:number){
    this.deleting = true;
    this.permissionService.delete(id).subscribe(data =>{
      this.deleting = false;
      this.loadTable();
    })
  }

  protected readonly getDescription = getDescription;
  protected readonly PermissionAction = PermissionAction;
}
