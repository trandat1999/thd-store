import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AdministrativeUnitComponent} from "./administrative-unit/administrative-unit.component";
import {PermissionComponent} from "./permission/permission.component";

const routes: Routes = [
  {
    path:"administrative-unit",component:AdministrativeUnitComponent,data:{
      breadcrumb: "breadcrumb.administrativeUnit"
    }
  },{
    path:"permission",component:PermissionComponent,data:{
      breadcrumb: "Quyền hạn"
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ManagementRoutingModule { }
