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
  displayName?: string;
  total?: number;
  paid?: boolean;
  paymentType?: string;
  code?: string;
  status?: string;
  saleDate?: Date
  note?: string;
  items?: InvoiceItem[];
  customer?: Customer
  customerPay?:number
  showQr?: boolean
}
export interface Customer{
  displayName?: string;
  phoneNumber?: string;
  id?:number;
}

export const SalesInvoiceType = []
export const PaymentType = []
export const SalesInvoiceStatus = []
