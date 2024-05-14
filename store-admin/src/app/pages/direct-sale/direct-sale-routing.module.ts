import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {DirectSaleComponent} from "./direct-sale.component";

const routes: Routes = [
  {
    path: "",component : DirectSaleComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DirectSaleRoutingModule { }
