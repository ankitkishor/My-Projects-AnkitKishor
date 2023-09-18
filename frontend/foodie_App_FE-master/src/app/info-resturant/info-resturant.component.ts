import { Feedback } from './../models/feedback';
import { RestaurantService } from './../services/restaurant.service';
import { ActivatedRoute } from '@angular/router';
import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Restaurant } from '../models/restaurant';
import { UserService } from '../services/user.service';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-info-resturant',
  templateUrl: './info-resturant.component.html',
  styleUrls: ['./info-resturant.component.css']
})
export class InfoResturantComponent implements OnInit {
  @Input() restaurantId:any
  restaurant: Restaurant={} ;
  rating3:any = 0

 text:string="";
 value:boolean=true;

  constructor(private user:UserService,private fb: FormBuilder,private login:LoginService,private activate:ActivatedRoute,private restaurantService:RestaurantService){
  }

  isLiked:boolean = false;
  hover1:boolean = false;
  ngOnInit() {
    console.log("kam kr")
    if( localStorage.getItem("isLoggedIn")=="true" ){
      console.log("Kam kr")
      this.restaurantService.getIfLiked(this.restaurantId).subscribe( {next:(data:any)=>{
        console.log("like"+ data)
      this.isLiked = data
    }}

    )
  }


    console.log("resturantid"+this.restaurantId)
        this.restaurantService.getResturantById(this.restaurantId).subscribe(
       res=>{console.log(res+"resturant");
            this.restaurant=res
            this.rating3 = res.rating
      },err=>{console.log("not found resturant id")}
        );

  }
  @Output() feed:EventEmitter<boolean> = new EventEmitter<boolean>
  feedback(data:any){
    if(data != ""){
    console.log(data+"datahere");
    let feedback1:Feedback ={}
    feedback1.user= String(localStorage.getItem("email"))
    feedback1.feedback = data
    feedback1.image = Number(localStorage.getItem("image"))
    this.restaurantService.addFeedback(feedback1,this.restaurantId).subscribe( {next:(res:any)=>{console.log("sucess")
    this.text = ""
    this.feed.emit(true)
  }})
  }}
  change (){
    console.log(this.rating3)
    this.restaurantService.addRating(this.restaurantId,this.rating3).subscribe(
      res=>{
        console.log("working")
      }
    )
  }

  unlike(){this.isLiked = false;
    this.user.removeFav(this.restaurantId).subscribe(res=>console.log("liked removed"))
  }
  like(){this.isLiked = true;
  this.user.addFav(this.restaurantId).subscribe(res=>{console.log("fav Added");
  })
  }

  hover2(){
    if( localStorage.getItem("isLoggedIn")=="true" ){
      this.hover1 = true
      this.restaurantService.getRating(this.restaurantId).subscribe(
        res=>{
          console.log("Rating "+res)
          this.rating3 = res
        }
      )
    }
  }


  leave1(){

    this.hover1 = false
    this.restaurantService.getResturantById(this.restaurantId).subscribe(
      res=>{console.log(res+"resturant");
           this.restaurant=res
           this.rating3 = res.rating
     })
  }
  @Output()
  sending:EventEmitter<boolean>=new EventEmitter<boolean>();

  onClick(value:boolean){
    console.log(value)
    this.sending.emit(value)
  }
}
