package com.msa;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public class CommonUtils {

    public static HttpEntity<?> apiClientHttpEntity(String appType, String params) {
         
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.set("Content-Type", appType);
         
        if (params == null || "".equals(params)) {
            return new HttpEntity<Object>(reqHeaders);
        } else {
            return new HttpEntity<Object>(params, reqHeaders);
        }
    }

}