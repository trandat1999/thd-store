import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DirectSaleRoutingModule } from './direct-sale-routing.module';
import { DirectSaleComponent } from './direct-sale.component';
import {NzLayoutModule} from "ng-zorro-antd/layout";
import {CommonsModule} from "../commons/commons.module";
import {NzIconModule} from "ng-zorro-antd/icon";
import {NzDividerModule} from "ng-zorro-antd/divider";
import {NzButtonModule} from "ng-zorro-antd/button";
import {NzTabsModule} from "ng-zorro-antd/tabs";


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
        NzTabsModule
    ]
})
export class DirectSaleModule { }
