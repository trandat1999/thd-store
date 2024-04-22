import {Component} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidatorFn, Validators} from "@angular/forms";
import {getErrorMessage, VOIDED_CHOICE} from "../../../utils/ConstUtil";
import {TranslateService} from "@ngx-translate/core";
import {AdministrativeUnit} from "../management.model";
import {AdministrativeUnitService} from "./administrative-unit.service";
import {ApplicationConfigService} from "../../../services/application-config.service";
import {NzUploadFile} from "ng-zorro-antd/upload";

@Component({
  selector: 'thd-administrative-unit',
  templateUrl: './administrative-unit.component.html',
  styleUrls: ['./administrative-unit.component.scss']
})
export class AdministrativeUnitComponent {
  uploading= false
  deleting = false;
  isVisible= false;
  serverUrl: string = "";
  entity:AdministrativeUnit;
  formGroup: FormGroup;
  voidedList = VOIDED_CHOICE
  entities: AdministrativeUnit[] = [];
  constructor(private administrativeUnitService: AdministrativeUnitService,
              private configService: ApplicationConfigService,
              private translate : TranslateService) {
    this.serverUrl = this.configService.apiBaseUrl;
  }

  ngOnInit(): void {
    this.loadTable();
  }
  loadTable(){
    this.administrativeUnitService.getAllProvince().subscribe(data=>{
      if(data.body){
        this.entities = data.body;
      }
    })
  }
  onCreateOrUpdate(data:any){
    this.isVisible = true;
    this.entity = data?data:{}
    this.formGroup = new FormGroup({
      id : new FormControl(this.entity.id),
      name : new FormControl(this.entity.name,[Validators.required]),
      code : new FormControl(this.entity.code,[Validators.required]),
      description : new FormControl(this.entity.description),
      voided : new FormControl(this.entity.voided?this.entity.voided:false),
      parent : new FormControl(this.entity.parent),
      level : new FormControl(this.entity.level,[Validators.required,
        Validators.min(1),Validators.max(3)])
    },this.parentRequiredValidator)
  }
  parentRequiredValidator: ValidatorFn = (control: AbstractControl) => {
    let levelValue = this.formGroup?.get("level")?.value;
    let parent = this.formGroup?.get("parent");
    if(parent){
      if(levelValue>1 && !parent.value){
        parent.setValidators(Validators.required)
        parent.setErrors({required: true })
        return null;
      }
      parent.setValidators(null)
      parent.setErrors(null)
    }
    return null;
  };
  getErrorMessage(control:string):string{
    return getErrorMessage(control,this.formGroup,this.translate);
  }
  onSubmit(){
    this.administrativeUnitService.save(this.formGroup.getRawValue()).subscribe(data=>{
      if(data.code==400){
        if (data.body) {
          Object.keys(data.body).forEach(key => {
            this.formGroup.controls[key]?.setErrors({ 'serverError': true, 'serverErrorMess': data.body[key] });
          });
          return;
        }
      }else{
        this.isVisible=false;
        this.loadTable();
      }
    })
  }
  delete(id:number){
    this.deleting = true;
    this.administrativeUnitService.delete(id).subscribe(data =>{
      this.deleting = false;
      this.loadTable();
    })
  }
  handleChangeFileUpload(info: { file: NzUploadFile }): void {
    switch (info.file.status) {
      case 'uploading':
        this.uploading = true;
        break;
      case 'done':
        this.uploading = false;
        this.loadTable();
        break;
      case 'error':
        this.uploading = false;
        break;
      case "removed":
        break;
      case "success":
        this.uploading = false;
        break;
    }
  }
  toggleRow(data: AdministrativeUnit,$event: boolean){
    if($event && !(data.children && data.children.length>0)){
      this.administrativeUnitService.getAllByParent(data.id).subscribe(rs =>{
        data.children = rs.body || [];
      })
    }
  }
}
