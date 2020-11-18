import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SupervisorUpdateComponent } from './supervisor-update.component';

describe('SupervisorUpdateComponent', () => {
  let component: SupervisorUpdateComponent;
  let fixture: ComponentFixture<SupervisorUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SupervisorUpdateComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SupervisorUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
