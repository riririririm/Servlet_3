package com.rim.qna;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.rim.action.Action;
import com.rim.action.ActionForward;
import com.rim.notice.NoticeDTO;
import com.rim.page.SearchMakePage;
import com.rim.page.SearchPager;
import com.rim.page.SearchRow;
import com.rim.upload.UploadDAO;
import com.rim.upload.UploadDTO;
import com.rim.util.DBConnector;

public class QnaService implements Action {
	private QnaDAO qnaDao;
	private UploadDAO uploadDAO;
	
	public QnaService() {
		qnaDao = new QnaDAO();
		uploadDAO = new UploadDAO();
	}

	@Override
	public ActionForward list(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		
		int curPage =1;
		try {
			curPage = Integer.parseInt(request.getParameter("curPage"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		String kind = request.getParameter("kind");
		String search = request.getParameter("search");
		
		SearchMakePage s = new SearchMakePage(curPage, kind, search);
		
		
		//1. row
		SearchRow searchRow = s.makeRow();
				
		//page
		int totalCount =0;
		try {
			System.out.println(searchRow.getStartRow());
			System.out.println(searchRow.getLastRow());
			totalCount= qnaDao.countTotal(searchRow);
			ArrayList<QnaDTO> ar = qnaDao.list(searchRow);
			request.setAttribute("list", ar);
			
			SearchPager searchPager = s.makePage(totalCount);
			request.setAttribute("pager", searchPager);
			
			actionForward.setCheck(true);
			actionForward.setPath("../WEB-INF/views/qna/qnaList.jsp");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			request.setAttribute("message", "server error");
			request.setAttribute("path", "../index.do");
			actionForward.setCheck(true);
			actionForward.setPath("../WEB-INF/views/common/result.jsp");
		}
		
		return actionForward;
	}

	@Override
	public ActionForward select(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionForward insert(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		actionForward.setCheck(true);
		actionForward.setPath("../WEB-INF/views/qna/qnaWrite.jsp");
		
		String method = request.getMethod();
		if(method.equals("POST")) {
			//실제 저장될 주소(C:)
			String saveDirectory = request.getServletContext().getRealPath("/upload");
			int maxPostSize = 1024*1024*100;
			Connection conn=null;
			try {
				MultipartRequest multipartRequest = new MultipartRequest(request, saveDirectory, maxPostSize, "UTF-8", new DefaultFileRenamePolicy());
				Enumeration<String> e= multipartRequest.getFileNames();
				ArrayList<UploadDTO> ar = new ArrayList<UploadDTO>();
						
				while(e.hasMoreElements()) {
					//파라미터 이름이 다를때
					String s = e.nextElement();
					String fname=multipartRequest.getFilesystemName(s);
					String oname = multipartRequest.getOriginalFileName(s);
					UploadDTO uploadDTO = new UploadDTO();
					uploadDTO.setFname(fname);
					uploadDTO.setOname(oname);
					ar.add(uploadDTO);
				}
				
				QnaDTO qnaDTO = new QnaDTO();
				qnaDTO.setTitle(multipartRequest.getParameter("title"));
				qnaDTO.setWriter(multipartRequest.getParameter("writer"));
				qnaDTO.setContents(multipartRequest.getParameter("contents"));
				
				conn = DBConnector.getConnection();
				
				//1. 시퀀스번호
				int num = qnaDao.getNum();
				qnaDTO.setNum(num);
				conn.setAutoCommit(false);
				
				//2. qna insert
				num = qnaDao.insert(qnaDTO, conn);
				
				//3. upload insert
				for(UploadDTO uploadDTO : ar) {
					uploadDTO.setNum(qnaDTO.getNum());
					num=uploadDAO.insert(uploadDTO, conn);
					if(num<1) {
						throw new Exception();
					}
				}
				
				conn.commit();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}finally {
				try {
					conn.setAutoCommit(true);
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			actionForward.setCheck(false);
			actionForward.setPath("./qnaList");
		}//end of post
		
		
	
		return actionForward;
	}

	@Override
	public ActionForward update(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionForward delete(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
