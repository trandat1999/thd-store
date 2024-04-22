import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {SupplierComponent} from "./supplier/supplier.component";
import {InventoryImportComponent} from "./inventory-import/inventory-import.component";

const routes: Routes = [
  {
    path: "supplier", component: SupplierComponent, data: {
      breadcrumb: 'breadcrumb.supplier'
    },
  },{
    path: "inventory-import",
    data: {
      breadcrumb: 'breadcrumb.inventoryImport'
    },
    children:[
      {path: "", component: InventoryImportComponent,data: {
          breadcrumb: 'breadcrumb.inventoryImport'
        },
      },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductImportRoutingModule { }
