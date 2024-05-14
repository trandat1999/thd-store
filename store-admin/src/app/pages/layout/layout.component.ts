import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {Router} from "@angular/router";
import {StorageService} from "../../services/storage.service";
import {TranslateService} from "@ngx-translate/core";
import {TranslateConfigService} from "../../services/translate.service";
import {BehaviorSubject} from "rxjs";
import {NavigationItem, navigation} from "./layout.model";
import {SignalService} from "../../services/signal.service";

@Component({
  selector: 'thd-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent implements OnInit {
  isCollapsed = false;
  currentLanguage = "en";
  navigation = navigation;
  constructor(private authService: AuthService,
              private translateService: TranslateConfigService,
              private router: Router,
              private translate: TranslateService,
              private signalService: SignalService,
              private storage: StorageService) {
    this.currentLanguage = this.storage.getLanguage();
    this.signalService.subscribeToSignal().subscribe(signal => {
      if(signal.type === 'navCollapsed'){
        this.isCollapsed = signal.value || false;
      }
    });
  }

  ngOnInit(): void {
  }

  logout(): void {
    this.authService.logout().subscribe(data => {
    });
    this.storage.signOut();
    this.router.navigate(['/login'])
  }

  changeLanguage(lang: string) {
    this.destroyAndReload();
    this.translateService.changeLanguage(lang);
  }

  translateFn = (key: string) => {
    if (key) {
      return this.translate.instant(key)
    } else {
      return "";
    }
  }
  isVisible$ = new BehaviorSubject(true);

  destroyAndReload() {
    this.isVisible$.next(false);
    setTimeout(() => {
      this.isVisible$.next(true);
    }, 1);
  }
  isOpen(item : NavigationItem): boolean {
    if(item.children){
      let currentUrl = this.router.url;
      for(let sub of item.children){
        if(currentUrl.startsWith(sub.link)){
          return true;
        }
      }
    }
    return false;
  }
}
