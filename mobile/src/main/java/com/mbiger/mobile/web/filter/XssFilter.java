package com.mbiger.mobile.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * XSS攻击解决方案之一（过滤器）
 */
public class XssFilter implements Filter {
    FilterConfig filterConfig = null;

    private List<String> urlExclusion = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String servletPath = httpServletRequest.getServletPath();

        if (urlExclusion != null && urlExclusion.contains(servletPath)) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
        }
    }

    public List<String> getUrlExclusion() {
        return urlExclusion;
    }

    public void setUrlExclusion(List<String> urlExclusion) {
        this.urlExclusion = urlExclusion;
    }

}
