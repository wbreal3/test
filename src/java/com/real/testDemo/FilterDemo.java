package com.real.testDemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "TestFilter",urlPatterns = "/*.jsp")
public class FilterDemo implements Filter {
    private static final Logger log = LoggerFactory.getLogger(FilterDemo.class);
    public void init(FilterConfig config) throws ServletException {
        String filterName = config.getFilterName();
        String charset = config.getInitParameter("charset");

        System.out.println("过滤器名称：" + filterName);
        System.out.println("字符集编码：" + charset);
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        //拦截进入首页的请求
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        StringBuffer url = req.getRequestURL();
        int last = url.lastIndexOf("/");
        String sub = url.substring(last + 1);
        if (!sub.equals("login.html")){
            log.info("========进行拦截============");
            resp.sendRedirect("login.html");
        }else {
            chain.doFilter(request,response);
        }
    }
}
