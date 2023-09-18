import { Restaurant } from './../models/restaurant';
import { RestaurantService } from './../services/restaurant.service';
import { ActivatedRoute } from '@angular/router';
import { UserService } from './../services/user.service';
import { CityService } from './../services/city.service';
import { Component } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-restaurant-edit',
  templateUrl: './restaurant-edit.component.html',
  styleUrls: ['./restaurant-edit.component.css']
})
export class RestaurantEditComponent {
  constructor(private fb:FormBuilder,private city1:CityService,private resService:RestaurantService, private userservice:UserService,private activate:ActivatedRoute){}
  form:FormGroup|any = this.fb.group({
    name: ['', Validators.required],
    description: ['', Validators.required],
    city: ['', Validators.required],
    // image: ['', ],
  });
  submitForm(){
    console.log(this.form.value)
    let restaurant:Restaurant = {
      id:this.restaurant.id,
      name:this.form.value.name,
      description:this.form.value.description,
      city:this.form.value.city
    }
    this.userservice.updateRestaurantByVendor(restaurant).subscribe(res=>{console.log("formUpdated");
    })
  }
  onFileSelected(event:any){
  }
  editProfile(img:HTMLInputElement){}
  restaurant:Restaurant={}

  cities:String[]=[];
  ngOnInit(): void {
    this.activate.paramMap.subscribe(
      data => {
       let id = data.get('id') ?? 0;
        this.resService.getResturantById(id).subscribe((res:Restaurant)=>{
          this.restaurant=res
        })
      }
    )
    this.city1.getCity().subscribe(
      res=> {
        this.cities = res
      }
    )
  }
  updateImage(event:any){
    this.resService.updateImage( this.restaurant.image,event.target.files[0]).subscribe(res=>{console.log("succesImage");
    let img:any = document.getElementById("profile-img")
    img.src = window.URL.createObjectURL(event.target.files[0])
    console.log(img.src)
    })
  }
}
