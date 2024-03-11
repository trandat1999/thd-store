import {Injectable} from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from '@angular/common/http';
import {BehaviorSubject, catchError, Observable, switchMap, throwError} from 'rxjs';
import {StorageService} from "../services/storage.service";
import {Router} from "@angular/router";
import {AuthResponse} from "../pages/auth/auth.model";
import {ApplicationConfigService} from "../services/application-config.service";
import {BaseResponse} from "../utils/BaseResponse";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private isRefreshing = false;
  private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);

  constructor(
    private applicationConfigService: ApplicationConfigService,
    private http: HttpClient,
    private router: Router,
    private storageService: StorageService
  ) {
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.storageService.getLanguage()) {
      request = request.clone({
        setHeaders: {"Accept-language": this.storageService.getLanguage()}
      })
    }
    if (!request.url.includes('/api/v1/auth') && !request.url.includes('/api/v1/publish')) {
      const token = this.storageService.getToken();
      // if (!token || token.length <= 0) {
      //   this.router.navigate(['/login']);
      // }
      if(token && token.length>0){
        request = request.clone({
          setHeaders: {"Authorization": "Bearer "+ this.storageService.getToken()}
        })
      }
      return next.handle(request).pipe(catchError(error => {
        if (error instanceof HttpErrorResponse && error.status === 401) {
          return this.handle401Error(request, next);
        }
        return throwError(error);
      }));
    }
    return next.handle(request);
  }

  private handle401Error(request: HttpRequest<any>, next: HttpHandler) {
    // if (!this.isRefreshing) {
      this.isRefreshing = true;
      this.refreshTokenSubject.next(null);
      const token = this.storageService.getRefreshToken();
      if (token) {
        return this.http.get(this.applicationConfigService.apiBaseUrl + "/api/v1/auth/refresh-token/" +
          this.storageService.getRefreshToken()).pipe(
          switchMap((data: BaseResponse) => {
            this.isRefreshing = false;
            if (data.code == 200) {
              let authResponse = data.body as AuthResponse;
              this.storageService.saveToken(authResponse);
              request = request.clone({
                setHeaders: {"Authorization": "Bearer "+ this.storageService.getToken()}
              })
              return next.handle(request);
            }
            return next.handle(request);
          }),
          catchError((err) => {
            this.isRefreshing = false;
            this.storageService.signOut();
            this.router.navigate(['/login']);
            return next.handle(request);
          })
        )
      // }
    }
    // if (this.storageService.getRefreshToken()) {
    //   this.http.get(this.applicationConfigService.apiBaseUrl + "/api/v1/auth/refresh-token/" +
    //     this.storageService.getRefreshToken()).subscribe(data => {
    //     let rs = data as BaseResponse
    //     if (rs.code == 200) {
    //       let authResponse = rs.body as AuthResponse;
    //       this.storageService.saveToken(authResponse);
    //       request = request.clone({
    //         headers: request.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + authResponse.accessToken)
    //       });
    //       return next.handle(request);
    //     }
    //     this.storageService.signOut();
    //     this.router.navigate(['/login']);
    //     return next.handle(request);
    //   })
    // }
    this.router.navigate(['/login']);
    return next.handle(request);
  }
}

