package com.justinboohan.cuteurl.service;

import com.justinboohan.cuteurl.model.UrlMapping;

public interface UrlMappingService {

    String getTargetUrl(String id);

    UrlMapping storeMapping(UrlMapping urlMapping);
}
