package com.cs739.app.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs739.app.util.AppConstants;
import com.cs739.app.util.AppConstants.MasterPages;

/**
 * Every request passes through this filter.
 * Right now, it's job is to make sure that enough {@link Replicant}
 * machines have contacted the master before requests can pass through.
 * @author MDee
 * 
 */
public class PlopboxMasterFilter implements Filter {

    protected FilterConfig config;
    private ServletContext context;
    
    private static final Logger log = LoggerFactory
            .getLogger(PlopboxMasterFilter.class);
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
        context = config.getServletContext();
    }
    
    private boolean requestIsAddingReplicant(HttpServletRequest request) {
        return request.getRequestURI().indexOf(AppConstants.ADD_REPLICANT_URI) != -1;
    }
    
    private boolean minimumReplicantsOnline() {
        return ((Integer) context.getAttribute(AppConstants.NUM_REPLICANTS)) > 0;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (requestIsAddingReplicant(req) || minimumReplicantsOnline()) {
            // It's all good!
            chain.doFilter(request, response);
        } else {
            log.info("Not enough replicants!");
            RequestDispatcher dispatcher = req.getRequestDispatcher(MasterPages.UNINITIALIZED.toString());
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

}
