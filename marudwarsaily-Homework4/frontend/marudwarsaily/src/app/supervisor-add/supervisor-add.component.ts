import { Component, OnInit } from '@angular/core';
import { Supervisor } from '../supervisor';
import { SupervisorService } from '../supervisor.service';
import { Router } from '@angular/router';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-supervisor-add',
  templateUrl: './supervisor-add.component.html',
  styleUrls: ['./supervisor-add.component.css']
})
export class SupervisorAddComponent implements OnInit {
  supervisor: Supervisor = new Supervisor();
  departments: string[];  
  sidAlreadyExists = false; 


  constructor(private titleService: Title,private supervisorService: SupervisorService,
   
    private router: Router) {
      this.getDepartmentList();
     }
  

  ngOnInit(): void {
  }

  saveSupervisor() {
    this.supervisorService.createSupervisor(this.supervisor).subscribe(data => {
      console.log(data);
      this.goToSupervisorList();
    },
    error => {
      if (error.status == '409') {
        console.error("Supervisor already exists");
        this.sidAlreadyExists = true;
      } else {
         this.goToSupervisorList();
      }
    });
  }

  goToSupervisorList() {
    this.router.navigate(['/supervisors']);
  }

  onSubmit() {
    console.log(this.supervisor);
    this.saveSupervisor();
  }

  private getDepartmentList() {
    this.supervisorService.getDepartmentList().subscribe(data => {
      this.departments = data;      
    });
  }

}
