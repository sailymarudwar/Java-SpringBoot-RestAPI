import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import { Supervisor } from './supervisor';

@Injectable({
  providedIn: 'root'
})
export class SupervisorService {

  private baseURL = "http://localhost:8080";

  constructor(private httpClient: HttpClient) { }
  
  getSupervisorsList(): Observable<Supervisor[]>{
    return this.httpClient.get<Supervisor[]>(`${this.baseURL}/supervisors`);
  }

  getSupervisorById(sid: number): Observable<Supervisor>{
    return this.httpClient.get<Supervisor>(`${this.baseURL}/supervisorBysid/${sid}`);
  }

  createSupervisor(supervisor: Supervisor): Observable<Object>{
    return this.httpClient.post(`${this.baseURL}/createSupervisor`, supervisor);
  }

  updateSupervisor(sid: number, supervisor: Supervisor): Observable<Object>{
    return this.httpClient.put(`${this.baseURL}/updateSupervisor/${sid}`, supervisor);
  }

  deleteSupervisor(id: number): Observable<Object>{
    return this.httpClient.delete(`${this.baseURL}/deleteSupervisor/${id}`);
  }
}
