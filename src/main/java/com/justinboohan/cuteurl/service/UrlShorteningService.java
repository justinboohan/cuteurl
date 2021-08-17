package com.justinboohan.cuteurl.service;

import org.springframework.stereotype.Service;

@Service
public interface UrlShorteningService {

    public String createShortenedKey(String url);
}
