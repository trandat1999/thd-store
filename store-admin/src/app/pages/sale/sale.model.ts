import {AdministrativeUnit} from "../management/management.model";
import {InvoiceItem} from "../product-import/product-import.model";

export interface SaleInvoice {
  id?: number
  type?: string;
  province?: AdministrativeUnit;
  district?: AdministrativeUnit;
  commune?: AdministrativeUnit;
  address?: string;
  phoneNumber?: string;
  total?: number;
  paid?: boolean;
  paymentType?: string;
  code?: string;
  status?: string;
  saleDate?: Date
  note?: string;
  items?: InvoiceItem[];
  customer?: Customer
}
export interface Customer{
  fullName?: string;
  phoneNumber?: string;
  id?:number;
}

export const SalesInvoiceType = []
export const PaymentType = []
export const SalesInvoiceStatus = []
