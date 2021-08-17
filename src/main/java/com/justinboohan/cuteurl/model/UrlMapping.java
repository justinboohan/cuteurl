package com.justinboohan.cuteurl.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="URL_MAPPING")
public class UrlMapping {

    private @Id
    String id;

    @NotEmpty
    private String targetUrl;

    private String ownerId;
}
