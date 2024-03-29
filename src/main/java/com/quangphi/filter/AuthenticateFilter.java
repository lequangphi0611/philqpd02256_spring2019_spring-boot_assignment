package com.quangphi.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.quangphi.model.AccountDTO;

@Component
@Order(1)
public class AuthenticateFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        AccountDTO account = (AccountDTO) session.getAttribute("account");
        if (account == null) {
            req.getRequestDispatcher("/authenticate/login").forward(request, response);
        }
        chain.doFilter(request, response);
    }

}