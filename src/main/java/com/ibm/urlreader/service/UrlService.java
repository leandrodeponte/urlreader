package com.ibm.urlreader.service;

import com.ibm.urlreader.exception.ProcessUrlException;
import com.ibm.urlreader.model.Url;
import com.ibm.urlreader.util.UrlUtil;
import com.ibm.urlreader.validator.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UrlService {

    private static final org.apache.log4j.Logger log
            = org.apache.log4j.Logger.getLogger(UrlService.class);

    @Autowired
    UrlRepository urlRepository;

    @Autowired
    UrlValidator urlValidator;

    @Value("${ibm.urlreader.recursivity-level}")
    private int recursivityLevel;

    public List<Url> getAllUrls(){
        return urlRepository.findAll();
    }

    /***
     * Processes the URL.
     *  1. Verifies the recursivity level
     *  2. Persits the URL being processed
     *  3. Lists the Sub URLs (URLs inside the Main URL text)
     *  4. Calls the method recursively to process each Sub URL
     * @param url the URL to be processed
     * @return List with the processed URLs
     * @throws ProcessUrlException
     */
    public List<Url> processUrl(String url)
            throws ProcessUrlException {
        return processUrl(url, new ArrayList<>(), 0);
    }

    /***
     * Processes the URL.
     *  1. Verifies the recursivity level
     *  2. Persits the URL being processed
     *  3. Lists the Sub URLs (URLs inside the Main URL text)
     *  4. Calls the method recursively to process each Sub URL
     * @param url the URL to be processed
     * @param urlPersistedList list fo the already processed URLs
     * @param level Current Recursivity level
     * @return List with the processed URLs
     * @throws ProcessUrlException
     */
    public List<Url> processUrl(String url, List<Url> urlPersistedList, Integer level)
            throws ProcessUrlException {

        List<Url> urlList = new ArrayList<>();
        Url urlMain = new Url(url);

        //This block verifies the configured recursivity level
        level++;
        if(level > recursivityLevel){
            log.debug("Stopping recursivty");
            return new ArrayList<>();
        }

        log.info("Processing url. (" + url + ")");

        urlPersistedList.add(persistUrl(urlMain));
        urlList = this.urlparser(urlMain);

        //For each URL on the page text, verifies if its already processed
        //IF not, processes the URL
        log.info("Processing suburls from " + url);
        for(Url urlItem : urlList){

            //Before processing the Sub URl, verifies if its already processed previously
            boolean altreadyExists = urlPersistedList
                    .stream()
                    .anyMatch(item -> Objects.equals(item.getText(), urlItem.getText()));

            if(!altreadyExists){
                processUrl(urlItem.getText()
                        , urlPersistedList
                        , level);
            }
        }

        return urlPersistedList;

    }

    /**
     * Connects to the URL and gets the text. Searches for URLs in the text
     * @param url The URL to be ṕarsed
     * @return List fo the located URLs
     */
    private List<Url> urlparser(Url url)  {

        List<Url> urlList = new ArrayList<>();

        try {

            URLConnection connection = new URL(url.getText()).openConnection();

            connection.setConnectTimeout(100);
            String out = UrlUtil.readFromUrl(url.getText());
            urlList = UrlUtil.identifyURL( out);

        } catch (MalformedURLException e) {
            log.error("A URL "+url+" não pode ser processada ->"+e.getMessage());

        } catch (IOException e) {
            log.error("Não foi possível conectar na URL -> "+e.getMessage());
        }

        return urlList;

    }

    private Url persistUrl(Url url){
        return urlRepository.save(url);
    }

    private void persistUrls(List<Url> urlList){
        for(Url url : urlList){
            urlRepository.save(url);
        }
    }

}
