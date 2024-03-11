import {AfterViewInit, Component, OnInit} from '@angular/core';
import {PreloaderService} from "./services/preloader.service";
import {StorageService} from "./services/storage.service";
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit,AfterViewInit{
  ngAfterViewInit(): void {
    this.preloader.hide();
  }
  constructor(
    private preloader: PreloaderService,
    private translate: TranslateService,
    private storeService: StorageService
  ) {
  }
  ngOnInit(): void {
    this.translate.use(this.storeService.getLanguage())
  }
}
