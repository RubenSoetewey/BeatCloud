import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import {ApiService} from '../../services/api/api.service';

import {AppState, User, WebAuth} from '../../interfaces';
import * as AuthActions from '../../stores/auth/auth.actions';


@Component({
  selector: 'app-root',
  templateUrl: './root.component.html',
})
export class RootComponent implements OnInit {

  constructor(private store: Store<AppState>, private apiService: ApiService) {}

  loadWebAuth() {
    const authItem: WebAuth = JSON.parse(localStorage.getItem('authItem'));
    if (authItem && authItem.isAuth) {

      var sub = this.apiService.sendRequest('/account/?token='+ authItem.token, 'get');
      this.store.dispatch(new AuthActions.LoginIn(authItem));
      sub.subscribe((user : WebAuth)=>{
        this.store.dispatch(new AuthActions.LoginIn(user));
      },(err) => {
        console.log(err);
        this.store.dispatch(new AuthActions.LogOut());
      });

    }else{
      this.store.dispatch(new AuthActions.LogOut());
    }
  }
  ngOnInit() {
    this.loadWebAuth();
  }
}
