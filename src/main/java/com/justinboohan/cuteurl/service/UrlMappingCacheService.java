package com.justinboohan.cuteurl.service;

import com.justinboohan.cuteurl.model.UrlMapping;

public interface UrlMappingCacheService {

    void put(UrlMapping urlMapping) throws IllegalStateException;

    String getTargetUrl(String id) throws IllegalStateException;
}
