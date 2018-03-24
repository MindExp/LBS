package com.administrator.hezhihai.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControlServlet extends HttpServlet{
	/**
	 * MVC¿ØÖÆ²ã
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{	
		if(req.getParameter("requestType").equals("searchSPGuiderGroupInfo")){
			req.getRequestDispatcher("/SearchGuiderGroupInfo").forward(req,resp);
			return;
		}else if(req.getParameter("requestType").equals("userLogin")){
			req.getRequestDispatcher("/LoginServlet").forward(req,resp);
			return;
		}else if(req.getParameter("requestType").equals("userRegister")){
			req.getRequestDispatcher("/RegisterServlet").forward(req,resp);
			return;
		}else if(req.getParameter("requestType").equals("diaryInfo")){
			req.getRequestDispatcher("/DiaryServlet").forward(req,resp);
			return;
		}else if(req.getParameter("requestType").equals("guideReqInfo")){
			req.getRequestDispatcher("/GuideRequirementInfo").forward(req,resp);
			return;
		}else if(req.getParameter("requestType").equals("appointTourGuide")){
			req.getRequestDispatcher("/AppointInfoServlet").forward(req,resp);
			return;
		}else if(req.getParameter("requestType").equals("serviceAppeal")){
			req.getRequestDispatcher("/ServiceAppealServlet").forward(req,resp);
			return;
		}
		
		
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
		this.doGet(req, resp);
	}
}