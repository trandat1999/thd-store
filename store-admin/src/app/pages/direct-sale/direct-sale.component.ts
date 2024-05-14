import { Component } from '@angular/core';

@Component({
  selector: 'thd-direct-sale',
  templateUrl: './direct-sale.component.html',
  styleUrls: ['./direct-sale.component.scss']
})
export class DirectSaleComponent {
  tabs = ['Tab 1', 'Tab 2'];
  selectedIndex = 0;

  closeTab({ index }: { index: number }): void {
    this.tabs.splice(index, 1);
  }

  newTab(): void {
    this.tabs.push('New Tab');
    this.selectedIndex = this.tabs.length;
  }
}
