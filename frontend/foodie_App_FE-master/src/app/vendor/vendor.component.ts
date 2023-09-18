import { Login } from './../models/login';
import { User } from './../models/user';
import { UserService } from './../services/user.service';
import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-vendor',
  templateUrl: './vendor.component.html',
  styleUrls: ['./vendor.component.css']
})
export class VendorComponent {
  constructor(private userService:UserService,private login:LoginService){}
  user:User = {}
  vendorForm:any = new FormGroup({
    vendor:new FormControl(''),
    password: new FormControl('',[Validators.required])
  })

  getErrorMessage(){}
  get vendor(){return this.vendorForm.get("vendor")}
  get password(){return this.vendorForm.get("password")}

  onSubmit(){
    this.user.email = String(localStorage.getItem('email'))
    this.user.password = this.vendorForm.value.password
    console.log(this.user)
    this.userService.vendor(this.user)
  }

  vendor1():boolean{
    if(localStorage.getItem('vendor') == "true"){
      console.log('true working')
      return true
    }
    console.log("Hum Kam nahi krra")
    return false
  }
}
