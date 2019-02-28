package com.ibm.urlreader.util;

import com.ibm.urlreader.model.Url;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlUtilTest {

    private final static String URL = "http://www.ibm.com";
    private final static String URL_HTTP = "<html> <a http://www.ibm.com> Link IBM </a> </html>";
    private final static String URL_HTTP_2LINKS = "<html> <a http://www.ibm.com> Link IBM </a> <a http://www.github.com> Link IBM </a> </html>";
    private final static String URL_HTTPS = "<html> <a https://www.ibm.com> Link IBM </a>  </html>";
    private final static String URL_FTP = "<html> <a ftp://www.ibm.com> Link IBM </a>  </html>";

    @Test
    public void identificaUrl_TestOK() throws Exception {

        List<Url> urlList = UrlUtil.identificarURL(URL_HTTP);
        Assert.assertEquals(urlList.size(),1);
        Assert.assertEquals(urlList.get(0).getText(), "http://www.ibm.com");

    }

    @Test
    public void identificarUrl_2Links_TestOK() throws Exception {

        List<Url> urlList = UrlUtil.identificarURL(URL_HTTP_2LINKS);
        Assert.assertEquals(urlList.size(),2);
        Assert.assertEquals(urlList.get(0).getText(), "http://www.ibm.com");
        Assert.assertEquals(urlList.get(1).getText(), "http://www.github.com");

    }

    @Test
    public void identificarUrl_https_TestOK() throws Exception {

        List<Url> urlList = UrlUtil.identificarURL(URL_HTTPS);
        Assert.assertEquals(urlList.size(),1);
        Assert.assertEquals(urlList.get(0).getText(), "https://www.ibm.com");

    }


    @Test
    public void identificarUrl_ftp_TestOK() throws Exception {

        List<Url> urlList = UrlUtil.identificarURL(URL_FTP);
        Assert.assertEquals(urlList.size(),1);
        Assert.assertEquals(urlList.get(0).getText(), "ftp://www.ibm.com");

    }

    @Test
    public void readFromUrl_TestOK() throws IOException {

       String url = UrlUtil.readFromUrl(URL);
       Assert.assertNotNull(url);

    }



}
