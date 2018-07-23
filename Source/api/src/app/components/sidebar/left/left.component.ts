import
{ Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import {Router} from "@angular/router";
import {ApiService} from '../../../services/api/api.service';
import {User, AppState, WebAuth} from '../../../interfaces/index';
import * as AuthActions from '../../../stores/auth/auth.actions';

declare var jquery:any;
declare var $ :any;

@Component({
  selector: 'app-left-sidebar',
  templateUrl: './left.component.html',
  styleUrls: ['./left.component.css']
})
export class LeftSideBarComponent implements OnInit {
  auth: WebAuth;
  error: string = '';

  constructor(private store: Store<AppState>, private apiService: ApiService, private router: Router) {}

  ngOnInit() {
    this.store.select((state: AppState ) => {
      return state.auth;
    }).subscribe((auth: WebAuth) => {
      this.auth = auth;
    });
  }

  onSubmit(form) {
    this.apiService.auth(form).subscribe((auth: WebAuth) => {
      $('#modalLogin').modal('hide');
      this.store.dispatch(new AuthActions.LoginIn(auth));
      this.router.navigateByUrl('/home');
    }, (err) => {
      alert("L'authentification a échoué, merci de réessayer.");
      console.log(err);
    });
  }

  logout() {
    this.apiService.logout(this.auth.token).subscribe(() => {}, (err) => {
      console.log(err);
    });
    this.store.dispatch(new AuthActions.LogOut());
    this.router.navigateByUrl('/home');
  }
}
