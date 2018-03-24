package com.administrator.hezhihai.model.proxy ;
import com.administrator.hezhihai.bean.Diary;
import com.administrator.hezhihai.bean.GuideReqInfo;
import com.administrator.hezhihai.bean.Tourist;
import com.administrator.hezhihai.dbc.* ;
import com.administrator.hezhihai.model.*;
import com.administrator.hezhihai.model.impl.TouristDAOImpl;

import java.sql.*;
public class TouristDAOProxy implements ITouristDAO {
	private DatabaseConnection dbc = null ;
	private ITouristDAO dao = null ;
	public TouristDAOProxy(){
		try{
			this.dbc = new DatabaseConnection() ;
		}catch(Exception e){
			e.printStackTrace() ;
		}
		this.dao = new TouristDAOImpl(dbc.getConnection()) ;
	}
	public boolean loginTourist(Tourist tourist) throws Exception{
		boolean flag = false ;
		try{
			flag = this.dao.loginTourist(tourist) ;	// 调用真实主题，完成操作
		}catch(Exception e){
			throw e ;
		}finally{
			this.dbc.close() ;
		}
		return flag ;
	}
	public String searchGuiderGroupInfo(String keyWords) throws Exception {
		// TODO Auto-generated method stub
		String guiderGroupInfo;
		try{
			guiderGroupInfo = this.dao.searchGuiderGroupInfo(keyWords);	// 调用真实主题，完成操作
		}catch(Exception e){
			throw e ;
		}finally{
			this.dbc.close() ;
		}
		return guiderGroupInfo;
	}
	@Override
	public boolean registerTourist(Tourist tourist) throws Exception {
		// TODO Auto-generated method stub
		boolean res;
		try{
			res = this.dao.registerTourist(tourist);	// 调用真实主题，完成操作
		}catch(Exception e){
			throw e ;
		}finally{
			this.dbc.close() ;
		}
		return res;
	}
	@Override
	public boolean addDiary(Diary diary) throws Exception {
		// TODO Auto-generated method stub
		boolean res;
		try{
			res = this.dao.addDiary(diary);	// 调用真实主题，完成操作
		}catch(Exception e){
			throw e ;
		}finally{
			this.dbc.close() ;
		}
		return res;
	}
	@Override
	public boolean addGuideReqInfo(GuideReqInfo guideReqInfo) throws Exception {
		// TODO Auto-generated method stub
		boolean res;
		try{
			res = this.dao.addGuideReqInfo(guideReqInfo);	// 调用真实主题，完成操作
		}catch(Exception e){
			throw e ;
		}finally{
			this.dbc.close() ;
		}
		return res;
	}
	@Override
	public boolean addAppointInfo(int info_id, int t_id) throws Exception {
		// TODO Auto-generated method stub
		boolean res;
		try{
			res = this.dao.addAppointInfo(info_id, t_id);	// 调用真实主题，完成操作
		}catch(Exception e){
			throw e ;
		}finally{
			this.dbc.close() ;
		}
		return res;
	}
	@Override
	public String viewAppointInfo(int t_id) throws Exception {
		// TODO Auto-generated method stub
		String res;
		try{
			res = this.dao.viewAppointInfo(t_id);	// 调用真实主题，完成操作
		}catch(Exception e){
			throw e ;
		}finally{
			this.dbc.close() ;
		}
		return res;
	}
	@Override
	public String serviceAppeal(int t_id) throws Exception {
		// TODO Auto-generated method stub
		String res;
		try{
			res = this.dao.serviceAppeal(t_id);	// 调用真实主题，完成操作
		}catch(Exception e){
			throw e ;
		}finally{
			this.dbc.close() ;
		}
		return res;
	}
} 