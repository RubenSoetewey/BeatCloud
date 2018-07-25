import { Action } from '@ngrx/store';

export const SETTERM = 'SETTERM';

export class SetTerm implements Action {
  readonly type = SETTERM;
  constructor(public payload: string) {}
}
export type All = SetTerm;
