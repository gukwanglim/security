package com.cos.jwt.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyFilter3 implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		if(req.getMethod().equals("POST")) {
			
			System.out.println("post request");
			String headerAuth = req.getHeader("Authorization");
			System.out.println(headerAuth);
			System.out.println("filter3");
			
			if(headerAuth.equals("kwang")) {
				chain.doFilter(req, res);
			} else {
				PrintWriter out = res.getWriter();
				out.println("Not authenticated");
			}
		}	
	}
}
