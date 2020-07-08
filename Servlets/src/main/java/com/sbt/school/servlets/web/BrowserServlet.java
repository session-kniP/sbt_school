package com.sbt.school.servlets.web;

import com.sbt.school.servlets.cache.HashMapCache;
import com.sbt.school.servlets.content.Content;
import com.sbt.school.servlets.content.ContentBuilder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@WebServlet(urlPatterns = {"/call"})
public class BrowserServlet extends HttpServlet {

    private HashMapCache cache;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setMaxInactiveInterval(300);
        super.service(req, resp);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> parameterMap = req.getParameterMap();
        if (parameterMap.size() > 1) {
            resp.getWriter().println("Request should contain only url");
            return;
        }

        String[] requestArray = parameterMap.get("url");

        if (requestArray == null) {
            resp.getWriter().println("Your request should contain 'url' key");
            return;
        }

        if (requestArray.length > 1) {
            resp.getWriter().println("Your request should contain the only 'url' key");
            return;
        }

        try {
            URL url = new URL(requestArray[0]);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int status = connection.getResponseCode();

            if (status == 200) {
                if (cache == null) {
                    this.cache = new HashMapCache();
                }

                if (!cache.contains(url)) {

                    Content content = new Content(
                            status,
                            connection.getHeaderFields(),
                            ContentBuilder.build(
                                    new BufferedReader(
                                            new InputStreamReader(connection.getInputStream())
                                    )
                            )
                    );

                    cache.save(url, content);

                }
                resp.getWriter().println("Response for URL: " + url + "\n");
                resp.getWriter().println(cache.get(url));
            }
        } catch (IOException e) {
            resp.getWriter().flush();
            resp.getWriter().println("Wrong url format. URL mask is like \"https://www.[resource].[domain]\"");
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

}
