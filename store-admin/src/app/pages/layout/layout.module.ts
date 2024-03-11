import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LayoutComponent } from './layout.component';
import {NzLayoutModule} from "ng-zorro-antd/layout";
import {NzMenuModule} from "ng-zorro-antd/menu";
import {NzIconModule} from "ng-zorro-antd/icon";
import {RouterLinkActive, RouterLinkWithHref, RouterOutlet} from "@angular/router";
import {TranslateModule} from "@ngx-translate/core";
import {NzDropDownModule} from "ng-zorro-antd/dropdown";
import {NzAvatarModule} from "ng-zorro-antd/avatar";
import {NzButtonModule} from "ng-zorro-antd/button";
import {NzSelectModule} from "ng-zorro-antd/select";
import {FormsModule} from "@angular/forms";
import {CommonsModule} from "../commons/commons.module";
import {NzBreadCrumbModule} from "ng-zorro-antd/breadcrumb";



@NgModule({
  declarations: [
    LayoutComponent
  ],
    imports: [
        CommonModule,
        NzLayoutModule,
        NzMenuModule,
        NzIconModule,
        RouterOutlet,
        RouterLinkWithHref,
        TranslateModule,
        RouterLinkActive,
        NzDropDownModule,
        NzAvatarModule,
        NzButtonModule,
        NzSelectModule,
        FormsModule,
        CommonsModule,
        NzBreadCrumbModule
    ]
})
export class LayoutModule { }
