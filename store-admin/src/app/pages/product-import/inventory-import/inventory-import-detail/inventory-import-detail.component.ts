import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {InventoryImportService} from "../inventory-import.service";
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {ToastrService} from "ngx-toastr";
import {SupplierService} from "../../supplier/supplier.service";
import {WarehouseService} from "../../../setting/warehouse/warehouse.service";
import {TranslateService} from "@ngx-translate/core";
import {
  InvoiceItem,
  InventoryImportStatus,
  InventoryImportStatusArray,
  InventoryImportStatusColor,
  Supplier
} from "../../product-import.model";
import {Product, Warehouse} from "../../../setting/setting.model";
import {differenceInCalendarDays} from "date-fns";
import {getErrorMessageValidator, VOIDED_CHOICE} from "../../../../utils/ConstUtil";

@Component({
  selector: 'thd-inventory-import-detail',
  templateUrl: './inventory-import-detail.component.html',
  styleUrls: ['./inventory-import-detail.component.scss']
})
export class InventoryImportDetailComponent implements OnInit {
  voidedList = VOIDED_CHOICE
  products: Product[] = [];
  suppliers: Supplier[] = [];
  warehouses: Warehouse[] = [];
  disabledDate = (current: Date): boolean => differenceInCalendarDays(current, new Date()) < 0;
  formGroup: FormGroup
  status: string;
  id: string

  constructor(private router: Router,
              private route: ActivatedRoute,
              private toast: ToastrService,
              private supplierService: SupplierService,
              private invoiceImportService: InventoryImportService,
              private warehouseService: WarehouseService,
              private translate: TranslateService) {
    this.id = this.route.snapshot.params['id'];
    if (!this.id) {
      this.toast.warning("Không tìm thấy dữ liệu", "Thông báo")
      this.router.navigate(["/product-import/inventory-import"])
      return
    }
    this.invoiceImportService.getByCode(this.id).subscribe(data => {
      if (!data.body) {
        this.toast.warning("Không tìm thấy dữ liệu", "Thông báo")
        this.router.navigate(["/product-import/inventory-import"])
        return
      }
      this.updateData(data.body);
    })
  }

  updateData(data: any) {
    this.formGroup.patchValue(data);
    this.updateItems(data.items);
    this.supplierChange(data.supplier)
    this.status = data.status;
    if (this.status != 'NEW') {
      this.disableForm(this.formGroup);
    }
  }

  ngOnInit() {
    this.supplierService.getAll().subscribe(data => {
      this.suppliers = data.body || [];
    })
    this.warehouseService.getAll().subscribe(data => {
      this.warehouses = data.body || [];
    })
    this.initForm();
  }

  initForm() {
    this.formGroup = new FormGroup({
      id: new FormControl(null, [Validators.required]),
      importDate: new FormControl(null, [Validators.required]),
      warehouse: new FormControl(null, [Validators.required]),
      supplier: new FormControl(null, [Validators.required]),
      note: new FormControl(null, [Validators.required]),
      items: new FormArray([], [Validators.required]),
      status: new FormControl(null, [Validators.required]),
    })
  }

  disableForm(formGroup: FormGroup): void {
    if (formGroup) {
      for (const [key, value] of Object.entries(formGroup.controls)) {
        if (value instanceof FormGroup) {
          this.disableForm(value);
        } else if (value instanceof FormArray) {
          for (let formArrayControl of value.controls) {
            if (formArrayControl instanceof FormGroup) {
              this.disableForm(formArrayControl)
            }
            formArrayControl.disable();
          }
        } else {
          value.disable();
        }
      }
    }
  }

  getErrorMessage(control: string): string {
    return getErrorMessageValidator(control, this.formGroup, this.translate);
  }

  onSubmit() {
    this.invoiceImportService.save(this.formGroup.getRawValue()).subscribe(data => {
      if (data.code == 400) {
        if (data.body) {
          Object.keys(data.body).forEach(key => {
            let field = key.replace("]", "");
            field = field.replace("[", ".");
            this.formGroup.get(field)?.setErrors({'serverError': true, 'serverErrorMess': data.body[key]});
          });
          return;
        }
      } else {
        this.formGroup.patchValue(data.body);
        this.toast.success("Cập nhật thành công", "Thông báo")
      }
    })
  }

  get items() {
    return this.formGroup?.get("items") as FormArray
  }

  get dataTable() {
    return [...this.items.controls]
  }

  initItem(data: InvoiceItem) {
    return new FormGroup({
      quantity: new FormControl(data.quantity, [Validators.required, Validators.min(1)]),
      price: new FormControl(data.price, [Validators.required]),
      product: new FormControl(data.product, [Validators.required]),
    })
  }

  updateItems(items: InvoiceItem[]) {
    this.items.clear();
    for (let item of items) {
      this.items.push(this.initItem(item));
    }
  }

  addItem() {
    this.items.push(this.initItem({}));
  }

  supplierChange(supplier: Supplier) {
    this.products = supplier?.products || [];
  }

  getTotalPrice() {
    let total = 0;
    for (let item of this.items?.controls) {
      total += (item.get('quantity')?.value || 0) * (item.get('price')?.value || 0);
    }
    return total;
  }

  currentStatus() {
    return this.status ? InventoryImportStatusArray.indexOf(this.status) : -1
  }

  back() {
    this.router.navigate(['/product-import/inventory-import'])
  }

  changeStatus(status: 'order'|'approve' | 'paid' | 'ship' | 'warehoused') {
    this.invoiceImportService.changeStatus(this.id,status).subscribe(data => {
      if (data.body) {
        this.updateData(data.body)
      }
    })
  }
  protected readonly InventoryImportStatus = InventoryImportStatus;
  protected readonly InventoryImportStatusColor = InventoryImportStatusColor;
}
