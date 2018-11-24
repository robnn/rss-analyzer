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

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {


  public message: Observable<Message>;


  public wsstate: Observable<string>;

  private connected: boolean = false;
  private sentOutNodes: NodeHolder[] = new Array();
  private urlText: string;
  private candidates: CandidateHolder[] = new Array();
  private selectedCandidate: CandidateHolder;
  private interval: number;
  private attributes: string;
  constructor(private service: DetectionService,
    private stompService: StompService) { }

  ngOnInit() {
  }

  setUrl() {
    let urlHolder = new UrlHolder();
    urlHolder.pageUrl = this.urlText;
    this.service.detectForWebPage(urlHolder).subscribe(data => {
      this.candidates = new Array();
      data.forEach(x => {
        this.candidates.push(x);
      })
    });
  }

  setCandidate() {
    console.log(this.selectedCandidate);
    this.service.setFeed(this.selectedCandidate.tagWithDepth).subscribe();
    this.connect();
  }

  setInterval() {
    this.service.changeInterval(this.interval).subscribe();
  }

  resetSettings() {
    this.service.resetSettings().subscribe();
    this.candidates = new Array();
    this.sentOutNodes = new Array();
  }

  setAttributes() {
    var attributes = this.attributes.split(";");
    var holder = new AttributesHolder();
    holder.attributes = attributes;
    this.service.setNeededAttributes(holder).subscribe();
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
