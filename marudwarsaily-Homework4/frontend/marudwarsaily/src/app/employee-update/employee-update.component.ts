import { Component, OnInit } from '@angular/core';
import { EmployeeService } from '../employee.service';
import { SupervisorService } from '../supervisor.service';
import { Employee } from '../employee';
import { Title } from '@angular/platform-browser';
import { Supervisor } from '../supervisor';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-employee-update',
  templateUrl: './employee-update.component.html',
  styleUrls: ['./employee-update.component.css']
})
export class EmployeeUpdateComponent implements OnInit {
  eid: number;
  eidAlreadyExists = false; 
  supervisors: Supervisor[];
  employee: Employee = new Employee();

  constructor(private titleService: Title ,private employeeService: EmployeeService,private supervisorService: SupervisorService,
    private route: ActivatedRoute,
    private router: Router) {
            this.getSupervisors();
     }

  ngOnInit(): void {
    this.eid = this.route.snapshot.params['id'];
    this.titleService.setTitle("Update Employee");
    this.employeeService.getEmployeeById(this.eid).subscribe(data => {
      this.employee = data;
    }, error => console.log(error));
  }
  onSubmit(){
    this.employeeService.updateEmployee(this.eid, this.employee).subscribe( data =>{
      this.goToEmployeeList();
    },  error => {
        if (error.status == '409') {
          console.error("User already exists");
          this.eidAlreadyExists = true;
        } else {
           this.goToEmployeeList();
        }
      });
  }
  private getSupervisors() {
    this.supervisorService.getSupervisorsList().subscribe(data => {
      this.supervisors = data;      
    });
  }
  goToEmployeeList(){
    this.router.navigate(['/employees']);
  }

}
