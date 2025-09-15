// Package declaration
package com.ecommerce.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ecommerce.model.User;

// This WebFilter will intercept all requests to URLs starting with /admin/
@WebFilter("/admin/*")
public class AdminAuthFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        // Cast the request and response objects to their HTTP versions
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Get the current session, don't create a new one if it doesn't exist
        HttpSession session = httpRequest.getSession(false);
        
        // Check if a user is logged in and if they are an ADMIN
        boolean isAdmin = false;
        if (session != null) {
            User user = (User) session.getAttribute("user");
            // Check if user exists and their role is "ADMIN"
            if (user != null && "ADMIN".equals(user.getRole())) {
                isAdmin = true;
            }
        }

        if (isAdmin) {
            // If the user is an admin, allow the request to proceed to the destination
            chain.doFilter(request, response);
        } else {
            // If the user is not an admin, redirect them to the login page
            // You can also add a message to inform them why they were redirected
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp?error=access_denied");
        }
    }
    
    // Other filter methods (init, destroy) can be left empty for this example
    public void init(FilterConfig fConfig) throws ServletException {}
    public void destroy() {}
}