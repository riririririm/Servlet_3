package com.rim.point;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rim.action.ActionForward;

public class PointService {
	private PointDAO pointDAO;
	
	public PointService() {
		pointDAO = new PointDAO();
		
	}
	public ActionForward selectList(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		
		return actionForward;
	}
	
	public ActionForward selectOne(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		
		return actionForward;
	}
}
