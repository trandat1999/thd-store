import {Component} from '@angular/core';
import {Attribute} from "../setting.model";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {getErrorMessage, VOIDED_CHOICE} from "../../../utils/ConstUtil";
import {CategorySearch} from "../../../utils/search-object";
import {TranslateService} from "@ngx-translate/core";
import {AttributeService} from "./attribute.service";
import * as removeAccents from 'remove-accents'
@Component({
  selector: 'thd-attribute',
  templateUrl: './attribute.component.html',
  styleUrls: ['./attribute.component.scss']
})
export class AttributeComponent {
  deleting = false;
  isVisible= false;
  entity:Attribute;
  formGroup: FormGroup;
  voidedList = VOIDED_CHOICE

  searchObject : CategorySearch ={
    pageIndex: 1,
    pageSize: 10,
  }
  totalElement = 0;
  entities: Attribute[] = [];
  constructor(private attributeService: AttributeService,
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
    this.attributeService.search(search).subscribe(data=>{
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
      name : new FormControl(this.entity.name,[Validators.required]),
      code : new FormControl(this.entity.code,[Validators.required]),
      description : new FormControl(this.entity.description),
      voided : new FormControl(this.entity.voided?this.entity.voided:false),
    })
  }
  getErrorMessage(control:string):string{
    return getErrorMessage(control,this.formGroup,this.translate);
  }
  onSubmit(){
    this.attributeService.save(this.formGroup.getRawValue()).subscribe(data=>{
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
    this.attributeService.delete(id).subscribe(data =>{
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
