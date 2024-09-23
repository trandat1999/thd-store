import {Component, OnInit} from '@angular/core';
import {SaleInvoice} from "../sale/sale.model";
import {NzTabChangeEvent} from "ng-zorro-antd/tabs/interfaces";
import {Product} from "../setting/setting.model";
import {PublicService} from "../../services/public.service";
import {ProductSearch} from "../../utils/search-object";
import {ApplicationConfigService} from "../../services/application-config.service";
import {ToastrService} from "ngx-toastr";
import {TranslateService} from "@ngx-translate/core";
import {NzMessageService} from "ng-zorro-antd/message";

@Component({
  selector: 'thd-direct-sale',
  templateUrl: './direct-sale.component.html',
  styleUrls: ['./direct-sale.component.scss']
})
export class DirectSaleComponent implements OnInit{
  ngOnInit(): void {
    this.searchProduct()
  }
  constructor(private publicService : PublicService,
              private toast: ToastrService,
              private translate: TranslateService,
              private message: NzMessageService,
              private configService: ApplicationConfigService,) {
    this.serverUrl = this.configService.apiBaseUrl;
  }
  serverUrl: string = "";
  searchRequest : ProductSearch = {
    pageIndex: 1,
    pageSize: 20
  }
  saleInvoices: SaleInvoice[] = [{}];
  selectedIndex = 0;
  entity: SaleInvoice = {}
  drawerVisible = false;
  isAddCustomer = false;
  products: Product[] = [];
  totalElement = 0;
  selectedProduct: any;
  now : Date
  selectProduct(){
    if(this.selectedProduct){
      this.addProduct(this.selectedProduct);
    }
    this.selectedProduct = null;
  }
  searchProduct(){
    let obj = {...this.searchRequest};
    obj.pageIndex = this.searchRequest.pageIndex-1;
    this.publicService.getPageProduct(obj).subscribe(data=>{
      if(data?.body){
        this.products = data?.body.content;
        this.totalElement = data?.body.totalElements;
      }
    })
  }
  closeTab({ index }: { index: number }): void {
    this.saleInvoices.splice(index, 1);
    if(this.saleInvoices.length==0){
      this.saleInvoices.push({})
      this.selectedIndex = this.saleInvoices.length-1;
      this.entity = this.saleInvoices[this.selectedIndex];
    }
  }
  newTab(): void {
    this.saleInvoices.push({})
    this.selectedIndex = this.saleInvoices.length;
    this.entity = this.saleInvoices[this.saleInvoices.length-1];
  }
  changeTab({index}:NzTabChangeEvent): void {
    this.entity = this.saleInvoices[index];
  }
  openDrawer(){
    this.drawerVisible = true;
  }
  addCustomer(){
    this.isAddCustomer = true;
  }
  saveCustomer(){

  }
  searchChange(value: string){
    if(value){
      this.entity.phoneNumber = value;
    }
  }
  pageChange(pageIndex : number){
    this.searchRequest.pageIndex = pageIndex;
    this.searchProduct();
  }
  addProduct(product: Product){
    if(!this.entity.items){
      this.entity.items = [];
      this.entity.items.push({product: product,price: product.price,quantity: 1});
      this.entity.items = [...this.entity.items]
      return;
    }
    for(let item of this.entity.items){
      if(item.product.id == product.id){
        item.quantity++;
        return;
      }
    }
    this.entity.items.push({product: product,price: product.price,quantity: 1});
    this.entity.items = [...this.entity.items];
  }
  getTotalPrice(){
    let rs = 0;
    for(let item of this.entity.items || []){
      rs += item.quantity*item.price;
    }
    this.entity.total = rs;
    return rs;
  }
  saveInvoice(){
    if(!this.entity.items || this.entity.items.length==0){
      this.message.error(this.translate.instant("Chưa có mặt hàng"));
    }
  }
  printInvoice(){
    this.now = new Date();
    const content = document.getElementById("receipt")?.innerHTML;
    const printWindow = window.open('', '', 'height=600,width=800');
    printWindow?.document.write('<html lang=""><head><title>In hóa đơn</title></head><body>');
    printWindow?.document.write('<style>@media print { #receipt { width: 80mm; font-size: 12px; } }</style>');
    printWindow?.document.write(content ||'');
    printWindow?.document.write('</body></html>');
    printWindow?.document.close();
    printWindow?.focus();
    printWindow?.print();
  }
  protected readonly console = console;
}
