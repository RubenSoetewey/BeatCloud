import { Action } from '@ngrx/store';
import { WebAuth } from '../../interfaces/index';

export const LOGIN_IN = 'LOGIN_IN';
export const LOG_OUT = 'LOGIN_OUT';

export class LoginIn implements Action {
  readonly type = LOGIN_IN;
  constructor(public payload: WebAuth) {}
}

export class LogOut implements Action {
  readonly type = LOG_OUT;
  constructor() {}
}

export type All = LoginIn | LogOut;
