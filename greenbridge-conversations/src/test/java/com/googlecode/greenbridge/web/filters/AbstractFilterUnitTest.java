package com.googlecode.greenbridge.web.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.*;

public class AbstractFilterUnitTest {

    private TestableAbstractFilter abstractFilter;

    @Before
    public void setUp() {
        abstractFilter = new TestableAbstractFilter();
    }

    @Test
    public void initShouldPopulateConfig() throws Exception {
        final MockFilterConfig filterConfig = new MockFilterConfig();
        abstractFilter.init(filterConfig);
        assertTrue(filterConfig == abstractFilter.getConfig());
    }

    @Test
    public void doFilterShouldDelegateToTemplate() throws Exception {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final MockFilterChain chain = new MockFilterChain();
        abstractFilter.doFilter((ServletRequest) request, (ServletResponse) response, chain);
        assertTrue(request == abstractFilter.inputRequest);
        assertTrue(response == abstractFilter.inputRespone);
        assertTrue(chain == abstractFilter.inputChain);
    }

    @Test
    public void destroyShouldDoNothing() {
        abstractFilter.destroy();
    }

    private static class TestableAbstractFilter extends AbstractFilter {

        private HttpServletRequest inputRequest;

        private HttpServletResponse inputRespone;

        private FilterChain inputChain;

        public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
            inputRequest = request;
            inputRespone = response;
            inputChain = chain;
        }
    }
}
