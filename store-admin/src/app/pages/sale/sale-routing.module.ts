import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {SaleInvoiceComponent} from "./sale-invoice/sale-invoice.component";

const routes: Routes = [
  {
    path: "invoice",
    component: SaleInvoiceComponent,
    data: {
      breadcrumb: "breadcrumb.saleInvoice"
    }
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SaleRoutingModule { }
