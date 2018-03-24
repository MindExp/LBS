package com.administrator.hezhihai.model.proxy;

import com.administrator.hezhihai.bean.Guider;
import com.administrator.hezhihai.dbc.DatabaseConnection;
import com.administrator.hezhihai.model.IGuideDAO;
import com.administrator.hezhihai.model.impl.GuideDAOImpl;

public class GuideDAOProxy implements IGuideDAO{
	private DatabaseConnection dbc = null ;
	private IGuideDAO dao = null ;
	public GuideDAOProxy(){
		try{
			this.dbc = new DatabaseConnection() ;
		}catch(Exception e){
			e.printStackTrace() ;
		}
		this.dao = new GuideDAOImpl(dbc.getConnection()) ;
	}
	@Override
	public boolean loginGuide(Guider guide) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = false ;
		try{
			flag = this.dao.loginGuide(guide) ;	// 调用真实主题，完成操作
		}catch(Exception e){
			throw e ;
		}finally{
			this.dbc.close() ;
		}
		return flag ;
	}
	@Override
	public boolean registerGuide(Guider guide) throws Exception {
		// TODO Auto-generated method stub
		boolean res;
		try{
			res = this.dao.registerGuide(guide);	// 调用真实主题，完成操作
		}catch(Exception e){
			throw e ;
		}finally{
			this.dbc.close() ;
		}
		return res;
	}
}
