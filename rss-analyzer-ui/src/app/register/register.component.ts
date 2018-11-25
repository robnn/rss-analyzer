import { Component, OnInit } from '@angular/core';
import { User } from '../model/User';
import { ErrorService } from '../error.service';
import { UserService } from '../user.service';
import { Router } from '@angular/router'

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  private user : User = new User();
  private secondPassword : string;
  constructor(private errorService: ErrorService,
              private userService: UserService,
              private router: Router) { }

  ngOnInit() {
  }

  registerClick(){
    if(this.user.password != this.secondPassword){
      this.errorService.addErrors(["Passwords are not equal!"]);
    }
    if(!this.user.emailAddress){
      this.errorService.addErrors(["Email address is empty!"]);
    }
    if(!this.user.password){
      this.errorService.addErrors(["Password is empty!"]);
    }
    if(!this.user.userName){
      this.errorService.addErrors(["Username is empty!"]);
    }
    if(!this.user.realName){
      this.errorService.addErrors(["Real name is empty!"]);
      return;
    }
    console.log("registering");
    this.userService.register(this.user).subscribe(res => {
      this.router.navigate(['/login']);
    });
  }

}
