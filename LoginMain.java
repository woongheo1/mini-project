package project;

import java.util.ArrayList;
import java.util.Scanner;

public class LoginMain {

	// 로그인 함수
	public String loginCheck(String kid, String kpw) {
		System.out.println("LoginMain.loginCheck() 함수 진입 성공 >>> : ");
		System.out.println("hid >>> : " 		+ kid);
		System.out.println("hpw >>> : " 		+ kpw);
		
		MemVO mvo = null;
		mvo = new MemVO();
		mvo.setKid(kid);
		mvo.setKpw(kpw);
		
		LoginDAO ldao = new LoginDAOImpl();;
		ArrayList<MemVO> aList = ldao.loginCheck(mvo);
		
		String loginMsg = "";		
		
		if (aList !=null && aList.size() == 1) {
			
			loginMsg = "SUCCESS";
			
		}else if(aList.size() > 1) {
			
			loginMsg = "FAIL_1";
			
		}else {
			
			loginMsg = "FAIL_2";
		}
		
		return loginMsg;
	}
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("로그인 정보를 입력하시오 >>> : ");
		Scanner sc = new Scanner(System.in);
		
		System.out.println("닉네임 입력하시오 >>> : ");
		String kid = sc.next();		
		System.out.println("비밀번호 입력하시오 >>> : ");
		String kpw = sc.next();
		
		LoginMain lm = new LoginMain();		
		String lmsg = lm.loginCheck(kid, kpw);
		
		if ("SUCCESS".equals(lmsg.toUpperCase())) {			
			System.out.println("로그인 성공 >>> : ");
			
		}else {
			System.out.println("로그인 실패 >>> : ");
		}
		
		sc.close();
	}

}
