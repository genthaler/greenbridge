////////////////////////////////////////////////////////////////////////////////
//
// Copyright (c) 2007, Suncorp Metway Limited. All rights reserved.
//
// This is unpublished proprietary source code of Suncorp Metway Limited.
// The copyright notice above does not evidence any actual or intended
// publication of such source code.
//
////////////////////////////////////////////////////////////////////////////////

package com.googlecode.greenbridge.web.filters;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * $Author$ <br>
 * $Date$ <br>
 * $Revision$ <br>
 * $Source$ <br>
 * $Name$ <br>
 * <br>
 * Main Author: Simon Pink <br>
 * <br>
 * Copyright 2009 Vero Insurance New Zealand LTD. All rights reserved.<br>
 * <br>
 * See: http://www.onjava.com/pub/a/onjava/2004/03/03/filters.html
 */
public class ResponseHeaderFilter extends AbstractFilter {

    private FilterConfig filterConfig;

    private Logger log = Logger.getLogger(ResponseHeaderFilter.class);

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // set the provided HTTP response parameters
        log.debug("request: " + request.getRequestURI());

        Enumeration e = filterConfig.getInitParameterNames();
        while (e.hasMoreElements()) {
            String headerName = (String) e.nextElement();
            // Special field that allows relative setting of expires header
            if ("ExpiresFromNow".equalsIgnoreCase(headerName)) {
                long today = new Date().getTime();
                long fromNow = Long.parseLong(filterConfig.getInitParameter(headerName)) * 1000;
                log.debug("Expires: " + new Date(today + fromNow));
                response.addDateHeader("Expires", today + fromNow);
            } else {
                log.debug(headerName + ": " + filterConfig.getInitParameter(headerName));
                response.addHeader(headerName, filterConfig.getInitParameter(headerName));
            }
        }
        // pass the request/response on
        chain.doFilter(request, response);
    }

    /** {@inheritDoc} */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /** {@inheritDoc} */
    public void destroy() {
        this.filterConfig = null;
    }

    /**
     * Get the filterConfig of the ResponseHeaderFilter object.
     * 
     * @return Returns the filterConfig.
     */
    public FilterConfig getFilterConfig() {
        return filterConfig;
    }
}
