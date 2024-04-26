import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {SupplierComponent} from "./supplier/supplier.component";
import {InventoryImportComponent} from "./inventory-import/inventory-import.component";
import {
  InventoryImportDetailComponent
} from "./inventory-import/inventory-import-detail/inventory-import-detail.component";

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
      {path: ":id", component: InventoryImportDetailComponent,data: {
          breadcrumb: 'breadcrumb.edit'
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
