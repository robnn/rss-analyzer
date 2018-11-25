import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { UrlHolder } from './model/UrlHolder';
import { CandidateHolder } from './model/CandidateHolder';
import { TagWithDepth } from './model/TagWithDepth';
import { AttributesHolder } from './model/AttributesHolder';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class DetectionService {
  private detectionUrl = 'http://localhost:8080/detection/'

  constructor(private httpClient: HttpClient,
    private userService: UserService) { }

  detectForWebPage(urlHolder: UrlHolder) : Observable<CandidateHolder[]> {
    return this.httpClient.post<CandidateHolder[]>(this.detectionUrl, urlHolder, {  headers :this.userService.getAuthTokenAsHttpHeader(null)});
  }

  setFeed(tagWithDepth: TagWithDepth)  : Observable<any> {
    return this.httpClient.post(this.detectionUrl + "setFeedable", tagWithDepth, {  headers :this.userService.getAuthTokenAsHttpHeader(null)});
  }

  changeInterval(interval: number) : Observable<any> {
    return this.httpClient.post(this.detectionUrl + "changeInterval" + "?interval=" + interval, null, {  headers :this.userService.getAuthTokenAsHttpHeader(null)});
  }

  resetSettings() : Observable<any> {
    return this.httpClient.post(this.detectionUrl + "stopDetection", null, {  headers :this.userService.getAuthTokenAsHttpHeader(null)});
  }
}
