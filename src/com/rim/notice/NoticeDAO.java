package com.rim.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.rim.page.SearchRow;
import com.rim.upload.UploadDTO;
import com.rim.util.DBConnector;

public class NoticeDAO {
	//selectList()
		public ArrayList<NoticeDTO> selectList(SearchRow searchRow) throws Exception {
			ArrayList<NoticeDTO> arr = new ArrayList<NoticeDTO>();
			NoticeDTO dto = null;
			Connection conn = DBConnector.getConnection();
			String sql="select * from "
					+ "(select rownum R, n.* from "
					+ "(select * from notice where "+searchRow.getSearch().getKind()+" like ? order by num desc) n) "
					+ "where R between ? and ?";
			
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, "%"+searchRow.getSearch().getSearch()+"%");
			pst.setInt(2, searchRow.getStartRow());
			pst.setInt(3, searchRow.getLastRow());
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				dto = new NoticeDTO();
				dto.setContents(rs.getString("contents"));
				
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setHit(rs.getInt("hit"));
				dto.setNum(rs.getInt("num"));
				dto.setTitle(rs.getString("title"));
				dto.setWriter(rs.getString("writer"));
				arr.add(dto);
			}
			
			DBConnector.disConnect(rs, pst, conn);
			return arr;
		}
		
		//selectOne()
		public NoticeDTO selectOne(int num) throws Exception {
			NoticeDTO dto = null;
			Connection conn = DBConnector.getConnection();
			String sql="select N.*, U.* from notice N full outer join"
					+ "upload U on N.num=U.num where N.num=?";
			
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, num);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				dto = new NoticeDTO();
				dto.setContents(rs.getString("contents"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setHit(rs.getInt("hit"));
				dto.setNum(rs.getInt("num"));
				dto.setTitle(rs.getString("title"));
				dto.setWriter(rs.getString("writer"));
				
				UploadDTO uploadDTO = new UploadDTO();
				uploadDTO.setPnum(rs.getInt("pnum"));
				uploadDTO.setOname(rs.getString("oname"));
				uploadDTO.setFname(rs.getString("fname"));
				dto.setUploadDTO(uploadDTO);
				//dto.setUploadDTO(new UploadDTO());
				//dto.getUploadDTO().setPnum(rs.getInt("pnum"));
			}
			DBConnector.disConnect(rs, pst, conn);
			return dto;
		}
		
		//seq
		public int getNum() throws Exception {
			int result=0;
			Connection conn = DBConnector.getConnection();
			String sql = "select notice_seq.nextval from dual";
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			rs.next();
			
			result=rs.getInt(1);
			
			DBConnector.disConnect(rs, pst, conn);
			
			return result;
		}
		
		//insert()
		public int insert(NoticeDTO dto, Connection conn) throws Exception {
			int result=0;
			String sql = "insert into notice values(?,?,?,?,sysdate,0)";
			
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, dto.getNum());
			pst.setString(2, dto.getTitle());
			pst.setString(3, dto.getContents());
			pst.setString(4, dto.getWriter());
			
			result=pst.executeUpdate();
			
			pst.close();
			
			return result;
		}
		
		//update()
		public int update(NoticeDTO dto) throws Exception {
			int result=0;
			Connection conn = DBConnector.getConnection();
			String sql = "update notice set title=?, contents=?, reg_date=sysdate where num=?";
			
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, dto.getTitle());
			pst.setString(2, dto.getContents());
			pst.setInt(3, dto.getNum());
			
			result=pst.executeUpdate();
			
			DBConnector.disConnect(pst, conn);
			
			return result;
		}
		
		//delete()
		public int delete(int num) throws Exception {
			int result=0;
			Connection conn = DBConnector.getConnection();
			String sql = "delete notice where num=?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, num);
			
			result = pst.executeUpdate();
			
			DBConnector.disConnect(pst, conn);
			
			return result;
		}
		
		//countHit()
		public int countHit(int num) throws Exception {
			int result=0;
			Connection conn = DBConnector.getConnection();
			String sql = "update notice set hit=hit+1 where num=?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, num);
			
			result=pst.executeUpdate();
			
			DBConnector.disConnect(pst, conn);
			
			return result;
		}
		
		public int countTotal(SearchRow searchRow) throws Exception {
			int result=0;
			Connection conn = DBConnector.getConnection();
			String sql = "select count(num) from notice where "+searchRow.getSearch().getKind()+" like ?";
			
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, "%"+searchRow.getSearch().getSearch()+"%");
			ResultSet rs = pst.executeQuery();
			
			rs.next();
			result = rs.getInt("count(num)");
			
			
			return result;
		}
}
