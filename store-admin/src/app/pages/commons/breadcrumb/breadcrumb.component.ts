import {ChangeDetectionStrategy, Component, Input, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {NzBreadcrumb} from "ng-zorro-antd/breadcrumb/breadcrumb";
import {NzBreadCrumbComponent} from "ng-zorro-antd/breadcrumb/breadcrumb.component";

export interface BreadcrumbItem {
  link?: string;
  name?: string;
}
@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  selector: 'thd-breadcrumb',
  templateUrl: './breadcrumb.component.html',
  styleUrls: ['./breadcrumb.component.scss']
})
export class BreadcrumbComponent implements OnInit {
  @Input() lang: string
  @Input() items :BreadcrumbItem[] = [];
  @Input() autoGenerate: boolean = false;
  @Input() routeLabel: string = 'breadcrumb';
  @Input() routeLabelFn: (label: string) => string = label => label;
  constructor(
  ) { }
  ngOnInit(): void {
  }
}
