package com.ibm.urlreader.service;

import com.ibm.urlreader.controller.UrlController;
import com.ibm.urlreader.model.Url;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;


//@RunWith(SpringRunner.class)
//@SpringBootTest
public class UrlServiceTest {

    MockMvc mockMvc;

    @Autowired
    UrlController messageController;

    @Autowired
    private UrlService urlService;

    private List<Url> messageList = new ArrayList<Url>();

    //@Before
    public void setup() throws Exception {

        this.mockMvc = standaloneSetup(this.messageController).build();// Standalone context

        Url message = new Url();
        message.setText("https://www.bim.com");
        messageList.add(message);

    }

}
