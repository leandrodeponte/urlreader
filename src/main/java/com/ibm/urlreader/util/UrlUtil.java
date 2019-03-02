package com.ibm.urlreader.util;

import com.ibm.urlreader.Enum.ProtocolEnum;
import com.ibm.urlreader.model.Url;
import com.ibm.urlreader.service.UrlService;
import com.ibm.urlreader.validator.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UrlUtil {

    private static final org.apache.log4j.Logger logger
            = org.apache.log4j.Logger.getLogger(UrlUtil.class);

    private static final UrlValidator urlValidator = new UrlValidator();

    /**
     * Searches for URLs inside a Text
     * @param pageText The
     * @return
     */
    public static List<Url> identifyURL( String pageText){

        Pattern urlPattern = Pattern.compile(
                "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                        + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                        + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);


        List<Url> urlList = new ArrayList<>();
        Matcher matcher = urlPattern.matcher(pageText);
        while (matcher.find()) {
            int matchStart = matcher.start(1);
            int matchEnd = matcher.end();
            String urlFound = pageText.substring(matchStart, matchEnd);
            if(urlValidator.isValid(urlFound)){
                urlList.add(new Url(urlFound));
            }

        }

        return urlList;

    }

    public static String readFromUrl(String urlAdd) throws UnknownHostException, IOException{

        URL url;
        BufferedReader br;
        String line;
        StringBuilder sb;

        br = UrlUtil.openUrl(urlAdd);
        sb = new StringBuilder();

        while ((line = br.readLine()) != null){
            sb.append(line);
        }

        return sb.toString();

    }

    public static BufferedReader openUrl(String urlAdd) throws IOException {

        URL url = new URL(urlAdd);
        InputStream is = url.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        return br;

    }

    public static boolean isValidProtocol(URL url){
        String protocol = url.getProtocol();
        boolean validProtocol  = false;

        for(ProtocolEnum prt : ProtocolEnum.values())
        {
            if(prt.getProtocol().equalsIgnoreCase(protocol)){
                return true;
            }
        }

        return false;

    }
}
