package com.ibm.urlreader.validator;

import com.ibm.urlreader.util.UrlUtil;
import lombok.Getter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class UrlValidator {

    static Logger log = Logger.getLogger(UrlValidator.class.getName());

    @Getter
    List<String> errors = new ArrayList<String>();

    /**
     * Validates IF an URL com be processed by the API
     * @param urlStr
     * @return
     */
    public boolean isValid(String urlStr) {

        log.debug("Validating url = " + urlStr);

        errors = new ArrayList<String>();
        URL url = null;

        if(urlStr == null){
            errors.add("URL cannot be empty.");
            return false;
        }

        try{
            url = new URL(urlStr);
        }catch(MalformedURLException e){
            log.warn(e.getMessage());
            errors.add("URL is malformed -> " + e.getMessage());
            return false;
        }


        if(!UrlUtil.isValidProtocol(url)){
            errors.add("URL Protocol not allowed. Only HTTP or HTTPS");
            return false;
        }


        if(errors.size() > 0){
            return false;
        }

        log.debug("Valid URL. (" + urlStr + ")");

        return true;
    }

    /**
     *
     * @return true, if the validator found errors on the URL
     */
    public boolean hasErrors(){
        return errors.size() > 0;
    }

}
