import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';

import {ApiService} from '../../services/api/api.service';
import {User, AppState, WebAuth} from '../../interfaces';
import {Router} from "@angular/router";
import {environment} from "../../../environments/environment";
import {FileSystemDirectoryEntry, FileSystemFileEntry} from "ngx-file-drop/src/lib/ngx-drop/dom.types";
import {UploadEvent} from "ngx-file-drop";


@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  users$;
  musics$;
  plugins$;
  auth: WebAuth;

  constructor(private apiService: ApiService,  private store: Store<AppState>, private router: Router) {}

  ngOnInit() {
    this.store.select((state: AppState ) => {
      return state.auth;
    }).subscribe((auth: WebAuth) => {
      this.auth = auth;
      setTimeout(()=>{
        if(!this.auth.isAuth){
          this.router.navigateByUrl('/home');
          return;
        }
        this.loadUsers();
      }, 1000);
    });
  }

  loadUsers(){
    this.users$ = this.apiService.sendRequest('/admin/users', 'get', this.auth.token);
  }
  loadMusics(){
    this.apiService.sendRequest('/admin/sounds', 'get', this.auth.token).subscribe(results=>{
      this.musics$ = results;
    },err=>{
      alert("Une erreur est survenue");
      console.log(err);
    });
  }
  loadPlugins(){
    this.plugins$ = this.apiService.sendRequest('/public/plugins', 'get', this.auth.token);
  }

  banUser(user){
    this.apiService.sendPost('/admin/user/ban', {
      token: this.auth.token,
      userId: user.id,
    }).subscribe((result)=>{
      this.loadUsers();
    },((err)=>console.log(err)));
  }


  public dropped(event: UploadEvent) {
    console.log("dropped");
    for (const droppedFile of event.files) {

      // Is it a file wave?
      if (droppedFile.fileEntry.isFile) {
        const fileEntry = droppedFile.fileEntry as FileSystemFileEntry;
        fileEntry.file((file: File) => {
          const formData = new FormData();
          formData.append('plugin', file, droppedFile.relativePath);

          this.apiService.sendForm(formData, environment.app_url + '/upload/plugin', {token: this.auth.token})
            .subscribe(data => {
              this.loadPlugins();
            },err=>{
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
  public deletePlugin(plugin){
    this.apiService.sendPost('/plugin/remove/'+ plugin.id, {
      token: this.auth.token,
    }).subscribe((result)=>{
      this.loadPlugins();
    },((err)=>console.log(err)));
  }
}
