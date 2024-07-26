import {Component, OnInit} from '@angular/core';
import {Category} from "../setting.model";
import {CategoryService} from "./category.service";
import {CategorySearch} from "../../../utils/search-object";
import {AbstractControl, FormControl, FormGroup, ValidatorFn, Validators} from "@angular/forms";
import {TranslateService} from "@ngx-translate/core";
import {getErrorMessage, VOIDED_CHOICE} from "../../../utils/ConstUtil";
import * as removeAccents from "remove-accents";

@Component({
  selector: 'thd-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit {
  deleting = false;
  isVisible= false;
  entity:Category;
  formGroup: FormGroup;
  voidedList = VOIDED_CHOICE
  searchObject : CategorySearch ={
    pageIndex: 1,
    pageSize: 10,
  }
  totalElement = 0;
  categories: Category[] = [];
  categoryParents: Category[] = [];
  constructor(private categoryService: CategoryService,
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
    this.categoryService.search(search).subscribe(data=>{
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
      parentId : new FormControl(this.entity.parentId),
      level : new FormControl(this.entity.level,[Validators.required,
        Validators.min(1),Validators.max(3)])
    },this.parentRequiredValidator)
    if(this.entity.level>1){
      this.getCategoryByLevel(this.entity.level-1);
    }
  }
  changeLevel(){
    let levelValue = this.formGroup?.get("level")?.value;
    if(levelValue>1){
      this.getCategoryByLevel(levelValue-1);
    }else{
      this.formGroup?.get("parentId")?.setValue(null);
      this.categoryParents = [];
    }
  }
  getCategoryByLevel(level:number){
    this.categoryService.getAllByLevel(level).subscribe(data =>{
      this.categoryParents = data.body || [];
    })
  }
  parentRequiredValidator: ValidatorFn = (control: AbstractControl) => {
    let levelValue = this.formGroup?.get("level")?.value;
    let parentId = this.formGroup?.get("parentId");
    if(parentId){
      if(levelValue>1 && !parentId.value){
        parentId.setValidators(Validators.required)
        parentId.setErrors({required: true })
        return null;
      }
      parentId.setValidators(null)
      parentId.setErrors(null)
    }
    return null;
  };
  getErrorMessage(control:string):string{
    return getErrorMessage(control,this.formGroup,this.translate);
  }
  onSubmit(){
    this.categoryService.save(this.formGroup.getRawValue()).subscribe(data=>{
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
    this.categoryService.delete(id).subscribe(data =>{
      this.deleting = false;
      this.loadTable();
    })
  }
  onChangeCode(){
    let code = this.formGroup.get('code').value || "";
    code = removeAccents(code);
    code = code.toLowerCase().replace(/\s+/g, '-');
    this.formGroup.get('code').setValue(code)
  }
}
