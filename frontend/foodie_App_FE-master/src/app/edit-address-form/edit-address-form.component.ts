import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-edit-address-form',
  templateUrl: './edit-address-form.component.html',
  styleUrls: ['./edit-address-form.component.css']
})
export class EditAddressFormComponent {
  constructor(private userService:UserService,private fb:FormBuilder,private router:Router){}
  // form:FormGroup|any = this.fb.group
  addressForm:FormGroup = this.fb.group({
    name: new FormControl('', Validators.required),
    number: new FormControl('', [Validators.required, Validators.minLength(10), Validators.maxLength(10), Validators.pattern("^[0-9]*$")]),
    street: new FormControl('', Validators.required),
    city: new FormControl('', Validators.required),
    locality: new FormControl('', Validators.required),
  });
  onSubmit(){
this.userService.updateAddress(this.addressForm.value).subscribe(res=>{
  console.log("updated success");

},err=>{console.log("notUpdated");
})
  }

}
