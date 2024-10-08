import {BaseObject} from "../../utils/BaseResponse";

export interface AdministrativeUnit extends BaseObject{
  parent?:AdministrativeUnit;
  level?:number
  shortName?:string
  expand?:boolean
  children?:AdministrativeUnit[]
}

export interface Permission{
  id?: number;
  voided?:boolean;
  module?:string;
  action?: string;
}
