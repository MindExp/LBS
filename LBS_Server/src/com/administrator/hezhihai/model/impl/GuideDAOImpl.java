package com.administrator.hezhihai.model.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.administrator.hezhihai.bean.Guider;
import com.administrator.hezhihai.model.IGuideDAO;

public class GuideDAOImpl implements IGuideDAO{
	private Connection conn = null ;
	private PreparedStatement pstmt = null ;
	public GuideDAOImpl(Connection conn){
		this.conn = conn ;
	}
	@Override
	public boolean loginGuide(Guider guide) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = false ;
		String sql = "SELECT * FROM tb_guider WHERE g_email=? AND g_password=?" ;
		this.pstmt = this.conn.prepareStatement(sql) ;
		this.pstmt.setString(1,guide.getG_email());
		this.pstmt.setString(2,guide.getG_password()) ;
		ResultSet rs = this.pstmt.executeQuery() ;
		if(rs.next()){
			guide.setG_age(rs.getString("g_age"));
			guide.setG_gender(rs.getString("g_gender"));
			guide.setG_id(rs.getInt("g_id"));
			guide.setG_name(rs.getString("g_name"));
			guide.setG_phone(rs.getString("g_phone"));
			flag = true ;
		}
		this.pstmt.close() ;
		return flag ;
	}
	@Override
	public boolean registerGuide(Guider guide) throws Exception {
		// TODO Auto-generated method stub
		String check = "select g_email from tb_guider where g_email = '"+ guide.getG_email() +"'";
		this.pstmt = this.conn.prepareStatement(check) ;
		ResultSet temp = this.pstmt.executeQuery();
		boolean res = false;
		if(!temp.next()){
			String sql = "insert into tb_guider (g_email, g_password) " +
					"values ('"+ guide.getG_email()+"', '"+ guide.getG_password()+"')";
			this.pstmt = this.conn.prepareStatement(sql) ;
			if(this.pstmt.executeUpdate() == 1){
				res = true;
			}
		}
		return res;		//返回值为1代表注册成功，返回值为0代表该账号（t_email）已经存在
	}

}
