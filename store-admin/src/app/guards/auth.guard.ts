import {Injectable} from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateChild,
  Router,
  RouterStateSnapshot,
  UrlTree
} from '@angular/router';
import {catchError, map, Observable, of} from 'rxjs';
import {RootService} from "../services/root.service";
import {StorageService} from "../services/storage.service";
import {BaseResponse} from "../utils/BaseResponse";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router,
              private storage: StorageService,
              private rootService: RootService) {
  }
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.storage.getToken()) {
      if (this.rootService.currentUser) {
        return true;
      } else {
        return this.rootService.getCurrentUser().pipe(
          map(value => {
            const rs = value as BaseResponse
            if (rs.code == 200) {
              this.rootService.currentUser = rs.body;
              return true;
            }
            this.storage.signOut();
            this.router.navigate(['/login']);
            return false;
          }), catchError(error => {
            this.storage.signOut();
            this.router.navigate(['/login']);
            return of(false);
          }))
      }
    }
    this.router.navigate(["/login"])
    return false;
  }

}
