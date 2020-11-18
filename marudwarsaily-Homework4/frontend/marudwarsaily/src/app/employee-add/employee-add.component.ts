import { Component, OnInit } from '@angular/core';
import { Supervisor } from '../supervisor';
import { Employee } from '../employee';

import { EmployeeService } from '../employee.service';
import { SupervisorService } from '../supervisor.service';

import { Router } from '@angular/router';


@Component({
  selector: 'app-employee-add',
  templateUrl: './employee-add.component.html',
  styleUrls: ['./employee-add.component.css']
})
export class EmployeeAddComponent implements OnInit {
  employee: Employee = new Employee();
  supervisors: Supervisor[];

  constructor(private employeeService: EmployeeService,private supervisorService: SupervisorService,
    private router: Router) {
      this.getSupervisors();
     }

  ngOnInit(): void {
  }

  saveEmployee() {
    this.employeeService.createEmployee(this.employee).subscribe(data => {
      console.log(data);
      this.goToEmployeeList();
    },
      error => console.log(error));
  }

  goToEmployeeList() {
    this.router.navigate(['/employees']);
  }

  private getSupervisors() {
    this.supervisorService.getSupervisorsList().subscribe(data => {
      this.supervisors = data;      
    });
  }


  onSubmit() {
    console.log(this.employee);
    this.saveEmployee();
  }

}
