package com.rim.point;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import com.rim.util.DBConnector;

public class PointDAO {
	
	//insert
	public int insert(PointDTO dto) throws Exception {
		Connection conn=DBConnector.getConnection();
		String sql="insert into point values(idx_seq.nextval,?,?,?,?,?,?)";
		
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, dto.getP_name());
  		pst.setInt(2, dto.getKor());
  		pst.setInt(3, dto.getEng());
  		pst.setInt(4, dto.getMath());
  		pst.setInt(5, dto.getTotal());
  		pst.setDouble(6, dto.getP_avg());
  		
  		int result=pst.executeUpdate();
  		
  		DBConnector.disConnect(pst, conn);
		
		return result;
	}
	
	//update
	public int update(PointDTO dto) throws Exception{
		Connection conn = DBConnector.getConnection();
		String sql="update point set p_name=?, kor=?, eng=?, math=?, total=?, p_avg=? where idx=?";
		
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, dto.getP_name());
		pst.setInt(2, dto.getKor());
		pst.setInt(3, dto.getEng());
		pst.setInt(4, dto.getMath());
		pst.setInt(5, dto.getTotal());
		pst.setDouble(6, dto.getP_avg());
		pst.setInt(7, dto.getIdx());
		
		int result=pst.executeUpdate();
		
		DBConnector.disConnect(pst, conn);
		return result;
	}
	
	//delete
	public int delete(int idx) throws Exception {
		Connection conn = DBConnector.getConnection();
		String sql="delete point where idx=?";
		
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setInt(1, idx);
		
		int result=pst.executeUpdate();
		
		DBConnector.disConnect(pst, conn);
		
		return result;
	}
	
	//selectOne
	public PointDTO selectOne(int idx) throws Exception {
		Connection conn = DBConnector.getConnection();
		String sql = "select * from point where idx=?";
		
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setInt(1, idx);
		
		ResultSet rs = pst.executeQuery();
		
		PointDTO dto=null;
		if(rs.next()) {
			dto = new PointDTO();
			dto.setIdx(rs.getInt("idx"));
			dto.setP_name(rs.getString("p_name"));
			dto.setKor(rs.getInt("kor"));
			dto.setEng(rs.getInt("eng"));
			dto.setMath(rs.getInt("math"));
			dto.setTotal(rs.getInt("total"));
			dto.setP_avg(rs.getDouble("p_avg"));
		}
		
		DBConnector.disConnect(rs, pst, conn);
		
		return dto;
	}
	
	//selectList
	public ArrayList<PointDTO> selectList(String kind, String search, int startRow, int lastRow) throws Exception {
		Connection conn = DBConnector.getConnection();
		String sql = "select * from "
				+ "(select rownum R, p.* from "
				+ "(select * from point where "+kind+" like ? order by idx desc)p) "
				+ "where R between ? and ?";
		
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, "%"+search+"%");
		pst.setInt(2, startRow);
		pst.setInt(3, lastRow);
		
		ResultSet rs = pst.executeQuery();
		
		PointDTO dto=null;
		ArrayList<PointDTO> arr= new ArrayList<PointDTO>();
		
		while (rs.next()) {
				dto = new PointDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setP_name(rs.getString("p_name"));
				dto.setKor(rs.getInt("kor"));
				dto.setEng(rs.getInt("eng"));
				dto.setMath(rs.getInt("math"));
				dto.setTotal(rs.getInt("total"));
				dto.setP_avg(rs.getDouble("p_avg"));
				arr.add(dto);
		}
		
		DBConnector.disConnect(rs, pst, conn);
		
		return arr;
	}
	
	public int countTotalRow(String kind, String search) throws Exception {
		int count=0;
		
		Connection conn = DBConnector.getConnection();
		String sql = "select count(idx) from point where "+kind+" like ?";
		
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, "%"+search+"%");
		ResultSet rs = pst.executeQuery();
		
		rs.next();
		count = rs.getInt("count(idx)");
		
		DBConnector.disConnect(rs, pst, conn);
		
		return count;
	}
}
