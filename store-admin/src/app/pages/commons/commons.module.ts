import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BreadcrumbComponent } from './breadcrumb/breadcrumb.component';
import {NzBreadCrumbModule} from "ng-zorro-antd/breadcrumb";
import {NzIconModule} from "ng-zorro-antd/icon";
import {RouterLinkWithHref} from "@angular/router";
import { NuclearInputComponent } from './nuclear-input/nuclear-input.component';
import {NzFormModule} from "ng-zorro-antd/form";
import {NzGridModule} from "ng-zorro-antd/grid";
import {NzInputModule} from "ng-zorro-antd/input";
import {FormsModule} from "@angular/forms";
import {NzSelectModule} from "ng-zorro-antd/select";
import {AngularEditorModule} from "@kolkov/angular-editor";
import {NzInputNumberModule} from "ng-zorro-antd/input-number";
import {NzDatePickerModule} from "ng-zorro-antd/date-picker";
import {TranslateModule} from "@ngx-translate/core";



@NgModule({
  declarations: [
    BreadcrumbComponent,
    NuclearInputComponent
  ],
  exports: [
    BreadcrumbComponent,
    NuclearInputComponent
  ],
    imports: [
        CommonModule,
        NzBreadCrumbModule,
        NzIconModule,
        RouterLinkWithHref,
        NzFormModule,
        NzGridModule,
        NzInputModule,
        FormsModule,
        NzSelectModule,
        AngularEditorModule,
        NzInputNumberModule,
        NzDatePickerModule,
        TranslateModule
    ]
})
export class CommonsModule { }
