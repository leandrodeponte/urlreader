package com.ibm.urlreader.controller;

import com.ibm.urlreader.model.Url;
import com.ibm.urlreader.service.UrlService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/api")
public class UrlController {

    static Logger log = Logger.getLogger(UrlController.class.getName());

    @Autowired
    UrlService urlService;

    @GetMapping("/url")
    public ResponseEntity get(){

        List<Url> urlList = new ArrayList<>();

        try {

            urlList = urlService.getAllUrls();

            if(urlList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        }catch(Exception e){
            log.error(e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar informações de URL."
                    );
        }

        return new ResponseEntity<List<Url>>(urlList, HttpStatus.OK);

    }

    @PostMapping("/url/process")
    public ResponseEntity get(@RequestBody String url){

        List<Url> urlList = new ArrayList<>();

        try {

            urlList = urlService.processUrl(url);

            if(urlList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        }catch(Exception e){
            log.error(e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao busprocessar informações da URL."
                    );
        }

        return new ResponseEntity<List<Url>>(urlList, HttpStatus.OK);

    }

}
