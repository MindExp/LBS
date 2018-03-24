package com.administrator.hezhihai.model.impl;

import com.administrator.hezhihai.bean.Diary;
import com.administrator.hezhihai.bean.GuideReqInfo;
import com.administrator.hezhihai.bean.GuiderGroupInfo;
import com.administrator.hezhihai.bean.ServiceAppealInfo;
import com.administrator.hezhihai.bean.Tourist;
import com.administrator.hezhihai.bean.ViewAppointInfo;
import com.administrator.hezhihai.model.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.sql.* ;
public class TouristDAOImpl implements ITouristDAO {
	private Connection conn = null ;
	private PreparedStatement pstmt = null ;
	public TouristDAOImpl(Connection conn){
		this.conn = conn ;
	}
	public boolean loginTourist(Tourist tourist) throws Exception{
		boolean flag = false ;
		String sql = "SELECT * FROM tb_tourist WHERE t_email=? AND t_password=?" ;
		this.pstmt = this.conn.prepareStatement(sql) ;
		this.pstmt.setString(1,tourist.getT_email());
		this.pstmt.setString(2,tourist.getT_password()) ;
		ResultSet rs = this.pstmt.executeQuery() ;
		if(rs.next()){
			tourist.setT_id(rs.getInt("t_id"));
			tourist.setT_name(rs.getString("t_name")) ;	// 取出一个用户的真实姓名
			tourist.setT_age(rs.getInt("t_age"));
			tourist.setT_gender(rs.getString("t_gender"));
			tourist.setT_phone(rs.getString("t_phone"));
			flag = true ;
		}
		this.pstmt.close() ;
		return flag ;
	}
	public String searchGuiderGroupInfo(String keyWords) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select tb_guider.g_name,sp_name, info_num, info_date, info_id, info_detail from tb_guider, " +
				"(select tb_info.g_id, sp_name, info_num, info_date, info_id, info_detail from tb_info, " +
				"(select tb_spadmin.sp_id, sp_name from tb_spadmin where sp_name like '%"+ keyWords +"%') AS temp_sp " +
				"where tb_info.info_status = '1' AND tb_info.info_num > tb_info.info_appoint AND tb_info.sp_id = temp_sp.sp_id) AS temp " +
				"where tb_guider.g_id = temp.g_id;";
		this.pstmt = this.conn.prepareStatement(sql) ;
		ResultSet rs = this.pstmt.executeQuery() ;
		JSONArray guideGroupInfoJsonArray = new JSONArray();
		while(rs.next()){
			GuiderGroupInfo guiderGroupInfo = new GuiderGroupInfo();
			JSONObject jsonObject = new JSONObject();
			guiderGroupInfo.setG_name(rs.getString("g_name"));
			guiderGroupInfo.setSp_name(rs.getString("sp_name"));
			guiderGroupInfo.setInfo_num(rs.getInt("info_num"));
			guiderGroupInfo.setInfo_date(rs.getString("info_date"));
			guiderGroupInfo.setInfo_id(rs.getInt("info_id"));
			guiderGroupInfo.setInfo_detail(rs.getString("info_detail"));
			jsonObject = (JSONObject) JSONObject.toJSON(guiderGroupInfo);
			guideGroupInfoJsonArray.add(jsonObject);
		}
		return guideGroupInfoJsonArray.toJSONString();	//以JSON数组形式返回客户端
	}
	@Override
	public boolean registerTourist(Tourist tourist) throws Exception {
		// TODO Auto-generated method stub
		String check = "select t_email from tb_tourist where t_email = '"+ tourist.getT_email() +"'";
		this.pstmt = this.conn.prepareStatement(check) ;
		ResultSet temp = this.pstmt.executeQuery();
		boolean res = false;
		if(!temp.next()){
			String sql = "insert into tb_tourist (t_email, t_password) " +
					"values ('"+ tourist.getT_email()+"', '"+ tourist.getT_password()+"')";
			this.pstmt = this.conn.prepareStatement(sql) ;
			if(this.pstmt.executeUpdate() == 1){
				res = true;
			}
		}
		return res;		//返回值为1代表注册成功，返回值为0代表该账号（t_email）已经存在
	}
	@Override
	public boolean addDiary(Diary diary) throws Exception {
		// TODO Auto-generated method stub
		boolean res = false;
		String sql = "insert into tb_diary (t_id, diary_title, diary_date, diary_content) " +
				"values('" + diary.getT_id() + "', '" + diary.getD_title() + "', '" + diary.getD_date() + "', '" + diary.getD_content() + "')";
		this.pstmt = this.conn.prepareStatement(sql);
		if(this.pstmt.executeUpdate() == 1){
			res = true;
		}
		return res;
	}
	@Override
	public boolean addGuideReqInfo(GuideReqInfo guideReqInfo) throws Exception {
		// TODO Auto-generated method stub
		boolean res = false;
		String sql = "insert into tb_guidereq (t_id, r_date, r_spName, r_num, r_tip, r_extrainfo) " +
				"values ('"+ guideReqInfo.getT_id() +"', '" + guideReqInfo.getR_date() +"', '" + guideReqInfo.getR_spName() +"', '" + guideReqInfo.getR_num() +"', '" + guideReqInfo.getR_tip() +"', '" + guideReqInfo.getR_extrainfo() +"')";
		this.pstmt = this.conn.prepareStatement(sql);
		if(this.pstmt.executeUpdate() == 1){
			res = true;
		}
		return res;
	}
	@Override
	public boolean addAppointInfo(int info_id, int t_id) throws Exception {
		// TODO Auto-generated method stub
		String check = "select * from tb_appointment where info_id = '"+ info_id +"' AND t_id = '"+ t_id + "'";
		this.pstmt = this.conn.prepareStatement(check) ;
		ResultSet temp = this.pstmt.executeQuery();
		boolean res = false;
		if(!temp.next()){
			String sql = "insert into tb_appointment (info_id, t_id) values ('"+ info_id +"', '"+ t_id +"')";
			this.pstmt = this.conn.prepareStatement(sql) ;
			if(this.pstmt.executeUpdate() == 1){
				sql = "update tb_info set info_appoint = info_appoint + 1 where info_id = '"+ info_id +"'";
				this.pstmt = this.conn.prepareStatement(sql) ;
				if(this.pstmt.executeUpdate() == 1){
					res = true;
				}
			}
		}
		return res;
	}
	@Override
	public String viewAppointInfo(int t_id) throws Exception {
		// TODO Auto-generated method stub
		/*String sql = "select info_date, sp_name, g_name, g_phone from (select info_date, sp_name from tb_info, tb_spadmin where tb_info.info_id IN " +
				"(select info_id from tb_appointment where t_id = "+ t_id +") AND tb_info.sp_id = tb_spadmin.sp_id) As tb_temp1," +
				" (select g_name, g_phone from tb_guider where tb_tourist.t_id = "+ t_id +") As tb_temp2";*/
		String sql = "select info_date, sp_name, g_name, g_phone from (select g_id, sp_id, info_date from tb_appointment, tb_info " +
				"where tb_appointment.info_id = tb_info.info_id AND tb_appointment.t_id = '"+ t_id +"') AS tb_temp, tb_guider, tb_spadmin " +
						"where tb_temp.g_id = tb_guider.g_id AND tb_temp.sp_id = tb_spadmin.sp_id";
		this.pstmt = this.conn.prepareStatement(sql) ;
		ResultSet rs = this.pstmt.executeQuery() ;
		JSONArray appointInfoJsonArray = new JSONArray();
		while(rs.next()){
			ViewAppointInfo viewAppointInfo = new ViewAppointInfo();
			JSONObject jsonObject = new JSONObject();
			viewAppointInfo.setInfo_date(rs.getString("info_date"));
			viewAppointInfo.setSp_name(rs.getString("sp_name"));
			viewAppointInfo.setG_name(rs.getString("g_name"));
			viewAppointInfo.setG_phone(rs.getString("g_phone"));
			jsonObject = (JSONObject) JSONObject.toJSON(viewAppointInfo);
			appointInfoJsonArray.add(jsonObject);
		}
		return appointInfoJsonArray.toJSONString();	//以JSON数组形式返回客户端
	}
	@Override
	public String serviceAppeal(int t_id) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select app_id, sp_name, g_name, sp_hotline from (select app_id, sp_id, g_id from tb_info, tb_appointment where t_id = '"+ t_id +"'" +
				" AND tb_appointment.info_id = tb_info.info_id) AS tb_temp1, " +
				"tb_guider, tb_spadmin where tb_guider.g_id = tb_temp1.g_id AND tb_spadmin.sp_id = tb_temp1.sp_id";
		this.pstmt = this.conn.prepareStatement(sql) ;
		ResultSet rs = this.pstmt.executeQuery() ;
		JSONArray serviceAppealJsonArray = new JSONArray();
		while(rs.next()){
			ServiceAppealInfo  serviceAppealInfo= new ServiceAppealInfo();
			JSONObject jsonObject = new JSONObject();
			serviceAppealInfo.setApp_id(rs.getInt("app_id"));
			serviceAppealInfo.setSp_name(rs.getString("sp_name"));
			serviceAppealInfo.setSp_hotline(rs.getString("sp_hotline"));
			serviceAppealInfo.setG_name(rs.getString("g_name"));
			jsonObject = (JSONObject) JSONObject.toJSON(serviceAppealInfo);
			serviceAppealJsonArray.add(jsonObject);
		}
		return serviceAppealJsonArray.toJSONString();	//以JSON数组形式返回客户端
	}
} 