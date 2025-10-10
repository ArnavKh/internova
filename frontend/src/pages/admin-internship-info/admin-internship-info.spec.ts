import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminInternshipInfo } from './admin-internship-info';

describe('AdminInternshipInfo', () => {
  let component: AdminInternshipInfo;
  let fixture: ComponentFixture<AdminInternshipInfo>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminInternshipInfo]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminInternshipInfo);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
