package com.rim.point;

public class PointDTO {
	//멤버변수명 == 테이블의 컬럼명 == 파라미터 이름
	private int idx;
	private String p_name;
	private int kor;
	private int eng;
	private int math;
	private int total;
	private double p_avg;
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
	public int getKor() {
		return kor;
	}
	public void setKor(int kor) {
		this.kor = kor;
	}
	public int getEng() {
		return eng;
	}
	public void setEng(int eng) {
		this.eng = eng;
	}
	public int getMath() {
		return math;
	}
	public void setMath(int math) {
		this.math = math;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public double getP_avg() {
		return p_avg;
	}
	public void setP_avg(double p_avg) {
		this.p_avg = p_avg;
	}
	
	
	
}
