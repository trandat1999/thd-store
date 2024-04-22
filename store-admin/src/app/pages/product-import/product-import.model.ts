import {BaseObject} from "../../utils/BaseResponse";
import {Product} from "../setting/setting.model";

export interface Supplier extends BaseObject{
  email?:string;
  phoneNumber?:string;
  products?: Product[];
}
export interface InventoryImport extends BaseObject{
  status?:string;
  supplier?:Supplier;
  totalPrice?:number;
  note?:string;
}
