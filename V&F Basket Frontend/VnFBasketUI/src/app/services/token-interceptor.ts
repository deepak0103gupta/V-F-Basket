import { HttpInterceptorFn } from '@angular/common/http';
import { Injectable } from '@angular/core';


export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  const token = sessionStorage.getItem('token');
  if(token){
    const modifiedReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    return next(modifiedReq);
  }
  return next(req);
};
