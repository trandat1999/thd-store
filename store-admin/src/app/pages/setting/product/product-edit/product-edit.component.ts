import {Component, OnInit} from '@angular/core';
import {VOIDED_CHOICE, getErrorMessage} from "../../../../utils/ConstUtil";
import {Attribute, KeyValue, Product} from "../../setting.model";
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {ProductService} from "../product.service";
import {ActivatedRoute, Router} from "@angular/router";
import {FileService} from "../../../../services/file.service";
import {TranslateService} from "@ngx-translate/core";
import {ApplicationConfigService} from "../../../../services/application-config.service";
import {NzUploadFile} from "ng-zorro-antd/upload";
import {AttributeService} from "../../attribute/attribute.service";

@Component({
  selector: 'thd-product-edit',
  templateUrl: './product-edit.component.html',
  styleUrls: ['./product-edit.component.scss']
})
export class ProductEditComponent implements OnInit {
  formGroup: FormGroup;
  entity : Product = {};
  serverUrl: string = "";
  voidedList = VOIDED_CHOICE
  fileList : NzUploadFile[] = [];
  previewVisible: boolean = false;
  previewImage: string;
  uploading : boolean = false;
  attributeList: Attribute[] = [];
  constructor(private productService: ProductService,
              private router : Router,
              private route : ActivatedRoute,
              private fileService: FileService,
              private configService: ApplicationConfigService,
              private attributeService: AttributeService,
              private translate: TranslateService) {
    this.serverUrl = this.configService.apiBaseUrl;
    let id = this.route.snapshot.params["id"];
    console.log(id);
    if(id){
      this.productService.get(id).subscribe(data => {
        if(data.body){
          this.entity = data.body;
          this.formGroup.patchValue(this.entity);
          this.updateFileList();
          this.updateAttributes();
        }
      })
    }
  }
  updateAttributes(){
    if(this.entity.attributes && this.entity.attributes.length>0){
      for(let attribute of this.entity.attributes){
        this.attributes.push(this.initKeyValue(attribute));
      }
    }
  }
  updateFileList(){
    this.fileList = [];
    if(this.entity.files && this.entity.files.length>0){
      for(let file of this.entity.files){
        this.fileList.push({
          url: this.serverUrl+'/api/v1/publish/files/'+file?.id,
          status: 'done',
          name: file.name,
          response: file,
          filename: file.name,
          uid: file?.id.toString()
        })
      }
    }
  }
  getErrorMessage(control:string):string{
    return getErrorMessage(control,this.formGroup,this.translate);
  }
  back(){
    this.router.navigate(['/setting/product'])
  }
  onSubmit(){
    this.productService.save(this.formGroup.getRawValue()).subscribe(data=>{
      if(data.code==400){
        if (data.body) {
          Object.keys(data.body).forEach(key => {
            let field = key.replace("]","");
            field = field.replace("[",".");
            this.formGroup.get(field)?.setErrors({ 'serverError': true, 'serverErrorMess': data.body[key] });
          });
          return;
        }
      }
      if(data.code==200){
        this.router.navigate(["/setting/product"])
      }
      return;
    })
  }
  addAttribute() {
    this.attributes.push(this.initKeyValue({}));
  }
  ngOnInit(): void {
    this.formGroup = new FormGroup({
      id : new FormControl(this.entity.id),
      name: new FormControl(this.entity.name, [Validators.required]),
      code: new FormControl(this.entity.code, [Validators.required]),
      shortDescription: new FormControl(this.entity.shortDescription),
      description: new FormControl(this.entity.description),
      attributes: new FormArray([]),
      files: new FormControl(this.entity.files, [Validators.required]),
      voided: new FormControl(this.entity.voided ? this.entity.voided : false),
    });
    this.attributeService.getAll().subscribe(data => {
      this.attributeList = data?.body || [];
    })
  }
  get attributes(){
    return this.formGroup?.get("attributes") as FormArray
  }
  get dataTable(){
    return [...this.attributes.controls]
  }
  initKeyValue(data: KeyValue){
    return new FormGroup({
      key: new FormControl(data.key, [Validators.required]),
      value: new FormControl(data.value, [Validators.required]),
    })
  }
  handlePreview = async (file: NzUploadFile): Promise<void> => {
    this.previewImage = this.serverUrl+'/api/v1/publish/files/'+(file.response?.body?.id || file.uid);
    this.previewVisible = true;
  };
  handleDownload = async (file: NzUploadFile): Promise<void> => {
    this.fileService.get(file.response?.body?.id || file.uid).subscribe(data =>{
      const a = document.createElement('a');
      const objectUrl = URL.createObjectURL(data);
      a.href = objectUrl;
      a.download = file.name;
      a.click();
      URL.revokeObjectURL(objectUrl);
    })
  };
  handleChangeFileUpload(info: { file: NzUploadFile }): void {
    switch (info.file.status) {
      case 'uploading':
        this.uploading = true;
        break;
      case 'done':
        this.uploading = false;
        info.file.url = this.serverUrl+'/api/v1/publish/files/'+(info.file.response?.body?.id || info.file.uid);
        info.file.uid = (info.file.response?.body?.id || info.file.uid);
        info.file.response = info.file.response?.body;
        this.fileList.push(info.file);
        let files = this.formGroup.get("files")?.value || [];
        files.push(info.file.response);
        this.formGroup.get("files")?.setValue(files);
        break;
      case 'error':
        this.uploading = false;
        break;
      case "removed":
        let indexOf = this.fileList.indexOf(info.file.response);
        this.fileList.splice(indexOf, 1);
        let formValues = this.fileList.map(item => item.response)
        this.formGroup.get("files")?.setValue(formValues);
        break;
      case "success":
        this.uploading = false;
        break;
    }
  }
}
