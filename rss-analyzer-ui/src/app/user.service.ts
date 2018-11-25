import { Injectable, Output, EventEmitter } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from './model/User';
import { Observable, of } from 'rxjs';
import { ErrorService } from './error.service';
import { Token } from './model/Token';
import { LocalStorageService } from './local-storage.service';
import { Router } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class UserService {
  private usersUrl = 'http://localhost:8080/users/'

  private user: User;

  private static authToken: string;

  constructor(private http: HttpClient,
    private errorService: ErrorService,
    private localStorageService: LocalStorageService,
    private router: Router) { }

  register(user: User): Observable<User> {
    return this.http.post<User>(this.usersUrl, user)
  }

  login(user: User) {
    this.http.post<Token>(this.usersUrl + "login", user).subscribe(x => {
      UserService.authToken = x.token;
      this.localStorageService.storeUserToken(x.token);
      this.router.navigate(['/admin-panel'])
    }, error => {
      this.errorService.addErrors(error.error.messages);
    })
  }

  getAuthToken(): string {
    if (this.localStorageService.retrieveUserToken() && !UserService.authToken) {
      UserService.authToken = this.localStorageService.retrieveUserToken();
    }
    return UserService.authToken;
  }

  getAuthTokenAsHttpHeader(headers: HttpHeaders): HttpHeaders {
    if (!headers) {
      headers = new HttpHeaders();
    }
    if(!this.getAuthToken()){
      this.errorService.addErrors(['You are not logged in!']);
      return null;
    }
    headers = headers.append("authToken", this.getAuthToken());
    return headers;
  }

  getUserForToken(): Observable<User> {
    return this.http.get<User>(this.usersUrl + "byToken/" + UserService.authToken);
  }

  public getUserData2() : Observable<User> {
    if(!this.user){
      var observable = this.getUserForToken();
      observable.subscribe(user => {
        this.user = user;
      }, error => {
        this.errorService.addErrors(error.error.messages);
      })
      return observable;
    } else {
      return of(this.user);
    }
  }

  public invalidateData() {
    this.localStorageService.clearUserToken();
    UserService.authToken = null;
    this.user = null;
  }

  public getUser(): User{
    if(this.user){
      return this.user;
    }
    return null;
  }
}
