import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ManagementRoutingModule } from './management-routing.module';
import { AdministrativeUnitComponent } from './administrative-unit/administrative-unit.component';
import {CommonsModule} from "../commons/commons.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NzButtonModule} from "ng-zorro-antd/button";
import {NzFormModule} from "ng-zorro-antd/form";
import {NzIconModule} from "ng-zorro-antd/icon";
import {NzModalModule} from "ng-zorro-antd/modal";
import {NzPageHeaderModule} from "ng-zorro-antd/page-header";
import {NzPopconfirmModule} from "ng-zorro-antd/popconfirm";
import {NzTableModule} from "ng-zorro-antd/table";
import {NzToolTipModule} from "ng-zorro-antd/tooltip";
import {TranslateModule} from "@ngx-translate/core";
import {NzUploadModule} from "ng-zorro-antd/upload";
import { UserComponent } from './user/user.component';
import { RoleComponent } from './role/role.component';
import { PermissionComponent } from './permission/permission.component';


@NgModule({
  declarations: [
    AdministrativeUnitComponent,
    UserComponent,
    RoleComponent,
    PermissionComponent
  ],
    imports: [
        CommonModule,
        ManagementRoutingModule,
        CommonsModule,
        FormsModule,
        NzButtonModule,
        NzFormModule,
        NzIconModule,
        NzModalModule,
        NzPageHeaderModule,
        NzPopconfirmModule,
        NzTableModule,
        NzToolTipModule,
        ReactiveFormsModule,
        TranslateModule,
        NzUploadModule
    ]
})
export class ManagementModule { }
