package com.ibm.urlreader.service;

import com.ibm.urlreader.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UrlRepository extends JpaRepository<Url, Long>{

        Url findOneById(Long id);
        List<Url> findAll();
        Url save(Url url);

}
