import { Component } from '@angular/core';
import { OnInit, Input } from '@angular/core';
import { Store } from '@ngrx/store';

import {ApiService} from '../../services/api/api.service';
import {User, AppState, WebAuth} from '../../interfaces';
import {environment} from "../../../environments/environment";
import {Router} from "@angular/router";

declare var WaveSurfer:any;

@Component({
  selector: 'app-soundplayer',
  templateUrl: './soundplayer.component.html',
  styleUrls: ['./soundplayer.component.css']
})
export class SoundPlayerComponent implements OnInit {
  playerId: string = "";
  wavesurfer: any = {};
  public auth: WebAuth;
  isPlaying: boolean = false;
  playerLoaded: boolean = false;

  @Input() sound;

  constructor(private apiService: ApiService,  private store: Store<AppState>, private router: Router) {}

  ngOnInit() {
    var playerId = 'player-' + this.sound.id;
    var soundUrl = environment.app_url + '/public/content/' + this.sound.uid;
    this.playerId = playerId;
    var sound = this.sound;
    var playerImageUrl = environment.app_url + "/icons/wave.svg";

    if(sound.imageUrl){
      playerImageUrl = sound.imageUrl;
    }
    this.store.select((state: AppState ) => {
      return state.auth;
    }).subscribe((auth: WebAuth) => {
      this.auth = auth;
    });

    setTimeout(()=>{
      var player = document.getElementById(playerId);
      var playerImage = document.getElementById("img-" + playerId);
      if(playerImage){
        playerImage.setAttribute('style', "background-image: url(" + playerImageUrl + ");")
      }
      if(player){
        player.innerHTML= "";
      }

      this.wavesurfer = WaveSurfer.create({
        container: '#' + playerId,
        // The color can be either a simple CSS color or a Canvas gradient
        waveColor: "#D3D3D3",
        progressColor: '#f50',
        cursorColor: '#f50',
        // This parameter makes the waveform look like SoundCloud's player
        barWidth: 3
      });
      this.wavesurfer.load(soundUrl);
      this.wavesurfer.on('ready', ()=>{
        this.playerLoaded = true;
        //this.wavesurfer.play();
      });
    }, 1000)
  }

  playPause(){
    if(!this.playerLoaded){
      return;
    }
    this.wavesurfer.playPause();
    this.isPlaying = !this.isPlaying;
  }

  removeSound(){
    var sub = this.apiService.sendRequest('/'+ this.sound.type + '/remove/' + this.sound.id, "post", this.auth.token);
    var playerId = this.playerId;
    sub.subscribe(()=>{
      alert("Instrument supprimé");
      var containerPlayer = document.getElementById("container-" + playerId);
      if(containerPlayer){
        containerPlayer.remove();
      }
    },(err)=>{
      console.log(err);
      alert(err.msg || err.error.message);
    })
  }

  editSound(){
    this.router.navigate(['/music/edit/'+ this.sound.id]);
  }
}
