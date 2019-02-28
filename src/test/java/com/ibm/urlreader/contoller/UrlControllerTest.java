package com.ibm.urlreader.contoller;

import com.ibm.urlreader.controller.UrlController;
import com.ibm.urlreader.model.Url;
import com.ibm.urlreader.service.UrlService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


//@RunWith(SpringRunner.class)
//@SpringBootTest
public class UrlControllerTest {

    MockMvc mockMvc;

    @Autowired
    UrlController messageController;

    @MockBean
    private UrlService urlService;

    private List<Url> urlList = new ArrayList<Url>();

    //@Before
    public void setup() throws Exception {

        this.mockMvc = standaloneSetup(this.messageController).build();// Standalone context

        Url url = new Url();
        url.setText("https://www.ibm.com");
        urlList.add(url);

    }

    //@Test
    public void getMessage_TestOK() throws Exception {

        when(urlService.getAllUrls()).thenReturn(urlList);

        mockMvc.perform(get("/api/url/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

}
