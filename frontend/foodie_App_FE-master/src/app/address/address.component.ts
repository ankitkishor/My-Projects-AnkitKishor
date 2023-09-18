import { Router } from '@angular/router';
import { Address } from './../models/address';
import { UserService } from './../services/user.service';
import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css']
})
export class AddressComponent {
  constructor(private userService:UserService,private fb:FormBuilder,private router:Router){}
  // form:FormGroup|any = this.fb.group
  addressForm:FormGroup = this.fb.group({
    name: new FormControl('', Validators.required),
    number: new FormControl('', [Validators.required, Validators.minLength(10), Validators.maxLength(10), Validators.pattern("^[0-9]*$")]),
    street: new FormControl('', Validators.required),
    city: new FormControl('', Validators.required),
    locality: new FormControl('', Validators.required),
  });

  onSubmit() {
    // Handle form submission here
    console.log(this.addressForm.value);
    this.userService.addAddress(this.addressForm.value).subscribe(res=>{console.log("added");this.router.navigateByUrl("addressdash")
    },err=>{console.log("notAdded");

    }) }
}
