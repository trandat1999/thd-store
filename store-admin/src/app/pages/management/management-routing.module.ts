import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AdministrativeUnitComponent} from "./administrative-unit/administrative-unit.component";

const routes: Routes = [
  {
    path:"administrative-unit",component:AdministrativeUnitComponent,data:{
      breadcrumb: "breadcrumb.administrativeUnit"
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ManagementRoutingModule { }
