import {Component} from '@angular/core';
import {ProductShow} from "../setting.model";
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";
import {ProductSearch} from "../../../utils/search-object";
import {Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import {ProductShowService} from "./product-show.service";
import {VOIDED_CHOICE, getErrorMessage, ProductShowStatus, getDescription} from "../../../utils/ConstUtil";
import {InventoryImportService} from "../../product-import/inventory-import/inventory-import.service";

@Component({
  selector: 'thd-product-show',
  templateUrl: './product-show.component.html',
  styleUrls: ['./product-show.component.scss']
})
export class ProductShowComponent {
  isVisible= false;
  entity:ProductShow;
  formGroup: FormGroup;
  searchObject : ProductSearch ={
    pageIndex: 1,
    pageSize: 10,
  }
  totalElement = 0;
  entities: ProductShow[];
  lastPrice: null;
  showTooltip = false;
  constructor(private productShowService: ProductShowService,
              private inventoryImportService: InventoryImportService,
              private router : Router,
              private translate : TranslateService) { }

  submitSearch(){
    this.searchObject.pageIndex = 1;
    this.loadTable();
  }
  loadTable(){
    let search = {...this.searchObject}
    search.pageIndex=search.pageIndex-1;
    this.productShowService.search(search).subscribe(data=>{
      if(data.body){
        this.entities = data.body.content;
        this.totalElement = data.body.totalElements;
      }
    })
  }
  onCreateOrUpdate(data:ProductShow){
    this.entity = data || {};
    this.isVisible = true;
    this.formGroup = new FormGroup({
      id : new FormControl(this.entity.id),
      price : new FormControl(this.entity.price,[Validators.required,this.validatorMinValue()]),
      status : new FormControl(this.entity.status,[Validators.required]),
      product: new FormControl(this.entity.product,[Validators.required]),
    })
    setTimeout(()=>{
      this.onChangeProduct()
    },500);
  }
  validatorMinValue(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const isValid = this.lastPrice?(control.value >= this.lastPrice) : true;
      return isValid ? null : { invalidMinValue: { minValue: this.lastPrice } };
    };
  }
  getErrorMessage(control:string):string{
    return getErrorMessage(control,this.formGroup,this.translate);
  }
  onSubmit(){
    this.productShowService.save(this.formGroup.getRawValue()).subscribe(data=>{
      if(data.code==400){
        if (data.body) {
          Object.keys(data.body).forEach(key => {
            let field = key.replace("]","");
            field = field.replace("[",".");
            this.formGroup.get(field)?.setErrors({ 'serverError': true, 'serverErrorMess': data.body[key] });
          });
          return;
        }
      }else{
        this.isVisible=false;
        this.showTooltip = false;
        this.submitSearch();
      }
    })
  }
  pageChange(page:any){
    this.loadTable();
  }
  onChangeProduct(){
    if(this.formGroup.get("product")?.value?.id){
      this.showTooltip = true;
      this.inventoryImportService.getLastPriceImported(this.formGroup.get("product")?.value?.id).subscribe(data=>{
        this.lastPrice = data.body || null;
        if(this.lastPrice==null){
          this.formGroup.get("status")?.setValue(7);
        }
        this.formGroup.get("price")?.updateValueAndValidity();
      })
    }
  }
  getDescription(value){
    return getDescription(this.productShowStatus,value);
  }

  protected readonly productShowStatus = ProductShowStatus;
  protected readonly console = console;
}
