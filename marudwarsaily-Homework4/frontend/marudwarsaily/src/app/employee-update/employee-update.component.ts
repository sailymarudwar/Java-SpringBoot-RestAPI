import { Component, OnInit } from '@angular/core';
import { EmployeeService } from '../employee.service';
import { Employee } from '../employee';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-employee-update',
  templateUrl: './employee-update.component.html',
  styleUrls: ['./employee-update.component.css']
})
export class EmployeeUpdateComponent implements OnInit {
   eid: number;
   employee: Employee = new Employee();

  constructor(private employeeService: EmployeeService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.eid = this.route.snapshot.params['id'];

    this.employeeService.getEmployeeById(this.eid).subscribe(data => {
      this.employee = data;
    }, error => console.log(error));
  }
  onSubmit(){
    this.employeeService.updateEmployee(this.eid, this.employee).subscribe( data =>{
      this.goToEmployeeList();
    }
    , error => console.log(error));
  }
  goToEmployeeList(){
    this.router.navigate(['/employees']);
  }

}
