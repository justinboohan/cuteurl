package com.justinboohan.cuteurl.service;

import com.justinboohan.cuteurl.model.UrlMapping;
import com.justinboohan.cuteurl.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Service
public class UrlMappingCacheServiceHashMapImpl implements UrlMappingCacheService {

    private HashMap<String, String> urlMappingCache;

    @Autowired
    UrlMappingRepository urlMappingRepository;

    @PostConstruct
    public void init(){
        this.urlMappingCache = new HashMap<>();
        for(UrlMapping urlMapping: urlMappingRepository.findAll()){
            this.urlMappingCache.put(urlMapping.getId(), urlMapping.getTargetUrl());
        }
    }

    @Override
    public void put(UrlMapping urlMapping) throws IllegalStateException {
        this.urlMappingCache.put(urlMapping.getId(), urlMapping.getTargetUrl());
    }

    @Override
    public String getTargetUrl(String id) {
        return this.urlMappingCache.get(id);
    }
}
