import { CartComponent } from './../cart/cart.component';
import { UserService } from './../services/user.service';
import { Component, Input, Output } from '@angular/core';
import { Cuisine } from '../models/cuisine';
import { RestaurantService } from '../services/restaurant.service';
import { CartService } from '../services/cart.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-cuisine-cards',
  templateUrl: './cuisine-cards.component.html',
  styleUrls: ['./cuisine-cards.component.css']
})
export class CuisineCardsComponent {
  constructor(private service:RestaurantService,private userService:UserService,private cart : CartService){}

    @Input() cuisine: Cuisine = {
      price: 0,
      quantity: 0
    }
    addedToCart:boolean= false
    ngOnInit(){
      this.userService.getAddedToCart(this.cuisine.id).subscribe(
        res =>{
          this.addedToCart = res
        }
      )
    }

    addCart(){
      console.log(this.cuisine)
      this.service.addToCart(this.cuisine).subscribe(data=>{console.log(data+"sucess")
      this.userService.totalAmount=data
      this.cart.cartLength = 0
      this.userService.getCartItems().subscribe({
        next:data =>{
          this.cart.cartLength = Number(data)
          console.log(Number(data) + "working")
          this.userService.getAddedToCart(this.cuisine.id).subscribe(
            res =>{
              this.addedToCart = res
            }
          )
        }
      }
        // res => {
        //   this.cart.cartLength = res
        // }

      )
    },err=>{console.log("error")})
        }
}
