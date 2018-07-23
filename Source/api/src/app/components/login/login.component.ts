import { Component, OnInit } from '@angular/core';
import {Store} from '@ngrx/store';

import {ApiService} from '../../services/api/api.service';
import {WebAuth, AppState} from '../../interfaces/index';
import * as AuthActions from '../../stores/auth/auth.actions';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  auth: WebAuth;
  error: any = {};
  constructor(private apiService: ApiService, private store: Store<AppState> ) {}

  ngOnInit() {
    this.store.select((state: AppState ) => {
      return state.auth;
    }).subscribe((auth: WebAuth) => {
      this.auth = auth;
    });
  }

  onSubmit(form) {
    this.error = '';
    this.apiService.auth(form).subscribe((auth: WebAuth) => {
      this.store.dispatch(new AuthActions.LoginIn(auth));
    }, (err) => {
      this.error = err.error.message;
    });
  }

  logout() {
    this.store.dispatch(new AuthActions.LogOut());
  }

}
