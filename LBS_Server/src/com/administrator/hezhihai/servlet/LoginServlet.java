package com.administrator.hezhihai.servlet ;
import java.io.* ;
import javax.servlet.* ;
import javax.servlet.http.* ;
import com.administrator.hezhihai.bean.*;
import com.administrator.hezhihai.factory.*;
import com.alibaba.fastjson.JSONObject;

public class LoginServlet extends HttpServlet {
	
	/**
	 * �û���¼
	 */
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{

		System.out.println(req.getParameter("requestType") + req.getParameter("loginType")
				+ req.getParameter("login_email") + req.getParameter("login_pwd"));
		try {
			if(req.getParameter("loginType").equals("touristLogin")){
				/*
				 * �ο���ݵ�¼
				 */
				Tourist tourist = new Tourist();
				tourist.setT_email(req.getParameter("login_email"));
				tourist.setT_password(req.getParameter("login_pwd"));
				boolean res = DAOFactory.getITouristDAOInstance().loginTourist(tourist);
				if(res){
					/*
					 * �����¼�ɹ������û���Ϣ��JSON������ʽ����
					 */
					JSONObject touristJsonObject = new JSONObject();
					touristJsonObject = (JSONObject)JSONObject.toJSON(tourist);
					resp.addHeader("loginResult", "success");
					resp.setContentType("application/json; charset=utf-8");
					resp.setCharacterEncoding("UTF-8");  
				    OutputStream out = resp.getOutputStream();  
				    out.write(touristJsonObject.toJSONString().getBytes("UTF-8"));  
				    out.flush();
					System.out.println("�û���¼��Ϣ��" + touristJsonObject.toJSONString());
				}
			}else if(req.getParameter("loginType").equals("tourGuiderLogin")){
				/*
				 * ������ݵ�¼
				 */
				Guider guide = new Guider();
				guide.setG_email(req.getParameter("login_email"));
				guide.setG_password(req.getParameter("login_pwd"));
				boolean res = DAOFactory.getIGuideDAOInstance().loginGuide(guide);
				if(res){
					JSONObject guideJsonObject = new JSONObject();
					guideJsonObject = (JSONObject)JSONObject.toJSON(guide);
					resp.addHeader("loginResult", "success");
					resp.setContentType("application/json; charset=utf-8");
					resp.setCharacterEncoding("UTF-8");  
				    OutputStream out = resp.getOutputStream();  
				    out.write(guideJsonObject.toJSONString().getBytes("UTF-8"));  
				    out.flush();
					System.out.println("�û���¼��Ϣ��" + guideJsonObject.toJSONString());
				}
				
			}else if(req.getParameter("loginType").equals("spAdminLogin")){
				/*
				 * ��������Ա��ݵ�¼
				 */
				Spadmin spadmin = new Spadmin();
				spadmin.setSp_email(req.getParameter("login_email"));
				spadmin.setSp_password(req.getParameter("login_pwd"));
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