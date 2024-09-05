package project;


import java.util.ArrayList;

public interface MemDAO {
	
	public int memInsert(MemVO mvo);
	public ArrayList<MemVO> memSelectAll();
	public int memUpdate(MemVO mvo);
	public int memDelete(MemVO mvo);
	public ArrayList<MemVO> memSelect(MemVO mvo);
	
	// 아이디, 이메일 조건 검색 추가 
	public ArrayList<MemVO> getKidSelect(MemVO mvo);
	public ArrayList<MemVO> getKemailSelect(MemVO mvo);
}
