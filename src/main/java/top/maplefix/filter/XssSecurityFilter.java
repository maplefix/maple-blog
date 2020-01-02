/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package top.maplefix.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : Maple
 * @description : xss过滤器(OncePerRequestFilter)
 * @date : Created in 2019/9/16 23:25
 * @version : v2.1
 */
@Component
@Slf4j
public class XssSecurityFilter extends OncePerRequestFilter {

    private static final String[] excludePath = {"/api/admin/blog/save","/api/admin/blog/update"};

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //过滤掉editor.md文本内容(其包含特殊的html标签)
        String uri = request.getRequestURI();
        if(excludePath(excludePath,uri)){
            filterChain.doFilter(request, response);
            return;
        }
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(request);
        filterChain.doFilter(xssRequest, response);
    }

    /**
     * 排除路径过滤.
     *
     * @param path the excludePaths
     * @param path the path
     * @return the boolean
     */
    public boolean excludePath(String[] excludePaths, String path) {
        for (int i = 0; i < excludePaths.length; i++) {
            if (path.equals(excludePaths[i])){
                log.info("放行白名单路径");
                return true;
            }
        }
        return false;
    }
}