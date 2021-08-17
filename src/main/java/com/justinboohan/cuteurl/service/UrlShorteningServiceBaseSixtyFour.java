package com.justinboohan.cuteurl.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class UrlShorteningServiceBaseSixtyFour implements UrlShorteningService {
    @Override
    public String createShortenedKey(String url) {
        return Base64.encodeBase64URLSafeString(url.getBytes(StandardCharsets.UTF_8));
    }
}
