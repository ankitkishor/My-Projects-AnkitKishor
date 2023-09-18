import { Cuisine } from "./cuisine"

export type Order={
    "cuisines"?:Cuisine[],
    "totalBillAmount"?:number
    "time"?:string,
    "date"?:string
}
