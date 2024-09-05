package project;

import java.util.ArrayList;
import java.util.Scanner;

public class MemMain {

	public int memInsert(String knum, String kid, String kpw, String kemail) {
		System.out.println("MemMain.memInsert() 함수 진입 성공 >>> : ");
		
		System.out.println("knum >>> ; " + knum);
		System.out.println("kid >>> ; " + kid);
		System.out.println("kpw >>> ; " + kpw);
		System.out.println("knemail >>> ; " + kemail);

		//객체를 생성하여 데이터 베이스에 저장할 데이터를 담는다
		MemVO mvo = null;
		mvo = new MemVO();
		mvo.setKnum(knum);
		mvo.setKid(kid);
		mvo.setKpw(kpw);
		mvo.setKemail(kemail);
		
		System.out.println("mvo.getKnum() >>> ; " + mvo.getKnum());
		System.out.println("mvo.getKid() >>> ; " + mvo.getKid());
		System.out.println("mvo.getKpw() >>> ; " + mvo.getKpw());
		System.out.println("mvo.getKemail() >>> ; " + mvo.getKemail());

		MemDAO mdao = new MemDAOImpl();
		int nCnt = mdao.memInsert(mvo);
		
		return nCnt;
	}
	
	//전체조회 함수
	public ArrayList<MemVO> memSelectAll(){
		System.out.println("MemMain.memSelectAll() 함수 진입 성공 >>> : ");	
		
		MemDAO mdao = new MemDAOImpl();
		ArrayList<MemVO> aList = mdao.memSelectAll();
		
		return aList;
	}
	
	public int memUpdate(String knum, String kemail) {
		System.out.println("MemMain.memUpdate() 함수 진입 성공 >>> : ");
		
		System.out.println("knum >>> : " + knum);
		System.out.println("kemail >>> : " + kemail);
		
		MemVO mvo = null;
		mvo = new MemVO();
		mvo.setKnum(knum);
		mvo.setKemail(kemail);
		System.out.println("mvo.getKnum() >>> : " + mvo.getKnum());
		System.out.println("mvo.getemail() >>> : " + mvo.getKemail());
		
		MemDAO mdao = new MemDAOImpl();
		int nCnt = mdao.memUpdate(mvo);
		
		return nCnt;
		
	}
	
	public int memDelete(String knum) {
		System.out.println("MemMain.memDelete() 함수 진입 성공 >>> : ");
		
		System.out.println("knum >>> : " + knum);			
		
		MemVO mvo = null;
		mvo = new MemVO();
		mvo.setKnum(knum);		
		
		System.out.println("mvo.getKnum() >>> : " + mvo.getKnum());			
		
		MemDAO mdao = new MemDAOImpl();
		int nCnt = mdao.memDelete(mvo);
		
		return nCnt;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("MemberMain.main() 함수 진입 성공 >>> : ");
		
		// Scanner 클래스를 이용해서 입력테이를 받고
		System.out.println("ISUD TYPE을 입력하시오 >>> : ");
		Scanner sc = new Scanner(System.in);
		String isudType = sc.next();
		isudType = isudType.toUpperCase();
		
		//입력
		if ("I".equals(isudType)) {
			
			String knum = "";
			String kid = "";
			String kpw = "";
			String kemail = "";
			
			System.out.println("데이터를 입력하시오");
			Scanner sc_i = new Scanner(System.in);
			System.out.println("회원번호를 입력하시오 >>> : ");
			knum = sc_i.next();
			System.out.println("아이디를 입력하시오 >>> : ");
			kid = sc_i.next();
			System.out.println("비밀번호를 입력하시오 >>> : ");
			kpw = sc_i.next();
			System.out.println("이메일를 입력하시오 >>> : ");
			kemail = sc_i.next();
			
			MemMain mm_i = new MemMain();
			int nCnt = mm_i.memInsert(knum, kid, kpw, kemail);
			
			if (nCnt >= 1) {
				System.out.println("입력 성공 >>> : " + nCnt);
			}else {
				System.out.println("입력 실패 >>> : " + nCnt);
			}
			sc_i.close();
		}
		
		//전체 조회
		if ("SALL".equals(isudType)) {
			
			MemMain mm_sall = new MemMain();
			ArrayList<MemVO> aList = mm_sall.memSelectAll();
			
			if (aList != null) {
				for (int i=0; i < aList.size(); i++) {
					MemVO mvo = aList.get(i);
					System.out.print(mvo.getKnum() + " ");
					System.out.print(mvo.getKid() + " ");
					System.out.print(mvo.getKpw() + " ");
					System.out.print(mvo.getKemail() + " ");
					System.out.print(mvo.getDeleteyn() + " ");
					System.out.print(mvo.getInsertdate() + " ");
					System.out.println(mvo.getUpdatedate());
					
				}
			}
		}
		
		//수정
		if ("U".equals(isudType)) {
			
			String knum = "";
			String kemail = "";
			
			System.out.println("데이터를 입력하시오");
			Scanner sc_u = new Scanner(System.in);
			System.out.println("회원번호를 입력하시오 >>> : ");
			knum = sc_u.next();			
			System.out.println("이메일을 입력하시오 >>> : ");
			kemail = sc_u.next();
						
			MemMain mm_u = new MemMain();
			int nCnt = mm_u.memUpdate(knum, kemail);
			
			if (nCnt >= 1) {
				System.out.println("수정 성공 >>> : " + nCnt);
			}else {
				System.out.println("수정 실패 >>> : " + nCnt);
			}
			
			sc_u.close();		
		}
		
		// 삭제
		if ("D".equals(isudType)) {
					
			String knum = "";						
					
			System.out.println("데이터를 입력하시오");
			Scanner sc_u = new Scanner(System.in);
			System.out.println("회원번호를 입력하시오 >>> : ");
			knum = sc_u.next();					
								
			MemMain mm_u = new MemMain();
			int nCnt = mm_u.memDelete(knum);
					
			if (nCnt >= 1) {
				System.out.println("삭제 성공 >>> : " + nCnt);
			}else {
				System.out.println("삭제 실패 >>> : " + nCnt);
			}
					
			sc_u.close();		
		}
				
		sc.close();
	}
}

