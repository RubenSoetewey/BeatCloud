import { Component } from '@angular/core';
import { OnInit, Input } from '@angular/core';
import { Store } from '@ngrx/store';
import { UploadEvent, UploadFile } from 'ngx-file-drop';
import { FileSystemFileEntry, FileSystemDirectoryEntry } from 'ngx-file-drop/src/lib/ngx-drop/dom.types';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';

import {ApiService} from '../../services/api/api.service';
import {User, AppState, WebAuth} from '../../interfaces';
import {environment} from "../../../environments/environment";
import {Router} from "@angular/router";
import { ActivatedRoute, Params } from '@angular/router';

declare var WaveSurfer:any;

@Component({
  selector: 'app-music',
  templateUrl: './music.component.html',
  styleUrls: ['./music.component.css']
})
export class MusicComponent implements OnInit {
  sound: any = {};
  soundId: string = "";
  auth: WebAuth;
  id: string;
  uid: string;
  music: any = {};
  public files: UploadFile[] = [];
  public filesResults = [];

  constructor(private apiService: ApiService,  private store: Store<AppState>,
              private router: Router, private route: ActivatedRoute, private http: HttpClient) {}

  ngOnInit() {
    this.loadMusicId();
  }
  loadMusic(){
    var sub = this.apiService.sendRequest('/music/'+ this.id ,'get');
    sub.subscribe((music)=>{
      this.music = music.sound;
    }, (err)=>{
      console.log(err);
    })
  }
  private loadMusicId(){
    this.route.params.forEach((params: Params) => {
      if (params['id'] !== undefined) {
        this.id = params['id'];
        this.music.uid = params['uid'];
        this.loadMusic();
      }
    });
  }
}
