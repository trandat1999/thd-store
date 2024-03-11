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
