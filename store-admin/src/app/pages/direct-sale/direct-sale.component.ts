import {Component} from '@angular/core';
import {SaleInvoice} from "../sale/sale.model";
import {NzTabChangeEvent} from "ng-zorro-antd/tabs/interfaces";

@Component({
  selector: 'thd-direct-sale',
  templateUrl: './direct-sale.component.html',
  styleUrls: ['./direct-sale.component.scss']
})
export class DirectSaleComponent {
  saleInvoices: SaleInvoice[] = [];
  selectedIndex = 0;
  entity: SaleInvoice
  drawerVisible = false;
  closeTab({ index }: { index: number }): void {
    this.saleInvoices.splice(index, 1);
  }

  newTab(): void {
    this.saleInvoices.push({})
    this.selectedIndex = this.saleInvoices.length;
    this.entity = this.saleInvoices[this.saleInvoices.length];
  }
  changeTab({index}:NzTabChangeEvent): void {
    this.entity = this.saleInvoices[index];
  }
  closeDrawer(){
    this.drawerVisible = false;
  }
  openDrawer(){
    this.drawerVisible = true;
  }
}
