import { Component, OnInit } from '@angular/core';
import { Employee } from '../employee';
import { ActivatedRoute } from '@angular/router';
import { EmployeeService } from '../employee.service';


@Component({
  selector: 'app-employeedetails',
  templateUrl: './employeedetails.component.html',
  styleUrls: ['./employeedetails.component.css']
})
export class EmployeedetailsComponent implements OnInit {
  eid: number
  employee: Employee
  constructor(private route: ActivatedRoute, private employeService: EmployeeService) { }
  ngOnInit(): void {
    this.eid = this.route.snapshot.params['id'];

    this.employee = new Employee();
    this.employeService.getEmployeeById(this.eid).subscribe( data => {
      this.employee = data;
    });
  }

}
