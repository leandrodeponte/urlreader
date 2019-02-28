package com.ibm.urlreader.util;

import com.ibm.urlreader.model.Url;
import com.ibm.urlreader.service.UrlService;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UrlUtil {

    private static final org.apache.log4j.Logger logger
            = org.apache.log4j.Logger.getLogger(UrlUtil.class);

    public static List<Url> identificarURL(String pageText){

        Pattern urlPattern = Pattern.compile(
                "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                        + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                        + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);


        List<Url> urlList = new ArrayList<>();
        //TODO buscar a lista das URLs dentro das URLs - Recursivo
        Matcher matcher = urlPattern.matcher(pageText);
        while (matcher.find()) {
            int matchStart = matcher.start(1);
            int matchEnd = matcher.end();
            urlList.add(new Url(pageText.substring(matchStart, matchEnd)));

        }

        return urlList;

    }

    public static String readFromUrl(String urlAdd){

        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;
        StringBuilder sb;

        try{
            url = new URL(urlAdd);
            is = url.openStream();
            br = new BufferedReader(new InputStreamReader(is));
            sb = new StringBuilder();

            while ((line = br.readLine()) != null){
                sb.append(line);
            }

            return sb.toString();

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(is != null){
                    is.close();
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }

        return null;

    }
}
