import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EmployeeListComponent } from './employee-list/employee-list.component';
import { EmployeeAddComponent } from './employee-add/employee-add.component';
import { EmployeeUpdateComponent } from './employee-update/employee-update.component';
import { EmployeedetailsComponent } from './employeedetails/employeedetails.component';

const routes: Routes = [
  {path: 'employees', component: EmployeeListComponent},
  {path: 'employee-add', component: EmployeeAddComponent},
  {path: '', redirectTo: 'employees', pathMatch: 'full'},
  {path: 'employee-update/:id', component: EmployeeUpdateComponent},
  {path: 'employeedetails/:id', component: EmployeedetailsComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
