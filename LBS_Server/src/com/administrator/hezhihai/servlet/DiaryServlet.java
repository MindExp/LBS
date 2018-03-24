package com.administrator.hezhihai.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.administrator.hezhihai.bean.Diary;
import com.administrator.hezhihai.factory.DAOFactory;

public class DiaryServlet extends HttpServlet {
	/**
	 * 添加旅行日记
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
		try {
			if(req.getParameter("diaryRequestType").equals("addDiary")){
				/*
				 * 添加旅行日记
				 */
				Diary diary = new Diary();
				diary.setT_id(Integer.parseInt(req.getParameter("t_id")));
				diary.setT_id(Integer.valueOf(req.getParameter("t_id")));
				diary.setD_title(req.getParameter("diary_title"));
				diary.setD_date(req.getParameter("diary_date"));
				diary.setD_content(req.getParameter("diary_content"));
				boolean res = DAOFactory.getITouristDAOInstance().addDiary(diary);
				
				if(res){
					resp.addHeader("addDiaryResult", "succeed");	//日记信息添加成功
					resp.setContentType("application/json; charset=utf-8");
					resp.setCharacterEncoding("UTF-8"); 
					return;
				}else{
					resp.addHeader("addDiaryResult", "failed");	//日记信息添加成功
					resp.setContentType("application/json; charset=utf-8");
					resp.setCharacterEncoding("UTF-8"); 
					return;
				}
			}else if(req.getParameter("diaryRequestType").equals("")){
				/*
				 * 查看旅行日记
				 */				
				
			}else if(req.getParameter("diaryRequestType").equals("")){
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