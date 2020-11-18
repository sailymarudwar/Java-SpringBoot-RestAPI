import { Component, OnInit } from '@angular/core';
import { Supervisor } from '../supervisor';
import { ActivatedRoute } from '@angular/router';
import { SupervisorService } from '../supervisor.service';


@Component({
  selector: 'app-supervisordetails',
  templateUrl: './supervisordetails.component.html',
  styleUrls: ['./supervisordetails.component.css']
})
export class SupervisordetailsComponent implements OnInit {
  sid: number
  supervisor: Supervisor
  constructor(private route: ActivatedRoute, private employeService: SupervisorService) { }
  ngOnInit(): void {
    this.sid = this.route.snapshot.params['id'];

    this.supervisor = new Supervisor();
    this.employeService.getSupervisorById(this.sid).subscribe( data => {
      this.supervisor = data;
    });
  }

}
