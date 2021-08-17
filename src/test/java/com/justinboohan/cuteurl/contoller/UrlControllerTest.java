package com.justinboohan.cuteurl.contoller;

import com.justinboohan.cuteurl.controller.UrlController;
import com.justinboohan.cuteurl.model.UrlMapping;
import com.justinboohan.cuteurl.service.UrlMappingServiceSimpleImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UrlController.class)
@RunWith(SpringRunner.class)
public class UrlControllerTest {

    @Autowired
    UrlController urlController;

    @MockBean
    UrlMappingServiceSimpleImpl mockUrlMappingService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testCreateMapping() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/url-mappings")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"targetUrl\":\"target\",\"ownerId\":\"googleId\"}");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(HttpStatus.CREATED.value()));
    }

    @Test
    public void testInvalidCreateMappingRequest() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/url-mappings")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"invalidValue\":\"target\",\"ownId\":\"googleId\"}");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testCreateMappingAlreadyExistsRequest() throws Exception{
        Mockito.when(mockUrlMappingService.storeMapping(Mockito.any(UrlMapping.class))).thenThrow(IllegalStateException.class);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/url-mappings")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"targetUrl\":\"target\",\"ownId\":\"googleId\"}");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()));
    }

    @Test
    public void testGetShortenedKey() throws Exception{

        Mockito.when(mockUrlMappingService.getTargetUrl("1234")).thenReturn("http://youtube.com");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/1234")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(HttpStatus.TEMPORARY_REDIRECT.value()))
                .andExpect(header().stringValues("Location", "http://youtube.com"));
    }

    @Test
    public void testGetShortenedKeyNotFound() throws Exception{

        Mockito.when(mockUrlMappingService.getTargetUrl("1234")).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/1234")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }


}
