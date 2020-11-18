import { Component, OnInit } from '@angular/core';
import { Employee } from '../employee';
import { EmployeeService } from '../employee.service'
import { Router } from '@angular/router';
import { Sort } from '@angular/material/sort';



@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {

  employees: Employee[];
  sortedData: Employee[];

  constructor(private employeeService: EmployeeService,
    private router: Router) {
      this.getEmployees();
  }

  refreshPage() {
    window.location.reload();
  }
  
  ngOnInit(): void {
    this.getEmployees();
  }

  private getEmployees() {
    this.employeeService.getEmployeesList().subscribe(data => {
      this.employees = data;
      this.sortedData = data.slice();
      
    });
  }

  sortData(sort: Sort) {
    const data = this.employees.slice();
    if (!sort.active || sort.direction === '') {
      this.sortedData = data;
      return;
    }

    this.sortedData = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'eid': return compare(a.eid, b.eid, isAsc);
        case 'sid': return compare(a.sid, b.sid, isAsc);
        case 'yos': return compare(a.yos, b.yos, isAsc);
        case 'name': return compare(a.name, b.name, isAsc);
        case 'position': return compare(a.position, b.position, isAsc);
        case 'salary': return compare(a.salary, b.salary, isAsc);
        default: return 0;
      }
    });
  }

  employeeDetails(id: number) {
    this.router.navigate(['employeedetails', id]);
  }

  updateEmployee(id: number) {

    this.router.navigate(['employee-update', id]);
  }
  supervisorDetails(id: number) {
    this.router.navigate(['supervisordetails', id]);
  }

  deleteEmployee(id: number) {
    if(confirm("Are you sure to delete Employee:"+id)) {
       this.employeeService.deleteEmployee(id).subscribe(data => {

     });
    window.location.reload();
    }
    
  }
}
function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
