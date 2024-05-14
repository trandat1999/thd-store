import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthRoutingModule } from './auth-routing.module';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import {NzFormModule} from "ng-zorro-antd/form";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NzInputModule} from "ng-zorro-antd/input";
import {NzButtonModule} from "ng-zorro-antd/button";
import {NzCheckboxModule} from "ng-zorro-antd/checkbox";
import { NotFoundComponent } from './not-found/not-found.component';
import {NzIconModule} from "ng-zorro-antd/icon";
import {NzCardModule} from "ng-zorro-antd/card";
import {CommonsModule} from "../commons/commons.module";
import {NzSpaceModule} from "ng-zorro-antd/space";
import {NzI18nModule} from "ng-zorro-antd/i18n";
import { ActiveComponent } from './active/active.component';
import {TranslateModule} from "@ngx-translate/core";
import {NzModalModule} from "ng-zorro-antd/modal";
import {NzAlertModule} from "ng-zorro-antd/alert";
import {NzResultModule} from "ng-zorro-antd/result";


@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
    ForbiddenComponent,
    ForgotPasswordComponent,
    NotFoundComponent,
    ActiveComponent
  ],
    imports: [
        CommonModule,
        AuthRoutingModule,
        NzFormModule,
        NzButtonModule,
        NzCheckboxModule,
        FormsModule,
        NzInputModule,
        ReactiveFormsModule,
        NzIconModule,
        NzCardModule,
        CommonsModule,
        NzSpaceModule,
        NzI18nModule,
        TranslateModule,
        NzModalModule,
        NzAlertModule,
        NzResultModule
    ]
})
export class AuthModule { }
