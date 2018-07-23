import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Store } from '@ngrx/store';

import {ApiService} from '../../services/api/api.service';
import {User, AppState, WebAuth} from '../../interfaces';

import * as AuthActions from '../../stores/auth/auth.actions';


@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {
  user: User = {
    username: "",
    email: "",
    firstName: "",
    lastName: "",
    artistName: "",
    phone: ""
  };


  auth: WebAuth;

  constructor(private apiService: ApiService,  private store: Store<AppState>) {}

  ngOnInit() {
    this.store.select((state: AppState ) => {
      return state.auth;
    }).subscribe((auth: WebAuth) => {
      this.auth = auth;
    });

    var sub = this.apiService.sendRequest('/account/?token='+ this.auth.token, 'get');
    sub.subscribe((user : User)=>{
      this.user = user;
    },function(err){
      console.log(err);
    });
  }

  editAccount(form) {
    const newUser: User = form;

    this.apiService.editAccount(this.auth.token, newUser).subscribe((result: WebAuth) => {
      console.log(result);
      this.store.dispatch(new AuthActions.LoginIn(result));
    }, err => {
      console.log(err);
      alert("une erreur est survenue : " + err.error.msg);
    });
  }
}
