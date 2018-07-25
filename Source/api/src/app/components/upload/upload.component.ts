import {Component, OnInit} from '@angular/core';
import { UploadEvent, UploadFile } from 'ngx-file-drop';
import { FileSystemFileEntry, FileSystemDirectoryEntry } from 'ngx-file-drop/src/lib/ngx-drop/dom.types';
import {AppState, WebAuth} from "../../interfaces";
import {Store} from "@ngrx/store";
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';

import {ApiService} from "../../services/api/api.service";
import {environment} from "../../../environments/environment";


@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
export class UploadComponent implements OnInit{

  public files: UploadFile[] = [];
  public filesResults = [];
  auth : WebAuth;

  constructor(private apiService: ApiService,  private store: Store<AppState>, private http: HttpClient) {}

  ngOnInit() {
    this.store.select((state: AppState ) => {
      return state.auth;
    }).subscribe((auth: WebAuth) => {
      this.auth = auth;
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
          formData.append('file', file, droppedFile.relativePath);

          this.apiService.sendForm(formData, environment.app_url + '/sound/upload', {token: this.auth.token})
            .subscribe(data => {
              this.filesResults.push({name: file.name, sucess: true});
              alert("Le fichier " +  file.name + " a été ajouté ");
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
