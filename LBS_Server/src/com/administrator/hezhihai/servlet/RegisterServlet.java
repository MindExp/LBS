package com.administrator.hezhihai.servlet;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.administrator.hezhihai.bean.Guider;
import com.administrator.hezhihai.bean.Spadmin;
import com.administrator.hezhihai.bean.Tourist;
import com.administrator.hezhihai.factory.DAOFactory;
import com.alibaba.fastjson.JSONObject;

public class RegisterServlet extends HttpServlet {
	/**
	 * 用户注册
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
		System.out.println(req.getParameter("requestType") + req.getParameter("registerType")
				+ req.getParameter("register_email") + req.getParameter("register_pwd"));
		try {
			if(req.getParameter("registerType").equals("touristRegister")){
				/*
				 * 游客注册
				 */
				Tourist tourist = new Tourist();
				tourist.setT_email(req.getParameter("register_email"));
				tourist.setT_password(req.getParameter("register_pwd"));
				boolean res = DAOFactory.getITouristDAOInstance().registerTourist(tourist);
				if(res){
					/*
					 * 如果注册成功，则将用户信息以JSON对象形式返回
					 */
					JSONObject touristJsonObject = new JSONObject();
					touristJsonObject = (JSONObject)JSONObject.toJSON(tourist);
					resp.addHeader("registerResult", "succeed");
					resp.setContentType("application/json; charset=utf-8");
					resp.setCharacterEncoding("UTF-8");  
				    OutputStream out = resp.getOutputStream();  
				    out.write(touristJsonObject.toJSONString().getBytes("UTF-8"));  
				    out.flush();
					System.out.println("游客注册信息：" + touristJsonObject.toJSONString());
				}
				return;
			}else if(req.getParameter("registerType").equals("guiderRegister")){
				/*
				 * 导游注册
				 */
				Guider guider = new Guider();
				guider.setG_email(req.getParameter("register_email"));
				guider.setG_password(req.getParameter("register_pwd"));
				boolean res = DAOFactory.getIGuideDAOInstance().registerGuide(guider);
				if(res){
					/*
					 * 如果注册成功，则将用户信息以JSON对象形式返回
					 */
					JSONObject touristJsonObject = new JSONObject();
					touristJsonObject = (JSONObject)JSONObject.toJSON(guider);
					resp.addHeader("registerResult", "succeed");
					resp.setContentType("application/json; charset=utf-8");
					resp.setCharacterEncoding("UTF-8");  
				    OutputStream out = resp.getOutputStream();  
				    out.write(touristJsonObject.toJSONString().getBytes("UTF-8"));  
				    out.flush();
					System.out.println("导游注册信息：" + touristJsonObject.toJSONString());
				}
				return;
			}else{
				/*
				 * 管理员注册
				 */
				Spadmin spadmin = new Spadmin();
				spadmin.setSp_email(req.getParameter("register_email"));
				spadmin.setSp_password(req.getParameter("register_pwd"));
				
				return;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
		this.doGet(req,resp) ;
	}
}