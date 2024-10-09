import {Component} from '@angular/core';
import {
  InventoryImport,
  InvoiceItem,
  InventoryImportSearch,
  InventoryImportStatus,
  InventoryImportStatusColor,
  Supplier
} from "../product-import.model";
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {getErrorMessageValidator, VOIDED_CHOICE} from "../../../utils/ConstUtil";
import {Product, Warehouse} from "../../setting/setting.model";
import {SupplierService} from "../supplier/supplier.service";
import {TranslateService} from "@ngx-translate/core";
import {InventoryImportService} from "./inventory-import.service";
import {WarehouseService} from "../../setting/warehouse/warehouse.service";
import {differenceInCalendarDays} from 'date-fns';
import {Router} from "@angular/router";

@Component({
  selector: 'thd-inventory-import',
  templateUrl: './inventory-import.component.html',
  styleUrls: ['./inventory-import.component.scss']
})
export class InventoryImportComponent {
  deleting = false;
  isVisible = false;
  formGroup: FormGroup;
  entity: InventoryImport
  voidedList = VOIDED_CHOICE
  searchObject: InventoryImportSearch = {
    pageIndex: 1,
    pageSize: 10,
  }
  totalElement = 0;
  entities: InventoryImport[] = [];
  products: Product[] = [];
  suppliers: Supplier[] = [];
  warehouses : Warehouse[] = [];
  disabledDate = (current: Date): boolean => differenceInCalendarDays(current, new Date()) < 0;
  constructor(private supplierService: SupplierService,
              private invoiceImportService: InventoryImportService,
              private warehouseService: WarehouseService,
              private router: Router,
              private translate: TranslateService) {
  }

  ngOnInit(): void {
    this.supplierService.getAll().subscribe(data => {
      this.suppliers = data.body || [];
    })
    this.warehouseService.getAll().subscribe(data => {
      this.warehouses = data.body || [];
    })
  }

  submitSearch() {
    this.searchObject.pageIndex = 1;
    this.loadTable();
  }

  loadTable() {
    let search = {...this.searchObject}
    search.pageIndex = search.pageIndex - 1;
    this.invoiceImportService.search(search).subscribe(data => {
      if (data.body) {
        this.entities = data.body.content;
        this.totalElement = data.body.totalElements;
      }
    })
  }

  onCreateOrUpdate(data: any) {
    if(data && data.code){
      this.router.navigate(["/product-import/inventory-import", data.code])
      return;
    }
    this.isVisible = true;
    this.formGroup = new FormGroup({
      importDate: new FormControl(null, [Validators.required]),
      warehouse: new FormControl(null, [Validators.required]),
      supplier: new FormControl(null, [Validators.required]),
      note: new FormControl(null, [Validators.required]),
      items: new FormArray([], [Validators.required]),
    })
  }

  getErrorMessage(control: string): string {
    return getErrorMessageValidator(control, this.formGroup, this.translate);
  }

  onSubmit() {
    this.invoiceImportService.save(this.formGroup.getRawValue()).subscribe(data => {
      if (data.code == 400) {
        if (data.body) {
          Object.keys(data.body).forEach(key => {
            let field = key.replace("]","");
            field = field.replace("[",".");
            this.formGroup.get(field)?.setErrors({ 'serverError': true, 'serverErrorMess': data.body[key] });
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
    this.invoiceImportService.delete(id).subscribe(data => {
      this.deleting = false;
      this.loadTable();
    })
  }

  get items(){
    return this.formGroup?.get("items") as FormArray
  }
  get dataTable(){
    return [...this.items.controls]
  }
  initItem(data: InvoiceItem){
    return new FormGroup({
      quantity: new FormControl(data.quantity, [Validators.required,Validators.min(1)]),
      price: new FormControl(data.price, [Validators.required]),
      product: new FormControl(data.product, [Validators.required]),
    })
  }
  addItem() {
    this.items.push(this.initItem({}));
  }
  supplierChange(supplier: Supplier){
    this.products = supplier?.products || [];
  }
  getTotalPrice(){
    let total = 0;
    for(let item of this.items?.controls){
      total += (item.get('quantity')?.value || 0) * (item.get('price')?.value || 0);
    }
    return total;
  }

  protected readonly InventoryImportStatusColor = InventoryImportStatusColor;
  protected readonly InventoryImportStatus = InventoryImportStatus;
}
