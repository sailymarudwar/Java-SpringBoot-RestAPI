import { Component, OnInit } from '@angular/core';
import { Supervisor } from '../supervisor';
import { SupervisorService } from '../supervisor.service'
import { Router } from '@angular/router';
import { Sort } from '@angular/material/sort';



@Component({
  selector: 'app-supervisor-list',
  templateUrl: './supervisor-list.component.html',
  styleUrls: ['./supervisor-list.component.css']
})
export class SupervisorListComponent implements OnInit {

  supervisors: Supervisor[];
  sortedData: Supervisor[];

  constructor(private supervisorService: SupervisorService,
    private router: Router) {
      this.getSupervisors();
  }

  refreshPage() {
    window.location.reload();
  }
  
  ngOnInit(): void {
    this.getSupervisors();
  }

  private getSupervisors() {
    this.supervisorService.getSupervisorsList().subscribe(data => {
      this.supervisors = data;
      this.sortedData = data.slice();
      
    });
  }

  sortData(sort: Sort) {
    const data = this.supervisors.slice();
    if (!sort.active || sort.direction === '') {
      this.sortedData = data;
      return;
    }

    this.sortedData = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'sid': return compare(a.sid, b.sid, isAsc);
        case 'name': return compare(a.name, b.name, isAsc);
        case 'department': return compare(a.department, b.department, isAsc);
        default: return 0;
      }
    });
  }

  supervisorDetails(id: number) {
    this.router.navigate(['supervisordetails', id]);
  }

  updateSupervisor(id: number) {

    this.router.navigate(['supervisor-update', id]);
  }

  deleteSupervisor(id: number) {
    if(confirm("Are you sure to delete Supervisor:"+id)) {
       this.supervisorService.deleteSupervisor(id).subscribe(data => {

     });
    window.location.reload();
    }
    
  }
}
function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
