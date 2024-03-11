import {Component, OnInit} from '@angular/core';
import {Country} from "../setting.model";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {VOIDED_CHOICE} from "../../../utils/ConstUtil";
import {CountrySearch} from "../../../utils/search-object";
import {TranslateService} from "@ngx-translate/core";
import {CountryService} from "./country.service";
import {BreadcrumbItem} from "../../commons/breadcrumb/breadcrumb.component";

@Component({
  selector: 'thd-country',
  templateUrl: './country.component.html',
  styleUrls: ['./country.component.scss']
})
export class CountryComponent implements OnInit {
  breadcrumbs : BreadcrumbItem[] = [
    {link: null, name : this.translate.instant("settingModule.country")}
  ]
  deleting : boolean = false
  isVisible= false;
  entity:Country;
  formGroup: FormGroup;
  voidedList = VOIDED_CHOICE
  searchObject : CountrySearch ={
    pageIndex: 1,
    pageSize: 10,
  }
  totalElement = 0;
  categories: Country[] = [];
  constructor(private countryService: CountryService,
              private translate : TranslateService) { }

  ngOnInit(): void {
    // this.loadTable();
  }
  submitSearch(){
    this.searchObject.pageIndex = 1;
    this.loadTable();
  }
  loadTable(){
    let search = {...this.searchObject}
    search.pageIndex=search.pageIndex-1;
    this.countryService.search(search).subscribe(data=>{
      if(data.body){
        this.categories = data.body.content;
        this.totalElement = data.body.totalElements;
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
    })
  }
  getErrorMessage(control:string):string{
    if (this.formGroup && control) {
      if (control == "code" && this.formGroup.controls[control].errors) {
        if (this.formGroup.controls[control].errors?.['required']) {
          return this.translate.instant("common.fieldRequired");
        }
        if (this.formGroup.controls[control].errors?.['serverError'] ||
          this.formGroup.controls[control].errors?.['serverErrorMess']) {
          return this.formGroup.controls[control].errors?.['serverErrorMess'];
        }
      }
      if (control == "name" && this.formGroup.controls[control].errors) {
        if (this.formGroup.controls[control].errors?.['required']) {
          return this.translate.instant("common.fieldRequired");
        }
      }
    }
    return "";
  }
  onSubmit(){
    this.countryService.save(this.formGroup.getRawValue()).subscribe(data=>{
      if(data.code==400){
        if (data.body) {
          Object.keys(data.body).forEach(key => {
            this.formGroup.controls[key]?.setErrors({ 'serverError': true, 'serverErrorMess': data.body[key] });
          });
          return;
        }
      }else{
        this.isVisible=false;
        this.submitSearch();
      }

    })
  }
  pageChange(page:any){
    this.loadTable();
  }
  delete(id:number){
    this.deleting = true;
    this.countryService.delete(id).subscribe(data =>{
      this.deleting = false;
      this.loadTable();
    })
  }

}
