package com.administrator.hezhihai.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.administrator.hezhihai.bean.GuideReqInfo;
import com.administrator.hezhihai.factory.DAOFactory;

public class GuideRequirementInfo  extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
		try {
			if(req.getParameter("guideReqInfoRequestType").equals("addGuideReq")){
				/*
				 * 添加导游需求信息
				 */
				GuideReqInfo guideReqInfo = new GuideReqInfo();
				guideReqInfo.setT_id(Integer.parseInt(req.getParameter("t_id")));
				guideReqInfo.setR_date(req.getParameter("req_date"));
				guideReqInfo.setR_spName(req.getParameter("req_spName"));
				guideReqInfo.setR_num(req.getParameter("req_num"));
				guideReqInfo.setR_tip(req.getParameter("req_tip"));
				guideReqInfo.setR_extrainfo(req.getParameter("req_extraInfo"));
				boolean res = DAOFactory.getITouristDAOInstance().addGuideReqInfo(guideReqInfo);
				if(res){
					resp.addHeader("addGuideReqInfo", "succeed");	//日记信息添加成功
					resp.setContentType("application/json; charset=utf-8");
					resp.setCharacterEncoding("UTF-8"); 
					return;
				}else{
					resp.addHeader("addGuideReqInfo", "failed");	//日记信息添加成功
					resp.setContentType("application/json; charset=utf-8");
					resp.setCharacterEncoding("UTF-8"); 
					return;
				}
			}else if(req.getParameter("guideReqInfoRequestType").equals("")){
				/*
				 * 查看旅行日记
				 */
				
				
			}else if(req.getParameter("guideReqInfoRequestType").equals("")){
				/*
				 * 修改旅行日记
				 */
				
				
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
