package com.ibm.urlreader.service;

import com.ibm.urlreader.model.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UrlRepository extends MongoRepository<Url, Long> {

        Url findOneById(Long id);
        List<Url> findByText(String text);
        List<Url> findAll();
        Url save(Url url);

}
