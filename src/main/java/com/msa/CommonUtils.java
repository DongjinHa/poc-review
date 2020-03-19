package com.msa;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

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
    
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
    	Map<Object, Boolean> map = new ConcurrentHashMap<>();
    	return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}