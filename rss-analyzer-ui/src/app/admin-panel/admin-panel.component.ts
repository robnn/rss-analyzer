import { Component, OnInit } from '@angular/core';
import { DetectionService } from '../detection.service';
import { UrlHolder } from '../model/UrlHolder';
import { CandidateHolder } from '../model/CandidateHolder';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {

  private urlText : string;
  private candidates : CandidateHolder[] = new Array();
  private selectedCandidate : CandidateHolder;
  private interval: number;
  constructor(private service: DetectionService) { }

  ngOnInit() {
  }

  setUrl(){
    let urlHolder = new UrlHolder();
    urlHolder.pageUrl = this.urlText;
    this.service.detectForWebPage(urlHolder).subscribe(data => {
      data.forEach(x => {
        this.candidates.push(x);
      })
    });
  }

  setCandidate(){
    console.log(this.selectedCandidate);
    this.service.setFeed(this.selectedCandidate.tagWithDepth).subscribe(data => {

    });
  }

  setInterval(){
    this.service.changeInterval(this.interval).subscribe(data => {

    });
  }
}
