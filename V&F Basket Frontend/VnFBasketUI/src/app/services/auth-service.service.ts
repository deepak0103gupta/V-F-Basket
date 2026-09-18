import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class AuthService {
     private token: string | null = null;
  
   private isLoggedInSubject = new BehaviorSubject<boolean>(this.hasToken());
     // Observable that components will subscribe to
   isLoggedIn$ = this.isLoggedInSubject.asObservable();

   private roleSubject = new BehaviorSubject<string | null>(sessionStorage.getItem('role'));
   role$ = this.roleSubject.asObservable();

   constructor(private http: HttpClient){}

   private hasToken(): boolean {
    return !!sessionStorage.getItem('token');
   }

     private apiUrl: string = 'http://localhost:8080/vnfbasket';

     login(details: any): Observable<any> {
      // const headers = { 'Content-Type': 'application/json' };
        return this.http.post(this.apiUrl+ '/user/login', details);
        this.isLoggedInSubject.next(true);
     }

     register(details: any): Observable<any> {
        return this.http.post(this.apiUrl + '/register', details);
     }

     saveToken(token: string): void {
        this.token = token;
        this.isLoggedInSubject.next(true);

        // Optionally, save the token to sessionStorage or cookies for persistence
        sessionStorage.setItem('token', token);
     }

     setRole(role: any){
        sessionStorage.setItem('role', role);
        this.roleSubject.next(role);
     }

     get Role(){
        return sessionStorage.getItem('role');
     }

     setUserId(userId: any){
        sessionStorage.setItem('userId', userId);
     }

     get UserId(){
        return sessionStorage.getItem('userId');
     }

     setEmail(email: any){
        sessionStorage.setItem('email', email);
     }

     get Email(){
        return sessionStorage.getItem('email');
     }

     get LoginStatus(): boolean {
        return !!sessionStorage.getItem('token');
     }

     get Token(): string | null {
        this.token = sessionStorage.getItem('token');
        return this.token;
     }

     logout(){
        sessionStorage.removeItem('token');
        sessionStorage.removeItem('role');
        sessionStorage.removeItem('userId');
        sessionStorage.removeItem('email');
        this.token = null;
        this.isLoggedInSubject.next(false);
        this.roleSubject.next(null);
     }

     checkEmailExists(email: string): Observable<boolean> {
        return this.http.get<boolean>(this.apiUrl + '/checkUserExists?username=' + email);
     }
}
    
