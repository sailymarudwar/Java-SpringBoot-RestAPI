import { Component, OnInit } from '@angular/core';
import { SupervisorService } from '../supervisor.service';
import { Supervisor } from '../supervisor';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-supervisor-update',
  templateUrl: './supervisor-update.component.html',
  styleUrls: ['./supervisor-update.component.css']
})
export class SupervisorUpdateComponent implements OnInit {
   sid: number;
   departments: string[];
   supervisor: Supervisor = new Supervisor();

  constructor(private supervisorService: SupervisorService,
    private route: ActivatedRoute,
    private router: Router) { 
      this.getDepartmentList();
    }

  ngOnInit(): void {
    this.sid = this.route.snapshot.params['id'];

    this.supervisorService.getSupervisorById(this.sid).subscribe(data => {
      this.supervisor = data;
    }, error => console.log(error));
  }
  onSubmit(){
    this.supervisorService.updateSupervisor(this.sid, this.supervisor).subscribe( data =>{
      this.goToSupervisorList();
    }
    , error => console.log(error));
  }
  goToSupervisorList(){
    this.router.navigate(['/supervisors']);
  }
  private getDepartmentList() {
    this.supervisorService.getDepartmentList().subscribe(data => {
      this.departments = data;      
    });
  }

}
