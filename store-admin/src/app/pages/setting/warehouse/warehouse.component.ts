import {Component} from '@angular/core';
import {Warehouse} from "../setting.model";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {getErrorMessage, VOIDED_CHOICE} from "../../../utils/ConstUtil";
import {WarehouseSearch} from "../../../utils/search-object";
import {TranslateService} from "@ngx-translate/core";
import {WarehouseService} from "./warehouse.service";
import {AdministrativeUnitService} from "../../management/administrative-unit/administrative-unit.service";
import {AdministrativeUnit} from "../../management/management.model";
import * as removeAccents from "remove-accents";

@Component({
  selector: 'thd-warehouse',
  templateUrl: './warehouse.component.html',
  styleUrls: ['./warehouse.component.scss']
})
export class WarehouseComponent {
  deleting : boolean = false
  isVisible= false;
  entity:Warehouse;
  formGroup: FormGroup;
  voidedList = VOIDED_CHOICE
  searchObject : WarehouseSearch ={
    pageIndex: 1,
    pageSize: 10,
  }
  totalElement = 0;
  entities: Warehouse[] = [];
  provinces: AdministrativeUnit[] = [];
  districts: AdministrativeUnit[] = [];
  modalDistricts: AdministrativeUnit[] = [];
  communes: AdministrativeUnit[] = [];
  modalCommunes: AdministrativeUnit[] = [];
  constructor(private warehouseService: WarehouseService,
              private administrativeUnitService: AdministrativeUnitService,
              private translate : TranslateService) { }

  ngOnInit(): void {
    this.getAllProvince();
  }
  getAllProvince(){
    this.administrativeUnitService.getAllProvince().subscribe(data =>{
      this.provinces = data.body || [];
    })
  }
  getAllByParent(type: 'district'| 'commune',isModal: boolean,clearData: boolean){
    let isDistrict = type === 'district';
    let parentId = null;
    if(isModal){
      parentId = isDistrict?(this.formGroup?.get("province")?.value?.id):
        (this.formGroup?.get("district")?.value?.id)
    }else{
      parentId = isDistrict?this.searchObject.provinceId:this.searchObject.districtId
    }
    if(parentId){
      this.administrativeUnitService.getAllByParent(parentId).subscribe(data =>{
        if(isModal){
          this.modalDistricts = isDistrict? (data.body || []) : this.modalDistricts;
          this.modalCommunes = isDistrict? []: (data.body || []);
          if(clearData){
            this.formGroup?.get("district")?.setValue(isDistrict?undefined:(this.formGroup?.get("district").value));
            this.formGroup?.get("commune")?.setValue(undefined);
          }
        }else{
          this.districts = isDistrict? (data.body || []) : this.districts;
          this.communes = isDistrict?[]:(data.body || []);
          if(clearData){
            this.searchObject.districtId = isDistrict? null : this.searchObject.districtId;
            this.searchObject.communeId = null;
          }
        }
      })
    }else{
      if(isModal){
        this.modalDistricts = isDistrict?[]:this.modalDistricts;
        this.modalCommunes = [];
        if(clearData){
          this.formGroup?.get("district")?.setValue(isDistrict?undefined:(this.formGroup?.get("district").value));
          this.formGroup?.get("commune")?.setValue(undefined);
        }
      }else{
        this.districts = isDistrict?[]:this.districts;
        this.communes = [];
        if(clearData){
          this.searchObject.districtId = isDistrict? null : this.searchObject.districtId;
          this.searchObject.communeId = null;
        }
      }
    }

  }
  submitSearch(){
    this.searchObject.pageIndex = 1;
    this.loadTable();
  }
  loadTable(){
    let search = {...this.searchObject}
    search.pageIndex=search.pageIndex-1;
    this.warehouseService.search(search).subscribe(data=>{
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
      address : new FormControl(this.entity.address),
      voided : new FormControl(this.entity.voided?this.entity.voided:false),
      province: new FormControl(this.entity.province, [Validators.required]),
      district: new FormControl(this.entity.district, [Validators.required]),
      commune: new FormControl(this.entity.commune, [Validators.required]),
    })
    this.getAllByParent("district",true,false);
    this.getAllByParent("commune",true,false);
  }
  getErrorMessage(control:string):string{
    return getErrorMessage(control,this.formGroup,this.translate);
  }
  onSubmit(){
    this.warehouseService.save(this.formGroup.getRawValue()).subscribe(data=>{
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
    this.warehouseService.delete(id).subscribe(data =>{
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
