import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import {MatNativeDateModule} from '@angular/material/core';
import { FormsModule} from '@angular/forms';
import {MAT_FORM_FIELD_DEFAULT_OPTIONS} from '@angular/material/form-field';
import {MatSortModule} from '@angular/material/sort'


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { EmployeeAddComponent } from './employee-add/employee-add.component';
import { EmployeeUpdateComponent } from './employee-update/employee-update.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { EmployeedetailsComponent } from './employeedetails/employeedetails.component';
import { EmployeeListComponent } from './employee-list/employee-list.component';
import { SupervisorAddComponent } from './supervisor-add/supervisor-add.component';
import { SupervisorUpdateComponent } from './supervisor-update/supervisor-update.component';
import { SupervisordetailsComponent } from './supervisordetails/supervisordetails.component';
import { SupervisorListComponent } from './supervisor-list/supervisor-list.component';





@NgModule({
  declarations: [
    AppComponent,
    EmployeeListComponent,
    EmployeeAddComponent,
    EmployeeUpdateComponent,
    EmployeedetailsComponent,
    SupervisorListComponent,
    SupervisorAddComponent,
    SupervisorUpdateComponent,
    SupervisordetailsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    MatNativeDateModule,
    BrowserAnimationsModule,
    MatSortModule
  ],
  providers: [{ provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: { appearance: 'fill' } },],
  bootstrap: [AppComponent]
})
export class AppModule { }
