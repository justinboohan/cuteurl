package com.justinboohan.cuteurl.repository;

import com.justinboohan.cuteurl.model.UrlMapping;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UrlMappingRepository extends CrudRepository<UrlMapping, String> {
}
