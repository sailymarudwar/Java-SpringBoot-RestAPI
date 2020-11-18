import { Component, OnInit } from '@angular/core';
import { Employee } from '../employee';
import { EmployeeService } from '../employee.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-employee-add',
  templateUrl: './employee-add.component.html',
  styleUrls: ['./employee-add.component.css']
})
export class EmployeeAddComponent implements OnInit {
  employee: Employee = new Employee();


  constructor(private employeeService: EmployeeService,
    private router: Router) { }

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

  onSubmit() {
    console.log(this.employee);
    this.saveEmployee();
  }

}
