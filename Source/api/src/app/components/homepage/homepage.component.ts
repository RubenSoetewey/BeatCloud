import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Store } from '@ngrx/store';

import {ApiService} from '../../services/api/api.service';
import {User, AppState, WebAuth} from '../../interfaces';

import * as AuthActions from '../../stores/auth/auth.actions';
import {environment} from "../../../environments/environment";
import {Router} from "@angular/router";


@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomePageComponent implements OnInit {
  musics: any[] = [];
  auth: WebAuth;

  constructor(private apiService: ApiService,  private store: Store<AppState>, private router: Router) {}

  ngOnInit() {
    this.store.select((state: AppState ) => {
      return state.auth;
    }).subscribe((auth: WebAuth) => {
      this.auth = auth;
    });
    this.loadMusics();
  }

  loadMusics(){
    this.apiService.getPublicMusic().subscribe((results :any)=>{
      results.map(music=>{
        if(!music.imageUrl){
          music.imageUrl = environment.app_url + "/icons/wave.svg";
        }
        return music;
      });
      this.musics = results;
      console.log(results);
    },err=>{
      alert("Une erreur est survenue");
      console.log(err);
    });
  }

  viewMusic(music){
    this.router.navigate(['/music/'+ music.id + '/' + music.uid]);
  }
}
