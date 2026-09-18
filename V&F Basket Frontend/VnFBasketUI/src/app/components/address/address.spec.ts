import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserAddress } from './address';

describe('Address', () => {
  let component: UserAddress;
  let fixture: ComponentFixture<UserAddress>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserAddress]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserAddress);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
