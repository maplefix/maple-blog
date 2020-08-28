package top.maplefix.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wangjg
 * @description 解决跨域问题过滤器
 * @date 2020/8/13 11:27
 */
//@Order(1)
//@WebFilter(filterName = "CORSFilter", urlPatterns = {"/*"})
public class CORSFilter implements Filter{

    private static final Logger log = LoggerFactory.getLogger(CORSFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("初始化CORSFilter ==========================");
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", "*"); //  这里最好明确的写允许的域名
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "Content-Type,Access-Token,Authorization,X-Frame-Options,access-control-allow-methods,access-control-allow-origin");
        filterChain.doFilter(servletRequest, servletResponse);
    }
    @Override
    public void destroy() {
        log.info("销毁CORSFilter==========================");
    }
}