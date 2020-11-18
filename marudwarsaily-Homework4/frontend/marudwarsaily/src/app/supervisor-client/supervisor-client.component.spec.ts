import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SupervisorClientComponent } from './supervisor-client.component';

describe('SupervisorClientComponent', () => {
  let component: SupervisorClientComponent;
  let fixture: ComponentFixture<SupervisorClientComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SupervisorClientComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SupervisorClientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
