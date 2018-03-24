package com.administrator.hezhihai.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.administrator.hezhihai.factory.DAOFactory;

public class ServiceAppealServlet  extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int t_id = Integer.valueOf(req.getParameter("t_id"));
		String result = null;
		try {
			result = DAOFactory.getITouristDAOInstance().serviceAppeal(t_id);
			resp.setContentType("application/json; charset=utf-8");
			resp.setCharacterEncoding("UTF-8");  
		    OutputStream out = resp.getOutputStream();  
		    out.write(result.getBytes("UTF-8"));
		    out.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!result.equals("")){
			System.out.println(result);
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req,resp) ;
	}
}
