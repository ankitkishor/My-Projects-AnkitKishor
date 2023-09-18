import { UserService } from './user.service';
import { RestaurantService } from './restaurant.service';
import { Restaurant } from './../models/restaurant';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Login } from '../models/login';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  isloggedIn:Boolean = false;
  token:any = ''
  user:User = {}
  get LoggedIn(){
    this.isloggedIn= Boolean(localStorage.getItem("isLoggedIn"))
    return this.isloggedIn
  }
   constructor(private http:HttpClient,private route:Router) { }
  ngOnInit(){
    const email:any = localStorage.getItem('email')
    this.user.email = String(email)
    const logged:any = localStorage.getItem("isLoggenIn")
    this.isloggedIn = Boolean(logged)
    const name:any = localStorage.getItem("name")
    this.user.name = String(name)
    const image:any = localStorage.getItem("image")
    this.user.image = Number(image)
  }
   login(log:Login){
     this.http.post("http://localhost:9000/api/v2/login",log).subscribe({
       next:(data:any)=>{
         console.log(data)
         if(data != ''){
             this.token = data.token
             this.isloggedIn = true
             localStorage.setItem("isLoggedIn","true")

             console.log(this.token)
             console.log(this.isloggedIn)
             localStorage.setItem("token",this.token)
             const headers = new HttpHeaders (
               {
                 "authorization" : "Bearer "+ localStorage.getItem("token")
               }
             );
             this.http.get("http://localhost:9000/api/v1/user/"+log.email,{headers:headers}).subscribe({
               next:(data:any)=>{
                if(localStorage.getItem("isLoggedIn"))
                 if(data != ''){
                     this.user = data
                    localStorage.setItem("vendor",String(this.user.vendor))
                    localStorage.setItem("email",String(this.user.email))
                    localStorage.setItem("name",String(this.user.name))
                    localStorage.setItem("image",String(this.user.image))
                   }
         }
     })
   }},error:(err:any)=>{alert("Wrong Credentials")}
 })}


 logout(){
    window.localStorage.clear();
    localStorage.setItem("isLoggedIn","false")
 }
}
