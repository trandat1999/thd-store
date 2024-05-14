import {Component} from '@angular/core';
import {
  InventoryImportSearch,
  InventoryImportStatus,
  InventoryImportStatusColor
} from "../../product-import/product-import.model";
import {VOIDED_CHOICE} from "../../../utils/ConstUtil";
import {Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import {SaleInvoiceService} from "./sale-invoice.service";
import {SaleInvoice} from "../sale.model";

@Component({
  selector: 'thd-sale-invoice',
  templateUrl: './sale-invoice.component.html',
  styleUrls: ['./sale-invoice.component.scss']
})
export class SaleInvoiceComponent {
  deleting = false;
  isVisible = false;
  voidedList = VOIDED_CHOICE
  searchObject: InventoryImportSearch = {
    pageIndex: 1,
    pageSize: 10,
  }
  totalElement = 0;
  entities: SaleInvoice[] = [];
  constructor(private saleInvoiceService: SaleInvoiceService,
              private router: Router,
              private translate: TranslateService) {
  }

  ngOnInit(): void {
  }

  submitSearch() {
    this.searchObject.pageIndex = 1;
    this.loadTable();
  }

  loadTable() {
    let search = {...this.searchObject}
    search.pageIndex = search.pageIndex - 1;
    this.saleInvoiceService.search(search).subscribe(data => {
      if (data.body) {
        this.entities = data.body.content;
        this.totalElement = data.body.totalElements;
      }
    })
  }
  pageChange(page: any) {
    this.loadTable();
  }

  delete(id: number) {
    this.deleting = true;
    this.saleInvoiceService.delete(id).subscribe(data => {
      this.deleting = false;
      this.loadTable();
    })
  }
  protected readonly InventoryImportStatusColor = InventoryImportStatusColor;
  protected readonly InventoryImportStatus = InventoryImportStatus;
}
