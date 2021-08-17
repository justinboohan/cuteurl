package com.justinboohan.cuteurl.controller;

import com.justinboohan.cuteurl.model.UrlMapping;
import com.justinboohan.cuteurl.service.UrlMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class UrlController {

    @Autowired
    UrlMappingService urlMappingServiceSimpleImpl;

    @GetMapping("/{id}")
    public void getShortenedUrl(HttpServletResponse httpServletResponse, @PathVariable String id){
        String targetUrl = urlMappingServiceSimpleImpl.getTargetUrl(id);

        if(targetUrl == null){
            httpServletResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }else{
            httpServletResponse.setHeader("Location", urlMappingServiceSimpleImpl.getTargetUrl(id));
            httpServletResponse.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
        }

    }

    @PostMapping("/url-mappings")
    public UrlMapping createShortenedUrl(HttpServletResponse httpServletResponse, @RequestBody @Validated UrlMapping urlMapping){

        try{
            return urlMappingServiceSimpleImpl.storeMapping(urlMapping);
        }catch(IllegalStateException ex){
            httpServletResponse.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        }
        return urlMapping;
    }
}
