import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EmployeeListComponent } from './employee-list/employee-list.component';
import { EmployeeAddComponent } from './employee-add/employee-add.component';
import { EmployeeUpdateComponent } from './employee-update/employee-update.component';
import { EmployeedetailsComponent } from './employeedetails/employeedetails.component';
import { SupervisorListComponent } from './supervisor-list/supervisor-list.component';
import { SupervisorAddComponent } from './supervisor-add/supervisor-add.component';
import { SupervisorUpdateComponent } from './supervisor-update/supervisor-update.component';
import { SupervisordetailsComponent } from './supervisordetails/supervisordetails.component';


const routes: Routes = [
  {path: 'employees', component: EmployeeListComponent},
  {path: 'employee-add', component: EmployeeAddComponent},
  {path: '', redirectTo: 'employees', pathMatch: 'full'},
  {path: 'employee-update/:id', component: EmployeeUpdateComponent},
  {path: 'employeedetails/:id', component: EmployeedetailsComponent},
  {path: 'supervisors', component: SupervisorListComponent},
  {path: 'supervisor-add', component: SupervisorAddComponent},
  {path: '', redirectTo: 'supervisors', pathMatch: 'full'},
  {path: 'supervisor-update/:id', component: SupervisorUpdateComponent},
  {path: 'supervisordetails/:id', component: SupervisordetailsComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
