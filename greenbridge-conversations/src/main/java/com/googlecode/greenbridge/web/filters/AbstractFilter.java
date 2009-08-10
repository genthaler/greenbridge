package com.googlecode.greenbridge.web.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * $Author$ <br>
 * $Date$ <br>
 * $Revision$ <br>
 * $Source$ <br>
 * $Name$ <br>
 * <br>
 *
 */
public abstract class AbstractFilter implements Filter {

    /**
     * The filter config.
     */
    private FilterConfig config;

    /**
     * Getter method for config.
     * 
     * @return config
     */
    protected final FilterConfig getConfig() {
        return config;
    }

    protected abstract void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException;

    /** {@inheritDoc} */
    public void init(FilterConfig config) throws ServletException {
        this.config = config;
    }

    /** {@inheritDoc} */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    /** {@inheritDoc} */
    public void destroy() {
    }
}
