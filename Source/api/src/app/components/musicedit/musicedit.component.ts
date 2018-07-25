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
  selector: 'app-soundedit',
  templateUrl: './musicedit.component.html',
  styleUrls: ['./musicedit.component.css']
})
export class MusicEditComponent implements OnInit {
  sound: any = {};
  soundId: string = "";
  auth: WebAuth;
  id: string;
  music: any = {};
  public files: UploadFile[] = [];
  public filesResults = [];

  constructor(private apiService: ApiService,  private store: Store<AppState>,
              private router: Router, private route: ActivatedRoute, private http: HttpClient) {}

  ngOnInit() {
    this.store.select((state: AppState ) => {
      return state.auth;
    }).subscribe((auth: WebAuth) => {
      this.auth = auth;
      this.loadMusicId();
      setTimeout(()=>{
        var sub = this.apiService.sendRequest('/music/'+ this.id ,'get');
        sub.subscribe((music)=>{
          this.music = music.sound;
        }, (err)=>{
          console.log(err);
        })
      }, 1000);
    });
  }

  private loadMusicId(){
    this.route.params.forEach((params: Params) => {
      if (params['id'] !== undefined) {
        this.id = params['id'];
      }
    });
  }

  public editMusic(form){
    form.token = this.auth.token;
    this.apiService.sendPost('/music/edit/' + this.music.id, form)
      .subscribe(res=>{
      this.music = res;
      alert("Modifié");
    }, err=>{
      alert('Une erreur est survenue');
      console.log(err);
    });
  }
  public dropped(event: UploadEvent) {
    console.log("dropped");
    this.files = event.files;
    for (const droppedFile of event.files) {

      // Is it a file wave?
      if (droppedFile.fileEntry.isFile) {
        const fileEntry = droppedFile.fileEntry as FileSystemFileEntry;
        fileEntry.file((file: File) => {
          const formData = new FormData();
          formData.append('image', file, droppedFile.relativePath);
          this.apiService.sendForm(formData, environment.app_url + '/image/upload/music?token=' + this.auth.token ,
            {
              token: this.auth.token,
              music: this.music.id
            }).subscribe(data => {
              this.filesResults.push({name: file.name, sucess: true});
              alert("Le fichier " +  file.name + " a été ajouté ");
              this.music = data;
            },err=>{
              this.filesResults.push({name: file.name, sucess: false});
              alert(err.error.error.message|| err.msg);
            })
        });
      } else {
        // It was a directory (empty directories are added, otherwise only files)
        const fileEntry = droppedFile.fileEntry as FileSystemDirectoryEntry;
        console.log(droppedFile.relativePath, fileEntry);
      }
    }
  }

  public fileOver(event){
    console.log(event);
  }

  public fileLeave(event){
    console.log(event);
  }
}
