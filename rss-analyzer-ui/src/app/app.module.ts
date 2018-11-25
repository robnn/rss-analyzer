import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { AdminPanelComponent } from './admin-panel/admin-panel.component';
import { StompService, StompConfig } from '@stomp/ng2-stompjs';
import { stompConfig } from './WebSocketConfig';
import { XmlPipe } from './XmlPipe';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ErrorModalComponent } from './error-modal/error-modal.component'
import { StorageServiceModule } from 'angular-webstorage-service';

const appRoutes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'admin-panel', component: AdminPanelComponent },
  { path: 'register', component: RegisterComponent },
  { path: '', redirectTo: 'login', pathMatch: 'full' }
];

@NgModule({
  declarations: [
    AppComponent,
    AdminPanelComponent,
    XmlPipe,
    LoginComponent,
    RegisterComponent,
    ErrorModalComponent
  ],
  imports: [
    RouterModule.forRoot(appRoutes, { enableTracing: false }),
    BrowserModule,
    FormsModule,
    HttpClientModule,
    StorageServiceModule,
    NgbModule
  ],
  providers: [StompService,
    {
      provide: StompConfig,
      useValue: stompConfig
    }],
  bootstrap: [AppComponent]
})
export class AppModule { }
