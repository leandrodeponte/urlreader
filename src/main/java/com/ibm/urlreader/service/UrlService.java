package com.ibm.urlreader.service;

import com.ibm.urlreader.model.Url;
import com.ibm.urlreader.util.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UrlService {

    private static final org.apache.log4j.Logger logger
            = org.apache.log4j.Logger.getLogger(UrlService.class);

    @Autowired
    UrlRepository urlRepository;

    public List<Url> getAllUrls(){
        return urlRepository.findAll();
    }

    public List<Url> processUrl(String url){

        List<Url> urlList = this.urlparser(url);

        persistUrls(urlList);

        for(Url urlItem : urlList){
            List<Url> urlListInner = urlparser(urlItem.getText());
            persistUrls(urlListInner);
        }

        return urlList;

    }

    private List<Url> urlparser(String url){

        List<Url> urlList = new ArrayList<>();

        try {

            URLConnection connection = new URL(url).openConnection();

            connection.setConnectTimeout(1000);
            //String out = new Scanner(new URL(url).openStream(), "UTF-8").useDelimiter("\\A").next();
            String out = UrlUtil.readFromUrl(url);

            urlList = UrlUtil.identificarURL(out);

        } catch (MalformedURLException e) {
            logger.error("A URL "+url+" não pode ser processada. Motivo: "+e.getMessage());
        } catch (IOException e) {
            logger.error("A URL "+url+" não pode ser processada. Motivo: "+e.getMessage());
        }

        return urlList;
    }

    private void persistUrls(List<Url> urlList){
        for(Url url : urlList){
            urlRepository.save(url);
        }
    }

}
