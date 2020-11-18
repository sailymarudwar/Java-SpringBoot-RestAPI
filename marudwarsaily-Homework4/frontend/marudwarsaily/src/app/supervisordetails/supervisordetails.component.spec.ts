import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SupervisordetailsComponent } from './supervisordetails.component';

describe('SupervisordetailsComponent', () => {
  let component: SupervisordetailsComponent;
  let fixture: ComponentFixture<SupervisordetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SupervisordetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SupervisordetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
