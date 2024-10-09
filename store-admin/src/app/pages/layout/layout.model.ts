export interface NavigationItem {
  translateKey?: string;
  name?: string;
  link?: string;
  iconType?: string;
  iconClass?: string;
  iconLink?: string;
  children?: NavigationItem[];
}

export const navigation: NavigationItem[] = [
  {
    translateKey: 'navigation.dashboard',
    iconType: "dashboard",
    children: [
      {
        name: 'Welcome',
        link: '/welcome'
      },
    ],
  },
  {
    translateKey: 'navigation.settings',
    iconType: "setting",
    children: [
      {
        translateKey: 'navigation.category',
        link: '/setting/category'
      }, {
        translateKey: 'navigation.country',
        link: '/setting/country'
      }, {
        translateKey: 'navigation.attribute',
        link: '/setting/attribute'
      }, {
        translateKey: 'navigation.product',
        link: '/setting/product'
      },{
        translateKey: 'navigation.productShow',
        link: '/setting/product-show'
      }, {
        translateKey: 'navigation.warehouse',
        link: '/setting/warehouse'
      },
    ],
  }, {
    translateKey: 'navigation.productImport',
    iconType: "shopping-cart",
    children: [
      {
        translateKey: 'navigation.supplier',
        link: '/product-import/supplier'
      }, {
        translateKey: 'navigation.inventoryImport',
        link: '/product-import/inventory-import'
      },
    ]
  }, {
    translateKey: 'navigation.sale',
    iconClass: "fa fa-shopping-bag",
    children: [
      {
        translateKey: 'navigation.saleInvoice',
        link: '/sale/invoice'
      },{
        translateKey: 'navigation.directSale',
        link: '/direct-sale'
      }
    ]
  },{
    translateKey: 'navigation.management',
    iconType: "appstore",
    children: [
      {
        translateKey: 'navigation.administrativeUnit',
        link: '/management/administrative-unit'
      },
      {
        translateKey: 'Quyền hạn',
        link: '/management/permission'
      },
    ]
  }
]
