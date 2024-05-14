import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SaleRoutingModule } from './sale-routing.module';
import { SaleInvoiceComponent } from './sale-invoice/sale-invoice.component';
import {CommonsModule} from "../commons/commons.module";
import {NzButtonModule} from "ng-zorro-antd/button";
import {NzIconModule} from "ng-zorro-antd/icon";
import {NzPageHeaderModule} from "ng-zorro-antd/page-header";
import {NzPopconfirmModule} from "ng-zorro-antd/popconfirm";
import {NzTableModule} from "ng-zorro-antd/table";
import {NzTagModule} from "ng-zorro-antd/tag";
import {NzToolTipModule} from "ng-zorro-antd/tooltip";
import {TranslateModule} from "@ngx-translate/core";
import {FormsModule} from "@angular/forms";


@NgModule({
  declarations: [
    SaleInvoiceComponent,
  ],
  imports: [
    CommonModule,
    SaleRoutingModule,
    CommonsModule,
    NzButtonModule,
    NzIconModule,
    NzPageHeaderModule,
    NzPopconfirmModule,
    NzTableModule,
    NzTagModule,
    NzToolTipModule,
    TranslateModule,
    FormsModule
  ]
})
export class SaleModule { }
