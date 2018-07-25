export interface WebAuth {
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  artistName: string;
  rank: number;
  token: string;
  isAuth: boolean;
  userId: string;
}

export interface AppState {
  auth: WebAuth;
  search: string;
}

export interface User {
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  artistName: string;
  phone: string;
  birthDate? :any;
}
