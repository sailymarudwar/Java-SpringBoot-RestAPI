import { Component, OnInit } from '@angular/core';
import { Supervisor } from '../supervisor';
import { Employee } from '../employee';
import { Title } from '@angular/platform-browser';
import { EmployeeService } from '../employee.service';
import { SupervisorService } from '../supervisor.service';

import { Router} from '@angular/router'


@Component({
  selector: 'app-employee-add',
  templateUrl: './employee-add.component.html',
  styleUrls: ['./employee-add.component.css']
})
export class EmployeeAddComponent implements OnInit {
  eidAlreadyExists = false; 
  employee: Employee = new Employee();
  supervisors: Supervisor[];

  constructor(private titleService: Title,private employeeService: EmployeeService,private supervisorService: SupervisorService,
    private router: Router) {
      this.getSupervisors();
     }

  ngOnInit(): void {
    this.titleService.setTitle("Add Employee");
  }

  saveEmployee() {
    this.employeeService.createEmployee(this.employee).subscribe(data => {
      console.log(data);
      this.goToEmployeeList();
    },
      error => {
        if (error.status == '409') {
          console.error("User already exists");
          this.eidAlreadyExists = true;
        } else {
           this.goToEmployeeList();
        }
      });
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
