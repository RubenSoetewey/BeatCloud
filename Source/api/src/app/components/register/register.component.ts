import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Store } from '@ngrx/store';

import {ApiService} from '../../services/api/api.service';
import {User, AppState, WebAuth} from '../../interfaces';

import * as AuthActions from '../../stores/auth/auth.actions';
import {Router} from '@angular/router';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  errors: any[] = [];
  auth: WebAuth;

  constructor(private apiService: ApiService,  private store: Store<AppState>,private router: Router ) {}

  ngOnInit() {
    this.store.select((state: AppState ) => {
      return state.auth;
    }).subscribe((auth: WebAuth) => {
      this.auth = auth;
    });
  }

  onSubmit(form) {
    const newUser: User = form;

    this.errors = [];
    if (form.password !== form.confirmPassword) {
      this.errors.push({msg : 'Le mot de passe de confirmation est inccorect.'});
      return;
    }
    newUser.birthDate = new Date(form.birthYear, form.birthMonth - 1, form.birthDay).getTime();

    this.apiService.createAccount(newUser).subscribe((response: any) => {
      var result : WebAuth = response;
      this.store.dispatch(new AuthActions.LoginIn(result));
      this.router.navigateByUrl('/home');
    }, result => {
      alert(result.error.message);
    });
  }
}
