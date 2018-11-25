import { Component, OnInit, ViewChild } from '@angular/core';
import { $ } from 'protractor';

@Component({
  selector: 'app-error-modal',
  templateUrl: './error-modal.component.html',
  styleUrls: ['./error-modal.component.css']
})
export class ErrorModalComponent implements OnInit {
  private errorMessages: string[] = new Array();

  private isModalOpen = false;
  
  show(errors: string[]): any {
    if(errors){
      console.log(errors)
      this.errorMessages.push(errors[0]);
    }
  }

  closeAlert(error: any){
    const index: number = this.errorMessages.indexOf(error);
    this.errorMessages.splice(index, 1);
  }
  constructor() { }

  ngOnInit() {
  }

}
