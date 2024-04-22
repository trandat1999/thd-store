import {BaseObject} from "../../utils/BaseResponse";
import {AdministrativeUnit} from "../management/management.model";

export interface Category extends BaseObject{
  level?:number;
  parentId?:number;
}
export interface Country extends BaseObject{
}
export interface Attribute extends BaseObject{
}
export interface File extends BaseObject {
  size?: number;
  path?: string;
}
export interface Product extends BaseObject{
  files?: File[];
  attributes?: KeyValue[];
}
export interface KeyValue{
  key?: string;
  value?: string;
}
export interface Warehouse extends BaseObject{
  province?:AdministrativeUnit;
  district?:AdministrativeUnit;
  commune?:AdministrativeUnit;
  address?:string;
}
