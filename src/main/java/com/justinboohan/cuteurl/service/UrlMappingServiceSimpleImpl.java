package com.justinboohan.cuteurl.service;

import com.justinboohan.cuteurl.model.UrlMapping;
import com.justinboohan.cuteurl.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlMappingServiceSimpleImpl implements UrlMappingService{

    @Autowired
    UrlMappingRepository urlMappingRepository;

    @Autowired
    UrlMappingCacheService urlMappingCacheServiceHashmapImpl;

    @Autowired
    UrlShorteningService urlShorteningServiceBaseSixtyFour;

    @Override
    public String getTargetUrl(String id)  {
        return urlMappingCacheServiceHashmapImpl.getTargetUrl(id);
    }

    @Override
    public UrlMapping storeMapping(UrlMapping urlMapping) throws IllegalStateException {
        String shortenedUrl = urlShorteningServiceBaseSixtyFour.createShortenedKey(urlMapping.getTargetUrl());
        if(urlMappingRepository.existsById(shortenedUrl)){
            throw new IllegalStateException("Url relationship already saved");
        }
        urlMapping.setId(shortenedUrl);
        return this.urlMappingRepository.save(urlMapping);
    }
}
