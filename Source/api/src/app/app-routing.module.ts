import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { HomePageComponent } from './components/homepage/homepage.component';
import { EditComponent } from './components/edit/edit.component';
import { UploadComponent } from './components/upload/upload.component';
import {SoundsComponent} from "./components/sounds/sounds.component";
import {MusicEditComponent} from "./components/musicedit/musicedit.component";
import {AdminComponent} from "./components/admin/admin.component";
import {MusicComponent} from "./components/music/music.component";
import {SearchComponent} from "./components/search/search.component";


const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'edit', component: EditComponent },
  { path: 'home', component: HomePageComponent },
  { path: 'upload', component: UploadComponent },
  { path: 'sounds', component: SoundsComponent },
  { path: 'music/edit/:id', component: MusicEditComponent },
  { path: 'music/:id/:uid', component: MusicComponent },
  { path: 'admin', component: AdminComponent },
  { path: 'search', component: SearchComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
