import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CategoryComponent} from "./category/category.component";
import {CountryComponent} from "./country/country.component";

const routes: Routes = [
  {
    path: "category", component: CategoryComponent, data: {
      breadcrumb: 'breadcrumb.category',
    },
    children: [
      {
        path: ":id",
        component: CategoryComponent,
        data: {
          breadcrumb: 'breadcrumb.category',
        },
      }
    ]
  },
  {
    path: "country", component: CountryComponent, data: {
      breadcrumb: 'breadcrumb.country'
    },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SettingRoutingModule {
}
