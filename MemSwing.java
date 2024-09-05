package project;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;

import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MemSwing extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	// 멤버변수
	private JLabel      la1, la2, la3;
	private JTextField  tf1, tf2, tf3;
	private JButton     bt1, bt2, bt3, bt4;
	private JPanel      pa1, pa2;
	private JButton     btClose;

	// 생성자 
	public MemSwing() {
	
		// JFrame 타이틀 세팅하기
		this.setTitle("SwingMember");
		// JFrame 레이아웃 매니저 보더 레이아웃으로 설정하기 
		
		this.setLayout(new BorderLayout());
		
		// 라벨, 텍스트필드 붙일 JPanel 인스턴스 
		pa1 = new JPanel();
		// JPanel 바탕색 칠하기 시안으로 
	
		// 버튼 붙일 JPanel 인스턴스
		pa2 = new JPanel();
		// JPanel 바탕색 칠하기 블루로		

		

		// 라벨, 텍스트필드 붙일 JPanel 레이아웃 매니저 그리드 레이아웃
		pa1.setLayout(new GridLayout(5,2));
		// 라벨 인스턴스 
		la1 = new JLabel(" 닉네임 ", JLabel.CENTER);	
		la2 = new JLabel(" 아이디 ", JLabel.CENTER);	
		la3 = new JLabel(" 패스워드 ", JLabel.CENTER);	


		// 텍스트필드 인스턴스
		tf1 = new JTextField(10);
		tf2 = new JTextField(10);
		tf3 = new JTextField(10);

		
		// 라벨, 텍스트필드 붙일 JPanel에 라벨, 텍스트 필드 붙이기
		pa1.add(la1);
		pa1.add(tf1);
		pa1.add(la2);
		pa1.add(tf2);
		pa1.add(la3);
		pa1.add(tf3);

		// 버튼 붙일 JPanel 레이아웃 매니저 플로우 레이아웃
		pa2.setLayout(new FlowLayout(FlowLayout.CENTER));
		// 버튼 인스턴스 
		bt1 = new JButton("조회");
		bt2 = new JButton("저장");
		bt3 = new JButton("삭제");
		bt4 = new JButton("초기화");
		btClose = new JButton("프로그램종료");
		
		// 버튼 붙일 JPanel에 버튼 필드 붙이기
		pa2.add(bt1);
		pa2.add(bt2);
		pa2.add(bt3);
		pa2.add(bt4);
		pa2.add(btClose);
				

		
		// 조회, 등록, 수정, 삭제 버튼에 이벤트 전달
		bt1.addActionListener(this);
		bt2.addActionListener(this);
		bt3.addActionListener(this);
		bt4.addActionListener(this);
		btClose.addActionListener(this);
		
		// JPanel 두장 JFrame 붙이기 
		this.getContentPane().add(pa1, BorderLayout.CENTER);
		this.getContentPane().add(pa2, BorderLayout.SOUTH);

		this.setSize(450, 400);
		this.setLocation(200, 200);
		setLocationRelativeTo(null);
		this.setVisible(true);

		// JFrame 클로우즈 
		this.addWindowListener(new WindowAdapter() { 
			public void windowClosing(WindowEvent e) { 
				e.getWindow().setVisible(false);
				e.getWindow().dispose();
//				System.exit(0);
			}
		});
	}
	
	// 조건 조회 
	public void memSelect(String hnum) {
		System.out.println("MemSwing :: memSelect() 시작 >>> : ");
		
		try {
			
			MemDAO mdao = new MemDAOImpl();
			MemVO mvo = null;
			mvo = new MemVO();
			mvo.setKnum(hnum);
			
			// 화면 텍스트필드 클리어 
			jtextFileClear();
			
			ArrayList<MemVO> aList = mdao.memSelect(mvo);
			
			if (aList !=null && aList.size() > 0) {
				
				for (int i=0; i < aList.size(); i++) {
					
					MemVO _mvo = aList.get(i);
					
					tf1.setText(_mvo.getKnum());					
					tf2.setText(_mvo.getKid());
					tf3.setText(_mvo.getKpw());
				}		
			}else {
				
			}
		}catch(Exception ex) {
			System.out.println("조건 중 에러가 >>> : " + ex.getMessage());
		}	
	}
	
	// 등록
	public void memInsert(String hnum, String hid, String hpw) {
		System.out.println("MemSwing :: memInsert() 시작 >>> : ");
		
		try {
			
			MemDAO mdao = new MemDAOImpl();
			MemVO mvo = null;
			mvo = new MemVO();
			mvo.setKnum(hnum);
			mvo.setKid(hid);
			mvo.setKpw(hpw);
			
			
			// 화면 텍스트필드 클리어 
			jtextFileClear();
			
			int nCnt = mdao.memInsert(mvo);
			
			if (nCnt >= 1) {
				System.out.println("회원 정보 입력 성공 >>> : " + nCnt);
				
				JOptionPane.showMessageDialog(this, "회원정보 입력 성공 >>> :  ");
				
				this.memSelect(hnum);
			}else {
				System.out.println("회원 정보 입력 실패 >>> : " + nCnt);
				JOptionPane.showMessageDialog(this, "회원정보 입력 실패 >>> :  ");
			}						
		}catch(Exception ex) {
			System.out.println("등록 중 에러가 >>> : " + ex.getMessage());
		}		
	}

	// 수정 
	public void memUpdate(String hnum) {
		System.out.println("MemSwing :: memUpdate() 시작 >>> : ");
		
		try {
			

			MemVO mvo = null;
			mvo = new MemVO();
			mvo.setKnum(hnum);		
			System.out.println("mvo.getHnum() >>> : " + mvo.getKnum());
			
			MemDAO mdao = new MemDAOImpl();
			int nCnt = mdao.memUpdate(mvo);
			
			// 화면 텍스트필드 클리어 
			jtextFileClear();
			
			if (nCnt > 0) {
				System.out.println("회원 정보 수정 성공 >>> : " + nCnt);
				
				JOptionPane.showMessageDialog(this, "회원정보 수정 성공 >>> :  ");
				
				this.memSelect(hnum);
			}else {
				System.out.println("회원 정보 수정 실패 >>> : " + nCnt);
				JOptionPane.showMessageDialog(this, "회원정보 수정 성공 >>> :  ");
			}		
		}catch(Exception ex) {
			System.out.println("수정 중 에러가 >>> : " + ex.getMessage());
		}					
	}

	// 삭제 
	public void memDelete(String hid) {
		System.out.println("MemSwing :: memDelete() 시작 >>> : ");
		
		try {
			
			MemVO mvo = null;
			mvo = new MemVO();
			mvo.setKnum(hid);		
			
			System.out.println("mvo.getHnum() >>> : " + mvo.getKid());			
			
			MemDAO mdao = new MemDAOImpl();
			int nCnt = mdao.memDelete(mvo);
			
			// 화면 텍스트필드 클리어 
			jtextFileClear();
			
			if (nCnt > 0) {
				System.out.println("회원 정보 삭제 성공 >>> : " + nCnt);	
				JOptionPane.showMessageDialog(this, "회원정보 삭제 성공 >>> :  ");
			}else {
				System.out.println("회원 정보 삭제 실패 >>> : " + nCnt);
				JOptionPane.showMessageDialog(this, "회원정보 실패 성공 >>> :  ");
			}				
			
		}catch(Exception ex) {
			System.out.println("삭제 중 에러가 >>> : " + ex.getMessage());
		}			
	}


//	2. 이벤트 리스너 클래스를 등록한다.	
//	현재 객체 : 상속하는 경우	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		String btnCmd = String.valueOf(e.getActionCommand());
		
		if ("조회".equals(btnCmd)) {
			System.out.println("btnCmd >>> : " + btnCmd + " 시작 >>> : ");
			
			String hnum = "";
			
			try {	
				
				hnum = tf1.getText();				
				System.out.println("닉네임 >>> : " + hnum);
				
				// 조건 조회 함수 호출 
				this.memSelect(hnum);	
				
			}catch(Exception ex) {
				System.out.println("에러가 >>> : " + ex.getMessage());
			}
		}
		if ("저장".equals(btnCmd)) {
			System.out.println("btnCmd >>> : " + btnCmd + " 시작 >>> : ");	
							
			String hnum = "";			
			String hid = "";
			String hpw = "";
			
			try {

				hnum = tf1.getText();
				hid = tf2.getText();
				hpw = tf3.getText();
				System.out.println("hnum >>> : " + hnum);
				System.out.println("hid >>> : " + hid);
				System.out.println("hpw >>> : " + hpw);
						
				// 등록 함수 호출 
				this.memInsert(hnum, hid, hpw);
				dispose();
				
			}catch(Exception ex) {
				System.out.println("에러가 >>> : " + ex.getMessage());
			}			
		}
		if ("수정".equals(btnCmd)) {
			System.out.println("btnCmd >>> : " + btnCmd + " 시작 >>> : ");
			
			// 이메일만 수정하기 				
			String hnum = "";
	
			
			try {
				
				hnum = tf1.getText();
				
				System.out.println("hnum >>> : " + hnum);
			
					
				// 수정 함수 호출 
				this.memUpdate(hnum);	
				
			}catch(Exception ex) {
				System.out.println("에러가 >>> : " + ex.getMessage());
			}
			
		}
		if ("삭제".equals(btnCmd)) {
			System.out.println("btnCmd >>> : " + btnCmd + " 시작 >>> : ");
			
			String hnum = "";
			
			try {
				
				hnum = tf1.getText();				
				System.out.println("hnum >>> : " + hnum);
					
				// 삭제 함수 호출
				this.memDelete(hnum);	
				
			}catch(Exception ex) {
				System.out.println("에러가 >>> : " + ex.getMessage());
			}
		}			
		if ("초기화".equals(btnCmd)) {
			System.out.println("btnCmd >>> : " + btnCmd + " 시작 >>> : ");
			
			try {
				// 초기화 함수 호출
				this.jtextFileClear();	
				
			}catch(Exception ex) {
				System.out.println("에러가 >>> : " + ex.getMessage());
			}
		}
		if ("프로그램종료".equals(btnCmd)) {
			System.out.println("btnCmd >>> : " + btnCmd + " 시작 >>> : ");
			
			try {
				dispose();			
			}catch(Exception ex) {
				System.out.println("에러가 >>> : " + ex.getMessage());
			}
		}
	}
	
	public void jtextFileClear() {
		System.out.println("MemSwing :: jtextFileClear() 시작 >>> : ");
		
		tf1.setText("");
		tf2.setText("");
		tf3.setText("");
		
	}
	
	public static void main(String args[]){
		// TODO Auto-generated method stub
		System.out.println("MemSwing :: main() 시작 >>> : ");
		
		new MemSwing();		
	}
}

	