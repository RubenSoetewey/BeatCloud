import * as AuthActions from './auth.actions';
import { WebAuth } from '../../interfaces/';

export type Action = AuthActions.All;


const defaultState: WebAuth = {
  firstName: '',
  lastName: '',
  phone: '',
  email: '',
  artistName: '',
  rank: 0,
  token: '',
  userId: '',
  isAuth: false
};

export function AuthReducer(state: WebAuth = defaultState, action: Action) {
  switch (action.type ) {
    case AuthActions.LOGIN_IN:
      const newState: WebAuth = {
        firstName: action.payload.firstName,
        lastName: action.payload.lastName,
        phone: action.payload.phone,
        email: action.payload.email,
        artistName: action.payload.artistName,
        rank: action.payload.rank,
        token: action.payload.token,
        userId: action.payload.userId,
        isAuth: true
      };
      localStorage.setItem('authItem', JSON.stringify(newState));
      return newState;
    case AuthActions.LOG_OUT:
      localStorage.setItem('authItem', JSON.stringify(defaultState));
      return defaultState;
    default:
      return state;
  }
}
