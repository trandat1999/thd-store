import {BaseObject} from "../../utils/BaseResponse";
import {Product, Warehouse} from "../setting/setting.model";
import {SearchObject} from "../../utils/search-object";

export interface Supplier extends BaseObject{
  email?:string;
  phoneNumber?:string;
  products?: Product[];
}

export interface InvoiceItem extends BaseObject{
  product?:Product;
  quantity?:number;
  price?: number;
}
export interface InventoryImport extends BaseObject{
  status?:string;
  supplier?:Supplier;
  warehouse?:Warehouse;
  items?: InvoiceItem[]
  total?:number;
  note?:string;
  importDate?:Date
}
export const InventoryImportStatus = {
  NEW: "New",
  PENDING_APPROVAL: "Pending approval",
  APPROVED: "Approved",
  TRANSPORT: "Transport",
  PAID: "Paid",
  WAREHOUSED: "Warehoused",
  CANCELLED: "Cancelled",
  ORDER : "Order",
}
export const InventoryImportStatusArray = [
  'NEW','ORDER','APPROVED','TRANSPORT','PAID','WAREHOUSED'
]
export interface InventoryImportSearch extends SearchObject{
  warehouseId?:number;
  supplierId?:number;
}
export const InventoryImportStatusColor = {
  NEW: "#84bcda",
  PENDING_APPROVAL: "#FFC107",
  APPROVED: "#2196F3",
  ORDER : "#9C27B0",
  TRANSPORT: "#FF5722",
  PAID: "#008000",
  WAREHOUSED: "#00296b",
  CANCELLED: "#fa0b2e",
}
