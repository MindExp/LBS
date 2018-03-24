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
				 * ��ӵ���������Ϣ
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
					resp.addHeader("addGuideReqInfo", "succeed");	//�ռ���Ϣ��ӳɹ�
					resp.setContentType("application/json; charset=utf-8");
					resp.setCharacterEncoding("UTF-8"); 
					return;
				}else{
					resp.addHeader("addGuideReqInfo", "failed");	//�ռ���Ϣ��ӳɹ�
					resp.setContentType("application/json; charset=utf-8");
					resp.setCharacterEncoding("UTF-8"); 
					return;
				}
			}else if(req.getParameter("guideReqInfoRequestType").equals("")){
				/*
				 * �鿴�����ռ�
				 */
				
				
			}else if(req.getParameter("guideReqInfoRequestType").equals("")){
				/*
				 * �޸������ռ�
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
