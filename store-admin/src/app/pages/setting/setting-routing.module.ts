import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CategoryComponent} from "./category/category.component";
import {CountryComponent} from "./country/country.component";
import {AttributeComponent} from "./attribute/attribute.component";
import {ProductComponent} from "./product/product.component";
import {ProductEditComponent} from "./product/product-edit/product-edit.component";
import {WarehouseComponent} from "./warehouse/warehouse.component";

const routes: Routes = [
  {
    path: "category", component: CategoryComponent, data: {
      breadcrumb: 'breadcrumb.category',
    },
  },
  {
    path: "country", component: CountryComponent, data: {
      breadcrumb: 'breadcrumb.country'
    },
  },
  {
    path: "attribute", component: AttributeComponent, data: {
      breadcrumb: 'breadcrumb.attribute'
    },
  },
  {
    path: "product", data: {
      breadcrumb: 'breadcrumb.product'
    },
    children: [
      {
        path: "",
        component: ProductComponent,
      },
      {
        path: "create",
        component: ProductEditComponent,
        data: {
          breadcrumb: 'breadcrumb.create',
        },
      },
      {
        path: ":id",
        component: ProductEditComponent,
        data: {
          breadcrumb: 'breadcrumb.edit',
        },
      },
    ]
  },
  {
    path: "warehouse", component: WarehouseComponent, data: {
      breadcrumb: 'breadcrumb.warehouse'
    },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SettingRoutingModule {
}
