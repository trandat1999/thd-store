import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProductImportRoutingModule } from './product-import-routing.module';
import {SupplierComponent} from "./supplier/supplier.component";
import {TranslateModule} from "@ngx-translate/core";
import {NzPageHeaderModule} from "ng-zorro-antd/page-header";
import {CommonsModule} from "../commons/commons.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NzButtonModule} from "ng-zorro-antd/button";
import {NzIconModule} from "ng-zorro-antd/icon";
import {NzTableModule} from "ng-zorro-antd/table";
import {NzToolTipModule} from "ng-zorro-antd/tooltip";
import {NzPopconfirmModule} from "ng-zorro-antd/popconfirm";
import {NzModalModule} from "ng-zorro-antd/modal";
import {NzFormModule} from "ng-zorro-antd/form";
import { InventoryImportComponent } from './inventory-import/inventory-import.component';
import { InventoryImportDetailComponent } from './inventory-import/inventory-import-detail/inventory-import-detail.component';
import {NzTagModule} from "ng-zorro-antd/tag";


@NgModule({
  declarations: [
    SupplierComponent,
    InventoryImportComponent,
    InventoryImportDetailComponent
  ],
    imports: [
        CommonModule,
        ProductImportRoutingModule,
        TranslateModule,
        NzPageHeaderModule,
        CommonsModule,
        FormsModule,
        NzButtonModule,
        NzIconModule,
        NzTableModule,
        NzToolTipModule,
        NzPopconfirmModule,
        NzModalModule,
        ReactiveFormsModule,
        NzFormModule,
        NzTagModule
    ]
})
export class ProductImportModule { }
