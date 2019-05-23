package com.rim.notice;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.rim.action.Action;
import com.rim.action.ActionForward;
import com.rim.page.SearchMakePage;
import com.rim.page.SearchPager;
import com.rim.page.SearchRow;
import com.rim.upload.UploadDAO;
import com.rim.upload.UploadDTO;
import com.rim.util.DBConnector;

public class NoticeService implements Action{
	private NoticeDAO noticeDAO;
	private UploadDAO uploadDAO;
	
	public NoticeService() {
		noticeDAO = new NoticeDAO();
		uploadDAO = new UploadDAO();
	}

	@Override
	public ActionForward list(HttpServletRequest request, HttpServletResponse response) {
		ActionForward  actionForward = new ActionForward();
		
		int curPage =1;
		try {
			curPage = Integer.parseInt(request.getParameter("curPage"));
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		String kind = request.getParameter("kind");
		String search = request.getParameter("search");
		
		SearchMakePage s = new SearchMakePage(curPage,kind,search);
		
		//1. row
		SearchRow searchRow = s.makeRow();
		ArrayList<NoticeDTO> ar = null;
		try {
			ar=noticeDAO.selectList(searchRow);
			
			//page
			int totalCount =noticeDAO.countTotal(searchRow);
			SearchPager searchPager = s.makePage(totalCount);
			
			request.setAttribute("pager", searchPager);
			request.setAttribute("list", ar);
			
			actionForward.setCheck(true);
			actionForward.setPath("../WEB-INF/views/notice/noticeList.jsp");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
		ActionForward actionForward = new ActionForward();
		
		//글이 있으면 출력
		//없는 글이면 메세지 출력 - 삭제되었거나 없는 글입니다 -> list페이지로
		NoticeDTO noticeDTO = null;
		UploadDTO uploadDTO = null;
		try {
			 int num = Integer.parseInt(request.getParameter("num")); 
			 noticeDTO = noticeDAO.selectOne(num);
			 uploadDTO = uploadDAO.selectOne(num);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		String path="";
		if(noticeDTO!=null) {
			request.setAttribute("dto", noticeDTO);
			request.setAttribute("upload", uploadDTO);
			path="../WEB-INF/views/notice/noticeSelect.jsp";
		}else {
			request.setAttribute("message", "No Data");
			request.setAttribute("path"	,"./noticeList");
			path="../WEB-INF/views/common/result.jsp";
		}
		
		actionForward.setCheck(true);
        actionForward.setPath(path);			
		
		return actionForward;
	}

	@Override
	public ActionForward insert(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		
		String method = request.getMethod(); //get또는 post
		boolean check = true;
		String path="../WEB-INF/views/notice/noticeWrite.jsp";
		if(method.equals("POST")) {//POST
			NoticeDTO noticeDTO = new NoticeDTO();
			
			// 1. request를 하나로 합치기 -enctype 때문에
			String saveDirectory = request.getServletContext().getRealPath("/upload");
			System.out.println(saveDirectory);
			
			int maxPostSize=1024*1024*10; //1024=1kb
			String encoding="UTF-8";
			MultipartRequest multi = null;
			
			try {
				multi = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, new DefaultFileRenamePolicy());
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//파일 저장이 됨
			
			
			//HDD에 저장된 이름
			//서버에 저장된 이름
			String fileName = multi.getFilesystemName("f1");//파일의 파라미터 이름
			//클라이언트가 저장한 이름
			String oName = multi.getOriginalFileName("f1");//파일의 파라미터 이름
			
			UploadDTO uploadDTO = new UploadDTO();
			uploadDTO.setFname(fileName);
			uploadDTO.setOname(oName);
						
			noticeDTO.setTitle(multi.getParameter("title"));
			noticeDTO.setWriter(multi.getParameter("writer"));
			noticeDTO.setContents(multi.getParameter("contents"));
			
			int result=0;
			try {
				int num= noticeDAO.getNum();
				noticeDTO.setNum(num);
				
				result =noticeDAO.insert(noticeDTO);
				
				uploadDTO.setNum(num);
				result= uploadDAO.insert(uploadDTO);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(result>0) {
				check=false;//redirect
				path="./noticeList";
			}else {
				request.setAttribute("message", "Write Fail");
				request.setAttribute("path"	,"./noticeList");
				check=true;
				path="../WEB-INF/views/common/result.jsp";
			}
		}
		
		actionForward.setCheck(check);
		actionForward.setPath(path);
		
		return actionForward;
	}

	@Override
	public ActionForward update(HttpServletRequest request, HttpServletResponse response) {
		NoticeDTO noticeDTO = (NoticeDTO)request.getAttribute("dto");
		
		noticeDTO.setTitle(request.getParameter("title"));
		noticeDTO.setContents(request.getParameter("contents"));
		
		ActionForward actionForward = new ActionForward();
		actionForward.setCheck(true);
		actionForward.setPath("../WEB-INF/views/notice/noticeUpdate.jsp"); // /notice/noticeUpdate이니까 현재 위치는 /notice >> 루트로 가려면 ../
		return actionForward;
	}

	@Override
	public ActionForward delete(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
