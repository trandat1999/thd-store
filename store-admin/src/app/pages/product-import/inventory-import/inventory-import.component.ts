import { Component } from '@angular/core';
import {Supplier} from "../product-import.model";
import {AbstractControl, FormControl, FormGroup, ValidatorFn, Validators} from "@angular/forms";
import {PHONE_NUMBER_REGEX, VOIDED_CHOICE, getErrorMessage} from "../../../utils/ConstUtil";
import {CategorySearch} from "../../../utils/search-object";
import {Product} from "../../setting/setting.model";
import {SupplierService} from "../supplier/supplier.service";
import {ProductService} from "../../setting/product/product.service";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'thd-inventory-import',
  templateUrl: './inventory-import.component.html',
  styleUrls: ['./inventory-import.component.scss']
})
export class InventoryImportComponent {
  deleting = false;
  isVisible = false;
  entity: Supplier;
  formGroup: FormGroup;
  voidedList = VOIDED_CHOICE
  searchObject: CategorySearch = {
    pageIndex: 1,
    pageSize: 10,
  }
  totalElement = 0;
  entities: Supplier[] = [];
  products: Product[] = [];

  constructor(private supplierService: SupplierService,
              private productService: ProductService,
              private translate: TranslateService) {
  }

  ngOnInit(): void {
    this.productService.getAll().subscribe(data => {
      this.products = data.body || [];
    })
  }

  submitSearch() {
    this.searchObject.pageIndex = 1;
    this.loadTable();
  }

  loadTable() {
    let search = {...this.searchObject}
    search.pageIndex = search.pageIndex - 1;
    this.supplierService.search(search).subscribe(data => {
      if (data.body) {
        this.entities = data.body.content;
        this.totalElement = data.body.totalElements;
      }
    })
  }

  onCreateOrUpdate(data: any) {
    this.isVisible = true;
    this.entity = data ? data : {}
    this.formGroup = new FormGroup({
      id: new FormControl(this.entity.id),
      name: new FormControl(this.entity.name, [Validators.required]),
      code: new FormControl(this.entity.code, [Validators.required]),
      description: new FormControl(this.entity.description),
      voided: new FormControl(this.entity.voided ? this.entity.voided : false),
      products: new FormControl(this.entity.products),
      email: new FormControl(this.entity.email, [Validators.required, Validators.email]),
      phoneNumber: new FormControl(this.entity.phoneNumber, [Validators.required, this.phoneNumberValidator]),
    })
  }

  phoneNumberValidator: ValidatorFn = (control: AbstractControl) => {
    if (!control.value) {
      return {required: true}
    }
    if (control.value) {
      let regex = new RegExp(PHONE_NUMBER_REGEX)
      if (!regex.test(control.value)) {
        return {phoneNumber: true}
      }
    }
    return {};
  };

  getErrorMessage(control: string): string {
    return getErrorMessage(control, this.formGroup, this.translate);
  }

  onSubmit() {
    this.supplierService.save(this.formGroup.getRawValue()).subscribe(data => {
      if (data.code == 400) {
        if (data.body) {
          Object.keys(data.body).forEach(key => {
            this.formGroup.controls[key]?.setErrors({'serverError': true, 'serverErrorMess': data.body[key]});
          });
          return;
        }
      } else {
        this.isVisible = false;
        this.submitSearch();
      }
    })
  }

  pageChange(page: any) {
    this.loadTable();
  }

  delete(id: number) {
    this.deleting = true;
    this.supplierService.delete(id).subscribe(data => {
      this.deleting = false;
      this.loadTable();
    })
  }
}
