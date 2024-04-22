import {Injectable} from '@angular/core';
import {BaseService} from "../../../services/base.service";
import {AdministrativeUnit} from "../management.model";

@Injectable({
  providedIn: 'root'
})
export class AdministrativeUnitService {
  private readonly URL = "/api/v1/administrative-units"

  constructor(private base: BaseService) {
  }

  getAllProvince() {
    return this.base.get(this.URL + "/all-province");
  }

  getAllByParent(parentId: number) {
    return this.base.get(this.URL + "/all-by-parent/" + parentId);
  }

  get(id: number) {
    return this.base.get(this.URL + "/" + id);
  }

  save(entity: AdministrativeUnit) {
    return this.base.post(this.URL, entity);
  }

  update(entity: AdministrativeUnit, id: number) {
    return this.base.put(this.URL + "/" + id, entity);
  }

  delete(id: number) {
    return this.base.delete(this.URL + "/" + id);
  }
}
