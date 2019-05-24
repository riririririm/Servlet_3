package com.rim.qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.rim.page.SearchRow;
import com.rim.util.DBConnector;

public class QnaDAO {
	
	// seq
	public int getNum() throws Exception {
		int result = 0;
		Connection conn = DBConnector.getConnection();
		String sql = "select qna_seq.nextval from dual";
		PreparedStatement pst = conn.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		rs.next();

		result = rs.getInt(1);

		DBConnector.disConnect(rs, pst, conn);

		return result;
	}
	
	//insert
	public int insert(QnaDTO qnaDTO, Connection conn) throws Exception{
		int result=0;
		
		String sql = "insert into qna values(?,?,?,?,sysdate,0,?,0,0)";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setInt(1, qnaDTO.getNum());
		pst.setString(2, qnaDTO.getTitle());
		pst.setString(3, qnaDTO.getContents());
		pst.setString(4, qnaDTO.getWriter());
		pst.setInt(5, qnaDTO.getNum());
		
		result = pst.executeUpdate();
		
		pst.close();
		return result;
	}

	//select*
	public ArrayList<QnaDTO> list(SearchRow searchRow) throws Exception {
		ArrayList<QnaDTO> arr = new ArrayList<QnaDTO>();
		
		Connection conn = DBConnector.getConnection();
		String sql="select * from "
				+ "(select rownum R, Q.* from "
				+ "(select * from qna where "+searchRow.getSearch().getKind()+" like ? order by ref desc, step asc) Q) "
				+ "where R between ? and ?";
		
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, "%"+searchRow.getSearch().getSearch()+"%");
		pst.setInt(2, searchRow.getStartRow());
		pst.setInt(3, searchRow.getLastRow());
		
		ResultSet rs = pst.executeQuery();
		
		QnaDTO qnaDTO = null;
		while(rs.next()) {
			qnaDTO = new QnaDTO();
			qnaDTO.setNum(rs.getInt("num"));
			qnaDTO.setTitle(rs.getString("title"));
			qnaDTO.setContents(rs.getString("contents"));
			qnaDTO.setWriter(rs.getString("writer"));
			qnaDTO.setReg_date(rs.getDate("reg_date"));
			qnaDTO.setHit(rs.getInt("hit"));
			qnaDTO.setRef(rs.getInt("ref"));
			qnaDTO.setStep(rs.getInt("step"));
			qnaDTO.setDepth(rs.getInt("depth"));
			arr.add(qnaDTO);
		}
		
		DBConnector.disConnect(pst,conn);
		
		return arr;
	}
	
	public int countTotal(SearchRow searchRow) throws Exception {
		int result = 0;
		Connection conn = DBConnector.getConnection();
		String sql = "select count(num) from qna where "+searchRow.getSearch().getKind()+" like ?";
		
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, "%"+searchRow.getSearch().getSearch()+"%");
		ResultSet rs = pst.executeQuery();
		
		rs.next();
		result = rs.getInt("count(num)");
		
		
		return result;
	}
}
