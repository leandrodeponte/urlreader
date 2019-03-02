package com.ibm.urlreader.controller;

import com.ibm.urlreader.exception.ProcessUrlException;
import com.ibm.urlreader.model.Url;
import com.ibm.urlreader.service.UrlService;
import com.ibm.urlreader.validator.UrlValidator;
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

        log.debug("Service /api/url requested.");
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

        log.debug("Service /api/url/process requested. (" + url + ")");
        UrlValidator urlValidator = new UrlValidator();
        List<Url> urlList = new ArrayList<>();

        try {

            if(!urlValidator.isValid(url)){
                log.warn("Service /api/url/process requested for invalid url. (" + url + ")");
                return ResponseEntity
                        .status(HttpStatus.PRECONDITION_FAILED)
                        .body("URL is invalid. " + urlValidator.getErrors());
            }

            urlList = urlService.processUrl(url);

            if(urlList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        }catch(ProcessUrlException pue){
            log.error(pue);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar a URL."
                    );
        }catch(Exception e){
            log.error(e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado no processamento da URL."
                    );
        }

        return new ResponseEntity<List<Url>>(urlList, HttpStatus.OK);

    }

}
