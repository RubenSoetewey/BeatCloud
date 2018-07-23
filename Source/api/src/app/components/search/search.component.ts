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
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  musics$;
  auth: WebAuth;

  constructor(private apiService: ApiService,  private store: Store<AppState>, private router: Router) {}

  ngOnInit() {
    this.store.select((state: AppState ) => {
      return state.search;
    }).subscribe((term: string) => {
      this.apiService.sendRequest('/public/all?term='+ term, 'get').subscribe((results : Array<any>)=>{
        this.musics$ = results;
      });
    });
  }
}
