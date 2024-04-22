export interface BaseResponse{
  timestamp?: Date;
  body?: any;
  message?: string;
  status?: string;
  code?: number;
}
export interface BaseObject {
  name?:string;
  code?:string;
  description?:string;
  shortDescription?:string;
  id?:number;
  voided?:boolean;
}
