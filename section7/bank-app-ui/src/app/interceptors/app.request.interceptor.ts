import { Injectable } from '@angular/core';
import { HttpInterceptor,HttpRequest,HttpHandler,HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import {Router} from '@angular/router';
import {tap} from 'rxjs/operators';
import { User } from 'src/app/model/user.model';

@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  user = new User();
  constructor(private router: Router) {}

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    let httpHeaders = new HttpHeaders();
    if(sessionStorage.getItem('userdetails')){
      this.user = JSON.parse(sessionStorage.getItem('userdetails')!);
    }
    if(this.user && this.user.pwd && this.user.email){
      /**
       * Previously, we have used the login page of Spring Security and it has attributes like username 
       * and password and it is reading with the help of UsernamePasswordAuthenticationFilter.
       * 
       * But in this scenario, we are sending the credentials of my end user from my interceptor class
        inside the request with an header, Authorization. And inside this Authorization header, we are 
        passing the credentials in the form of basic authentication.
        So whenever we are using HTTP Basic Authentication, there is one more filter inside the Spring 
        Security which will come into picture and this filter is BasicAuthenticationFilter.doFilterInternal
       */
      httpHeaders = httpHeaders.append('Authorization', 'Basic ' + window.btoa(this.user.email + ':' + this.user.pwd));
    }

    let xsrf = sessionStorage.getItem('XSRF-TOKEN');
    if(xsrf) {
      httpHeaders = httpHeaders.append('X-XSRF-TOKEN', xsrf); //CookieCsrfTokenRepository.DEFAULT_CSRF_HEADER_NAME
    }

    httpHeaders = httpHeaders.append('X-Requested-With', 'XMLHttpRequest');
    console.log(httpHeaders.getAll('Authorization'));
    console.log(httpHeaders.getAll('X-XSRF-TOKEN'));
    const xhr = req.clone({
      headers: httpHeaders
    });
  return next.handle(xhr).pipe(tap(
      (err: any) => {
        if (err instanceof HttpErrorResponse) {
          if (err.status !== 401) {
            return;
          }
          this.router.navigate(['dashboard']);
        }
      }));
  }
}