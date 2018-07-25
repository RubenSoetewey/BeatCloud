import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule   } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { StoreModule } from '@ngrx/store';
import { FileDropModule } from 'ngx-file-drop';
import { SnotifyModule, SnotifyService, ToastDefaults } from 'ng-snotify';


import { AuthReducer } from './stores/auth/auth.reducer';
import { SearchReducer } from './stores/search/search.reducer';
import { AppRoutingModule } from './app-routing.module';
import { RootComponent } from './components/root/root.component';
import {  HomePageComponent } from './components/homepage/homepage.component';
import { LoginComponent } from './components/login/login.component';
import { EditComponent } from './components/edit/edit.component';
import { RegisterComponent } from './components/register/register.component';
import { LeftSideBarComponent } from './components/sidebar/left/left.component';
import { TopSideBarComponent } from './components/sidebar/top/top.component';
import { UploadComponent } from './components/upload/upload.component';
import { SoundsComponent } from './components/sounds/sounds.component';
import { SoundPlayerComponent } from './components/soundplayer/soundplayer.component';
import { MusicEditComponent } from './components/musicedit/musicedit.component';
import {AdminComponent} from "./components/admin/admin.component";
import {MusicComponent} from "./components/music/music.component";

import { ApiService } from './services/api/api.service';
import {SearchComponent} from "./components/search/search.component";

@NgModule({
  declarations: [
    RootComponent,
    HomePageComponent,
    LeftSideBarComponent,
    TopSideBarComponent,
    LoginComponent,
    RegisterComponent,
    EditComponent,
    UploadComponent,
    SoundsComponent,
    SoundPlayerComponent,
    MusicEditComponent,
    AdminComponent,
    MusicComponent,
    SearchComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    StoreModule.forRoot({
      auth: AuthReducer,
      search: SearchReducer,
    }),
    FileDropModule,
    SnotifyModule,
  ],
  providers: [
    ApiService,
    { provide: 'SnotifyToastConfig', useValue: ToastDefaults},
    SnotifyService
  ],
  bootstrap: [RootComponent]
})
export class AppModule {}
