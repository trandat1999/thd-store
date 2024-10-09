import {
  Component,
  EventEmitter,
  HostBinding,
  Input,
  OnChanges,
  OnInit,
  Optional,
  Output,
  Self,
  SimpleChanges,
  TemplateRef,
  ViewChild
} from '@angular/core';
import {ControlValueAccessor, NgControl, Validators} from "@angular/forms";
import {TranslateService} from "@ngx-translate/core";
import {BooleanInput} from "ng-zorro-antd/core/types";
import {BehaviorSubject, catchError, debounceTime, map, Observable, of, switchMap} from "rxjs";
import {CommonService} from "../common.service";
import {SearchObject} from "../../../utils/search-object";

@Component({
  selector: 'thd-input',
  templateUrl: './nuclear-input.component.html',
  styleUrls: ['./nuclear-input.component.scss'],
})
export class NuclearInputComponent implements OnInit, ControlValueAccessor, OnChanges {
  static nextId = 0;
  @HostBinding()
  id: string = `nuclear-input-${NuclearInputComponent.nextId++}`;
  inputElement: NgControl;
  ngControl: NgControl;

  @ViewChild('inputElements1') set content1(content: NgControl) {
    if (content) {
      this.inputElement = content;
      const validators = this.ngControl?.control?.validator;
      this.inputElement?.control?.setValidators(validators ? validators : null);
    }
  }

  @ViewChild('inputElements2') set content2(content: NgControl) {
    if (content) {
      this.inputElement = content;
      const validators = this.ngControl?.control?.validator;
      this.inputElement?.control?.setValidators(validators ? validators : null);
    }
  }

  @ViewChild('inputElements3') set content3(content: NgControl) {
    if (content) {
      this.inputElement = content;
      const validators = this.ngControl?.control?.validator;
      this.inputElement?.control?.setValidators(validators ? validators : null);
    }
  }

  @ViewChild('inputElements4') set content4(content: NgControl) {
    if (content) {
      this.inputElement = content;
      const validators = this.ngControl?.control?.validator;
      this.inputElement?.control?.setValidators(validators ? validators : null);
    }
  }

  @ViewChild('inputElements5') set content5(content: NgControl) {
    if (content) {
      this.inputElement = content;
      const validators = this.ngControl?.control?.validator;
      this.inputElement?.control?.setValidators(validators ? validators : null);
    }
  }

  @Input() size: 'small' | 'default' | 'large' = 'default';
  @Input() isDisabled: boolean = false;
  @Input() hasFeedback: boolean = false;
  @Input() labelSpan: number = null;
  @Input() inputSpan: number = null;
  @Input() errorText: string = "";
  @Input() warningText: string = "";
  @Input() label: string = '';
  @Input() hint: string = '';
  @Input() tooltipTitle: string = null;
  @Input() placeHolder: string = '';
  @Input() type: 'text' | 'number' | 'email' | 'password' | 'date' | 'select' | 'editor' = 'text';
  @Input() items: any[] = [];
  @Input() bindLabel: string = null;
  @Input() bindValue: string = null;
  @Input() allowSearch: boolean = true;
  @Input() isTranslation: boolean = false;
  @Input() allowClear: BooleanInput = false;
  @Input() selectMode: "default" | "multiple" | "tags" = "default";
  @Input('value') val: any;
  @Input() step: number = 1;
  @Input() min: number = -Infinity;
  @Input() max: number = Infinity;
  @Input() showTime: boolean = false;
  @Input() showNow: boolean = true;
  @Input() showToday: boolean = true;
  @Input() dateTimeFormat: string;
  @Input() showLabel: boolean = true;
  @Input() prefixIcon: string;
  @Input() suffixIcon: string;
  @Input() prefix: string | TemplateRef<void>;
  @Input() suffix: string | TemplateRef<void>;
  @Input() addOnBefore: string | TemplateRef<void>;
  @Input() addOnAfter: string | TemplateRef<void>;
  @Input() addOnBeforeIcon: string;
  @Input() addOnAfterIcon: string;
  @Input() isSearch: boolean = false;
  @Input() offMb: boolean = false;
  @Output() valueChange: EventEmitter<any> = new EventEmitter<any>();
  @Input() disabledDate: (current: Date) => boolean
  @Input() isServerSearch = false;
  @Input() urlSearch = "";
  @Input() autoClearSearchValue = true;
  @Output() searchChange: EventEmitter<string> = new EventEmitter<string>();
  @Input() isWrapLabel: boolean = false;
  isLoading = false;
  isLoadingMore = false;
  searchChange$ = new BehaviorSubject("");
  searchObject: SearchObject = {
    pageIndex: 0,
    pageSize: 10,
    keyword: ""
  }

  onSearch(value: string): void {
    if (this.isServerSearch) {
      this.isLoading = true;
      this.searchChange$.next(value);
    }
    this.searchChange.emit(value);
  }

  loadMore(): void {
    if (this.isServerSearch) {
      this.isLoadingMore = true;
      this.searchObject.pageIndex = this.searchObject.pageIndex + 1;
      this.service.search(this.urlSearch, {...this.searchObject}).subscribe(data => {
        this.isLoadingMore = false;
        let temp = data.body?.content || [];
        this.items = [...this.items, ...temp]
      });
    }
  }

  get value(): any {
    return this.val;
  }

  set value(val: any) {
    this.val = val;
    if (this.onChange) {
      this.onChange(this.value);
      this.valueChange.emit(val);
    }
    this.onTouched();
    const errors = this.ngControl?.control?.errors;
    this.inputElement?.control?.setErrors(errors ? errors : null);
  }

  onChange: any = () => {
  };
  onTouched: any = () => {
  };

  constructor(public translate: TranslateService, @Self() @Optional() public control: NgControl,
              private service: CommonService) {
    if (this.control) {
      this.ngControl = this.control
      this.control.valueAccessor = this;
    }
  }

  get isRequired(): boolean {
    if (this.ngControl) {
      return this.ngControl?.control?.hasValidator(Validators.required);
    }
    return false;
  }

  writeValue(value: any): void {
    if (value) {
      if (this.type == "date") {
        this.val = new Date(value)
      } else {
        if(this.isServerSearch && this.bindValue==null && this.type == 'select'){
          if(this.selectMode!='multiple'){
            this.items.push(value)
          }
          if(this.selectMode=='multiple'){
            this.items = [...value]
          }
        }
        this.val = value;
      }
    } else {
      this.val = value;
    }
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  onSelectionChange(val: any) {
    // if(this.inputElement?.control){
    //   this.value = val;
    // }
    this.valueChange.emit(this.value);
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    this.isDisabled = isDisabled;
  }

  ngOnInit(): void {
    if (this.control) {
      this.control.control?.statusChanges.subscribe((status) => {
        this.inputElement?.control.markAsDirty();
      });
    }
    if (this.isServerSearch) {
      const getDataSelect = (name: string): Observable<any> => {
        this.searchObject.pageIndex = 0;
        return this.service.search(this.urlSearch, {...this.searchObject, keyword: name})
          .pipe(
            catchError(() => of({results: []})),
            map((res: any) => res.body?.content || [])
          )
      }
      const optionList$: Observable<any[]> = this.searchChange$
        .asObservable()
        .pipe(debounceTime(500))
        .pipe(switchMap(getDataSelect));
      optionList$.subscribe(data => {
        this.items = data;
        this.isLoading = false;
      });
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.errorText = changes['errorText']?.currentValue || this.errorText;
    this.label = changes['label']?.currentValue ? changes['label']?.currentValue : this.label;
    this.placeHolder = changes['placeHolder']?.currentValue ? changes['placeHolder']?.currentValue : this.placeHolder;
    this.warningText = changes['warningText']?.currentValue ? changes['warningText']?.currentValue : this.warningText;
    this.hint = changes['hint']?.currentValue ? changes['hint']?.currentValue : this.hint;
    const errors = this.ngControl?.control?.errors;
    this.inputElement?.control?.setErrors(errors ? errors : null);
  }

  compareFn = (o1: any, o2: any): boolean => {
    if (o1 && o2) {
      if (o1.value && o2.value) {
        return o1.value === o2.value
      }
      if (o1.key && o2.key) {
        return o1.key === o2.key
      }
      if (o1.id && o2.id) {
        return o1.id === o2.id
      }
      return o1 === o2;
    }
    return o1 === o2;
  }
}
