import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import { User, AppState,WebAuth } from '../../interfaces';
import { Store } from '@ngrx/store';

@Injectable()
export class ApiService {

  constructor(private http: HttpClient) {}


  createAccount(newUser: User) {
    let urlRegister;
    urlRegister = environment.app_url + '/account/register';
    return this.http.post(urlRegister, newUser);
  }

  editAccount(token, user: User) {
    let urlEdit;
    urlEdit = environment.app_url + '/account/edit?token=' + token;
    return this.http.post(urlEdit, user);
  }

  auth(data) {
    let urlLogin;
    urlLogin = environment.app_url + '/account/auth';
    return this.http.post(urlLogin, data);
  }

  logout(token) {
    let urlLogin;
    urlLogin = environment.app_url + '/account/logout?token=' + token;
    return this.http.get(urlLogin);
  }

  getUserSound(token) {
    let urlSounds;
    urlSounds = environment.app_url + '/sounds/user?token=' + token;
    return this.http.get(urlSounds);
  }
  getUserMusic(token) {
    let urlMusic;
    urlMusic = environment.app_url + '/music/user?token=' + token;
    return this.http.get(urlMusic);
  }
  getPublicMusic() {
    let urlMusic;
    urlMusic = environment.app_url + '/public/musics/';
    console.log(urlMusic);
    return this.http.get(urlMusic);
  }

  sendRequest(url, method, token = "") {
    let urlFinal;
    urlFinal = environment.app_url + url;
    if(token && token.length){
      if(url.includes('?')) {
        urlFinal += "&"
      }else if(!url.includes('&') && !url.includes('?')){
        urlFinal += "?";
      }

      urlFinal+= "token=" + token;
    }
    return this.http[method](urlFinal);
  }

  sendForm(formData, url, headersData) {
    const headers = new HttpHeaders(headersData);
    return this.http.post(url,
      formData, { headers: headers });
  }

  sendPost(url, data) {
    let urlLogin;
    urlLogin = url;
    return this.http.post(environment.app_url + urlLogin, data);
  }
}
