import { UserService } from './../services/user.service';
import { UrlTree, Router, ActivatedRoute } from '@angular/router';
import { Cuisine } from './../models/cuisine';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-add-cuisine',
  templateUrl: './add-cuisine.component.html',
  styleUrls: ['./add-cuisine.component.css']
})
export class AddCuisineComponent {
  image1:any;

  constructor(private formBuilder: FormBuilder,private userService:UserService,private router:Router,private activate:ActivatedRoute) { }


    cuisineForm: FormGroup | any= this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      price: ['', Validators.required],
      type: ['', Validators.required],
      // tags: ['',[]],
      image: [null]
    });


  onSubmit() {
    this.activate.paramMap.subscribe(
      data => {
      let id = data.get('id') ?? 0;
      console.log(id)
      console.log(this.cuisineForm.value)
      let cuisine:Cuisine={
        price: 0,
        quantity: 0
      };
      cuisine.name=this.cuisineForm.value.name;
      cuisine.description=this.cuisineForm.value.rating;
      cuisine.price=this.cuisineForm.value.price;
      cuisine.type=this.cuisineForm.value.type;
    this.userService.addCuisine(this.convert(cuisine),id)
        this.router.navigateByUrl("/vendorRestaurantDashboard")

      }
    )
    console.log(this.cuisineForm.value);

    // cuisine.tags=this.cuisineForm.value.tag;
    // this.userService.addCuisine(this.convert(cuisine))
  }
  onFileSelected(event:any){
    this.image1 = event.target.files[0];
  }


  convert(cuisine:Cuisine):FormData{
    const formdata = new FormData();
    formdata.append('cuisine',
    new Blob([JSON.stringify(cuisine)],{type:'application/json'})
    );
    formdata.append('image', this.image1

        // ,this.image1.name
        );

  return formdata;
  }

}
