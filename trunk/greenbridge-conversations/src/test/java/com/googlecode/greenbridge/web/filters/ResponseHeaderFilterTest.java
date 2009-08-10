package com.googlecode.greenbridge.web.filters;

import java.io.IOException;

import javax.servlet.ServletException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class ResponseHeaderFilterTest {

    private ResponseHeaderFilter filter;

    @Before
    public void setUp() throws ServletException {
        filter = new ResponseHeaderFilter();
    }

    // -----------------------------------------------------------
    // Begin Tests
    // -----------------------------------------------------------
    /**
     * General header setting check.
     * @throws IOException
     * @throws ServletException
     */
    @Test
    public void testCacheHeaderSet() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterConfig filterConfig = new MockFilterConfig();
        filterConfig.addInitParameter("Cache-Control", "private,max-age=2419200");
        filter.init(filterConfig);
        MockFilterChain chain = new MockFilterChain();
        filter.doFilter(request, response, chain);
        String cacheControl = (String) response.getHeader("Cache-Control");
        System.out.println(cacheControl);
        Assert.assertEquals("Cache control should be set to 2419200", "private,max-age=2419200", cacheControl);
    }

    /**
     * Test our special field "ExpiresFromNow".
     * @throws IOException
     * @throws ServletException
     */
    @Test
    public void testExpiresFromNowHeaderSet() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterConfig filterConfig = new MockFilterConfig();
        filterConfig.addInitParameter("ExpiresFromNow", "31536000");
        filter.init(filterConfig);
        MockFilterChain chain = new MockFilterChain();
        filter.doFilter(request, response, chain);
        Long expires = (Long) response.getHeader("Expires");
        Long expected = System.currentTimeMillis() + 31536000000L;
        boolean pass = expires > expected - 1000 && expires < expected + 1000;
        Assert.assertTrue("Expires should be approx. " + expected, pass);
    }

    /**
     * Destroy the filter.
     * @throws IOException
     * @throws ServletException
     */
    @Test
    public void testDestroy() throws IOException, ServletException {
        MockFilterConfig filterConfig = new MockFilterConfig();
        filter.init(filterConfig);
        filter.destroy();
        Assert.assertTrue("Filter config should have been detroyed. ", filter.getFilterConfig() == null);
    }
}
