import { Router } from '@angular/router';
import { UserService } from './../services/user.service';
import { Component, OnInit } from '@angular/core';
import { Address } from '../models/address';

@Component({
  selector: 'app-address-edit',
  templateUrl: './address-edit.component.html',
  styleUrls: ['./address-edit.component.css']
})
export class AddressEditComponent implements OnInit {
  constructor(private userService:UserService,private router:Router){}
ngOnInit(): void {
  this.userService.getAddress().subscribe(res=>{
    this.locations=res
  })
}

locations:Address[]=[]



edit(){
this.router.navigateByUrl("editAddressForm")
}
delete1(id:any){
  console.log("working" +id)
  this.userService.deleteAddress(id).subscribe(res=>{console.log("deleted");
  this.ngOnInit()
  },err=>{console.log("NotDeleted");
  })
}

add(){
  this.router.navigateByUrl("address")
}
}
