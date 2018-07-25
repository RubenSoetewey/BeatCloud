import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Store } from '@ngrx/store';

import {ApiService} from '../../../services/api/api.service';
import {User, AppState, WebAuth} from '../../../interfaces/index';

import * as SearchActions from '../../../stores/search/search.actions';
import {Router} from "@angular/router";


@Component({
  selector: 'app-top-sidebar',
  templateUrl: './top.component.html',
  styleUrls: ['./top.component.css']
})
export class TopSideBarComponent implements OnInit {
  errors: any[] = [];
  auth: WebAuth;
  term: string = "";

  constructor(private store: Store<AppState>, private router: Router) {}

  ngOnInit() {
    this.store.select((state: AppState ) => {
      return state.auth;
    }).subscribe((auth: WebAuth) => {
      this.auth = auth;
    });

    this.store.dispatch(new SearchActions.SetTerm(''));
  }

  search(){
    this.router.navigate(['search']);
  }

  keyup(){
    this.store.dispatch(new SearchActions.SetTerm(this.term));
  }
}
