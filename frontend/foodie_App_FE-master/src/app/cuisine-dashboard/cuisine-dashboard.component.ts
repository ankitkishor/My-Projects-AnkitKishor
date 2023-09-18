import { RestaurantService } from './../services/restaurant.service';
import { Restaurant } from './../models/restaurant';
import { Router, ActivatedRoute } from '@angular/router';
import { Component } from '@angular/core';
import { Cuisine } from '../models/cuisine';

@Component({
  selector: 'app-cuisine-dashboard',
  templateUrl: './cuisine-dashboard.component.html',
  styleUrls: ['./cuisine-dashboard.component.css']
})
export class CuisineDashboardComponent {
  cuisines: Cuisine[] = []
  id:any
  onOpen:boolean= true;
  value:boolean=false;
  constructor(private route:Router,private restuarant :RestaurantService,private activate:ActivatedRoute){}
  ngOnInit(): void {
    this.activate.paramMap.subscribe(
      data => {
       this.id = data.get('id') ?? 0;
        this.restuarant.getCuisine(+this.id).subscribe(
          res => {
            this.cuisines = res
          }
        );
      }
    )
  }
  onChange(event:any){
    console.log(event)
    this.onOpen = event
  }
  feed1:boolean = false
  feed(event:boolean) {
    this.feed1 = true
    // this.feed1 = false
  }
  feed2(event:boolean){
    this.feed1 = false
  }

}
