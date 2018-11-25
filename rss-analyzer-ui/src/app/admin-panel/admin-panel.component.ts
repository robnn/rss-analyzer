import { Component, OnInit } from '@angular/core';
import { DetectionService } from '../detection.service';
import { UrlHolder } from '../model/UrlHolder';
import { CandidateHolder } from '../model/CandidateHolder';
import { Subscription, Observable } from 'rxjs';

import { Message } from '@stomp/stompjs';
import { StompService, StompState } from '@stomp/ng2-stompjs';
import { WebSocketConfig } from '../WebSocketConfig';

import { map } from 'rxjs/operators';
import { NodeHolder } from '../model/NodeHolder';
import { AttributesHolder } from '../model/AttributesHolder';
import { UserService } from '../user.service';
import { Router } from '@angular/router';
import { ErrorService } from '../error.service';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {


  public message: Observable<Message>;


  public wsstate: Observable<string>;

  private connected: boolean = false;
  public sentOutNodes: NodeHolder[] = new Array();
  public urlText: string;
  public candidates: CandidateHolder[] = new Array();
  private selectedCandidate: CandidateHolder;
  public interval: number;
  constructor(private service: DetectionService,
    private stompService: StompService,
    private userService: UserService,
    private router: Router,
    private errorService: ErrorService) { }

  ngOnInit() {
    if (!this.userService.getAuthToken()) {
      this.router.navigate(['/login']);
    } else {
      this.userService.getUserData2().subscribe();
    }
  }

  setUrl() {
    if(!this.urlText){
      this.errorService.addErrors(["Url must not be empty!"]);
    } else {
      let urlHolder = new UrlHolder();
      urlHolder.pageUrl = this.urlText;
      this.service.detectForWebPage(urlHolder).subscribe(data => {
        this.candidates = new Array();
        data.forEach(x => {
          this.candidates.push(x);
        })
      });
    }
  }

  setCandidate() {
    if(!this.selectedCandidate){
      this.errorService.addErrors(["You must select a candidate!"]);
    } else {
      this.service.setFeed(this.selectedCandidate.tagWithDepth).subscribe();
      this.connect();
    }
  }

  setInterval() {
    if(!this.interval){
      this.errorService.addErrors(["You must set the interval!"]);
    } else {
      this.service.changeInterval(this.interval).subscribe();
    }
    
  }

  resetSettings() {
    this.service.resetSettings().subscribe();
    this.candidates = new Array();
    this.sentOutNodes = new Array();
  }

  connect() {
    if(!this.connected){
      this.wsstate = this.stompService.state.pipe(map((state: number) => StompState[state]));
      this.message = this.stompService.subscribe(WebSocketConfig.topic);
      this.message.subscribe(data => {
        let node = JSON.parse(data.body) as NodeHolder;
        this.sentOutNodes.push(node);
      });
      this.connected = true;
    }
    
  }
}
