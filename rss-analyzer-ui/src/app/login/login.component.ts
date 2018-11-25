import { Component, OnInit, Input } from '@angular/core';
import { UserService } from '../user.service';
import { User } from '../model/User'
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public user: User;
  constructor(private userService: UserService) { }

  ngOnInit() {
    this.user = new User()
  }

  loginClick(){
    this.userService.login(this.user);
  }

}
