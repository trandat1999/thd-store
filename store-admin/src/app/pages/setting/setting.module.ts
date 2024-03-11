import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SettingRoutingModule } from './setting-routing.module';
import { CategoryComponent } from './category/category.component';
import {NzPageHeaderModule} from "ng-zorro-antd/page-header";
import {TranslateModule} from "@ngx-translate/core";
import {NzTableModule} from "ng-zorro-antd/table";
import {CdkTableModule} from "@angular/cdk/table";
import {NzButtonModule} from "ng-zorro-antd/button";
import {NzIconModule} from "ng-zorro-antd/icon";
import {NzInputModule} from "ng-zorro-antd/input";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NzModalModule} from "ng-zorro-antd/modal";
import {NzFormModule} from "ng-zorro-antd/form";
import {CommonsModule} from "../commons/commons.module";
import { CountryComponent } from './country/country.component';
import {NzPopconfirmModule} from "ng-zorro-antd/popconfirm";
import {NzUploadModule} from "ng-zorro-antd/upload";
import {NzSelectModule} from "ng-zorro-antd/select";
import {NzToolTipModule} from "ng-zorro-antd/tooltip";
import { ProductComponent } from './product/product.component';
import { SupplierComponent } from './supplier/supplier.component';


@NgModule({
  declarations: [
    CategoryComponent,
    CountryComponent,
    ProductComponent,
    SupplierComponent,
  ],
    imports: [
        CommonModule,
        SettingRoutingModule,
        NzPageHeaderModule,
        TranslateModule,
        NzTableModule,
        CdkTableModule,
        NzButtonModule,
        NzIconModule,
        NzInputModule,
        FormsModule,
        NzModalModule,
        NzFormModule,
        ReactiveFormsModule,
        CommonsModule,
        NzPopconfirmModule,
        NzUploadModule,
        NzSelectModule,
        NzToolTipModule
    ]
})
export class SettingModule { }
