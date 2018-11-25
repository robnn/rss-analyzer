import { Component, ViewChild, OnDestroy, OnInit, EventEmitter } from '@angular/core';
import { ErrorModalComponent } from './error-modal/error-modal.component';
import { ErrorService } from './error.service'
import { Subject } from 'rxjs';

import { takeUntil } from 'rxjs/operators';
import { UserService } from './user.service';
import { LoginComponent } from './login/login.component';
import { Router } from '@angular/router'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnDestroy, OnInit {
  subscription: any;
  title = 'filemanager';
  realUserName : string;

  constructor(private errorService: ErrorService,
              private userService: UserService,
              private router: Router) {
    this.initializeErrors();
  }

  ngOnInit(): void {
    if(this.userService.getAuthToken()){
      this.router.navigate(['/admin-panel']);
    }
  }
  private ngUnsubscribe = new Subject();

  @ViewChild('modalError')
  errorModal: ErrorModalComponent; 

  loginComponent: LoginComponent;

  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }


  initializeErrors(): any {
    this
            .errorService
            .getErrors()
            .pipe(takeUntil(this.ngUnsubscribe))
            .subscribe((errors) =>
            {
                //this.displayErrorRef.error = errors
                this.errorModal.show(errors);
            });
  }

  logout(){
    this.userService.invalidateData();
    this.router.navigate(['/login']);
  }


}
