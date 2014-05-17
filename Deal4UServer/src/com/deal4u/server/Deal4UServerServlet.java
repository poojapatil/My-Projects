package com.deal4u.server;

import java.io.IOException;

import javax.servlet.http.*;

import com.deal4u.helper.Constant;

@SuppressWarnings("serial")
public class Deal4UServerServlet extends HttpServlet {
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String method="";
		if(method!=null)
		{
			if(method.equalsIgnoreCase(Constant.MOVIE_DEALS))
			{
				
			}else if(method.equalsIgnoreCase(Constant.FOOD_DEALS))
			{
				
			}
			else
			{
				
			}
		}
		
		
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}
