package com.sbt.school.servlets.web;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BrowserServletTest extends Mockito {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private BrowserServlet servlet;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        servlet = new BrowserServlet();
    }

    @Test
    public void whenParamsCountGreaterThanOneThenError() throws IOException, ServletException {

        Map<String, String[]> params = new HashMap<>();
        params.put("url", new String[]{"https://www.google.com"});
        params.put("another_param", new String[]{"https://www.google.com"});

        when(request.getParameterMap()).thenReturn(params);

        try (StringWriter sw = new StringWriter();
             PrintWriter pw = new PrintWriter(sw)) {


            when(response.getWriter()).thenReturn(pw);

            servlet.doGet(request, response);
            String result = sw.getBuffer().toString().trim();
            assertEquals(result, new String("Request should contain only url"));
        }

    }

    @Test
    public void whenParamUrlNullThenErrorMsg() throws Exception {

        try (StringWriter sw = new StringWriter();
             PrintWriter pw = new PrintWriter(sw)) {

            when(response.getWriter()).thenReturn(pw);

            servlet.doGet(request, response);
            String result = sw.getBuffer().toString().trim();
            assertEquals(result, "Your request should contain 'url' key");
        }
    }

    @Test
    public void whenParamUrlNotOnlyThenErrorMsg() throws Exception {

        Map<String, String[]> params = new HashMap<>();
        params.put("url", new String[]{"https://www.google.com", "https://www.yandex.ru"});

        when(request.getParameterMap()).thenReturn(params);

        try (StringWriter sw = new StringWriter();
             PrintWriter pw = new PrintWriter(sw)) {

            when(response.getWriter()).thenReturn(pw);

            servlet.doGet(request, response);
            String result = sw.getBuffer().toString().trim();
            assertEquals(result, "Your request should contain the only 'url' key");
        }
    }

    @Test
    public void whenParamUrlWrongFormatThenErrorMsg() throws Exception {

        Map<String, String[]> params = new HashMap<>();
        params.put("url", new String[]{"https://ww..google.c()m"});

        when(request.getParameterMap()).thenReturn(params);

        try (StringWriter sw = new StringWriter();
             PrintWriter pw = new PrintWriter(sw)) {

            when(response.getWriter()).thenReturn(pw);

            servlet.doGet(request, response);
            String result = sw.getBuffer().toString().trim();
            assertEquals(result, "Wrong url format. URL mask is like \"https://www.[resource].[domain]\"");

        }
    }

    @Test
    public void whenTheOnlyParamUrlRightFormatThenSuccess() throws Exception {

        String url = "https://www.google.com";

        Map<String, String[]> params = new HashMap<>();
        params.put("url", new String[]{url});

        when(request.getParameterMap()).thenReturn(params);

        try (StringWriter sw = new StringWriter();
             PrintWriter pw = new PrintWriter(sw)) {

            when(response.getWriter()).thenReturn(pw);

            servlet.doGet(request, response);
            String result = sw.getBuffer().toString().trim();
            assertTrue(result.contains("Response for URL: " + url));
        }

        //
    }

}
