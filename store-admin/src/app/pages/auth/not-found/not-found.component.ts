import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'thd-not-found',
  templateUrl: './not-found.component.html',
  styleUrls: ['./not-found.component.scss']
})
export class NotFoundComponent implements OnInit {

  constructor(private router : Router) { }

  ngOnInit(): void {
  }
  backHome(){
    this.router.navigate(['/'])
  }
}
