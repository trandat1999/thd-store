export interface SearchObject {
  pageIndex?:number;
  pageSize?:number;
  keyword?:string;
  voided?:boolean;
}
export interface CategorySearch extends SearchObject {
}
export interface CountrySearch extends SearchObject {
}
export interface MovieSearch extends SearchObject {
}
export interface ProductSearch extends SearchObject {
  status?: number
  priceFrom?: number
  priceTo?: number
}
export interface WarehouseSearch extends SearchObject {
  provinceId?:number;
  districtId?:number;
  communeId?:number;
}
