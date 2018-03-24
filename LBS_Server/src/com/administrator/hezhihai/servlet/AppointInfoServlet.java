package com.administrator.hezhihai.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.administrator.hezhihai.factory.DAOFactory;

public class AppointInfoServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			if(req.getParameter("appointInfoRequestType").equals("addAppointInfo")){
				boolean res = DAOFactory.getITouristDAOInstance().addAppointInfo(Integer.valueOf(req.getParameter("info_id")),
						Integer.valueOf(req.getParameter("t_id")));
				if(res){
					resp.addHeader("addAppointInfo", "succeed");	//日记信息添加成功
					resp.setContentType("application/json; charset=utf-8");
					resp.setCharacterEncoding("UTF-8"); 
					return;
				}else{
					resp.addHeader("addAppointInfo", "failed");	//日记信息添加成功
					resp.setContentType("application/json; charset=utf-8");
					resp.setCharacterEncoding("UTF-8"); 
					return;
				}
			}else if(req.getParameter("appointInfoRequestType").equals("viewAppointInfo")){
				int t_id = Integer.valueOf(req.getParameter("t_id"));
				String result = null;
				try {
					result = DAOFactory.getITouristDAOInstance().viewAppointInfo(t_id);
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
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req,resp) ;
	}
}
