import { NgModule } from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';

import { DirectSaleRoutingModule } from './direct-sale-routing.module';
import { DirectSaleComponent } from './direct-sale.component';
import {NzLayoutModule} from "ng-zorro-antd/layout";
import {CommonsModule} from "../commons/commons.module";
import {NzIconModule} from "ng-zorro-antd/icon";
import {NzDividerModule} from "ng-zorro-antd/divider";
import {NzButtonModule} from "ng-zorro-antd/button";
import {NzTabsModule} from "ng-zorro-antd/tabs";
import {TranslateModule} from "@ngx-translate/core";
import {NzGridModule} from "ng-zorro-antd/grid";
import {NzMenuModule} from "ng-zorro-antd/menu";
import {NzDrawerModule} from "ng-zorro-antd/drawer";
import {NzCollapseModule} from "ng-zorro-antd/collapse";
import {NzAffixModule} from "ng-zorro-antd/affix";
import {NzToolTipModule} from "ng-zorro-antd/tooltip";
import {FormsModule} from "@angular/forms";
import {NzPaginationModule} from "ng-zorro-antd/pagination";
import {NzListModule} from "ng-zorro-antd/list";
import {NzTableModule} from "ng-zorro-antd/table";
import {NzImageModule} from "ng-zorro-antd/image";


@NgModule({
  declarations: [
    DirectSaleComponent
  ],
  imports: [
    CommonModule,
    DirectSaleRoutingModule,
    NzLayoutModule,
    CommonsModule,
    NzIconModule,
    NzDividerModule,
    NzButtonModule,
    NzTabsModule,
    TranslateModule,
    NzGridModule,
    NzMenuModule,
    NzDrawerModule,
    NzCollapseModule,
    NzAffixModule,
    NzToolTipModule,
    FormsModule,
    NzPaginationModule,
    NgOptimizedImage,
    NzListModule,
    NzTableModule,
    NzImageModule,
  ]
})
export class DirectSaleModule { }
