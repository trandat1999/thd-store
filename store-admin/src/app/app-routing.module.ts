import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LayoutComponent} from "./pages/layout/layout.component";
import {AuthGuard} from "./guards/auth.guard";
import {ActiveComponent} from "./pages/auth/active/active.component";
import {LoginComponent} from "./pages/auth/login/login.component";
import { LoginGuard } from './guards/login.guard';
import {RegisterComponent} from "./pages/auth/register/register.component";
import {ForbiddenComponent} from "./pages/auth/forbidden/forbidden.component";
import {ForgotPasswordComponent} from "./pages/auth/forgot-password/forgot-password.component";
import {NotFoundComponent} from "./pages/auth/not-found/not-found.component";
const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/welcome' },
  { path: '', component: LayoutComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'welcome', loadChildren: () => import('./pages/welcome/welcome.module').then(m => m.WelcomeModule) },
      { path: 'setting',
        data: {
          breadcrumb: 'breadcrumb.none',
        },
        loadChildren: () => import('./pages/setting/setting.module').then(m => m.SettingModule)
      },
    ]
  },
  {path: "verification-account/:token", component: ActiveComponent},
  {path: "login",canActivate: [LoginGuard], component: LoginComponent},
  {path: "register", component: RegisterComponent},
  {path: "forbidden", component: ForbiddenComponent},
  {path: "forgot-password", component: ForgotPasswordComponent},
  {path: "forgot-password/:token", component: ForgotPasswordComponent},
  {path: "**", component: NotFoundComponent},
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
