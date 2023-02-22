package StudentTableMgr;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentProgramMain {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		StudentCrud student = new StudentCrud();
		ArrayList<StudentData> studentDataRows;
		Exceptions exception = new Exceptions();
		int choiceMenu;
		int id;
		
		String typeErrorMsg = "※ 올바른 정수 값을 입력해 주세요.\n";
		String columnHeading = "STDNO\t\t SNAME\t\t SG\t\t KOR\t\t ENG\t\t MATH\tSCI";
		String singleLine = "---------- ---------------- ---------- ---------- ---------- ---------- ----------";
		
		System.out.println("### 학생 성적 관리 프로그램 ###");
		while(true) {
			System.out.print("1. 학생 정보 입력\n"
									+ "2. 학생 정보 조회\n"
									+ "3. 성적 변경\n"
									+ "4. 학생 정보 삭제\n"
									+ "5. 프로그램 종료\n"
									+ ">>>");
			if(!sc.hasNextInt()) {
				System.out.print(typeErrorMsg);
				sc.next();
				continue;
			}
			
			choiceMenu = sc.nextInt();
			if(choiceMenu == 5) {
				System.out.println("프로그램을 종료합니다.");
				break;
			}
			
			switch(choiceMenu) {
			case 1:
				int rowCount = 0;
				System.out.println("\n<1. 학생 정보 입력>\n"
											+ "학번, 이름, 성별(M/W), 성적(국/영/수/과) 순서로 입력해 주세요>> ");
				try {
					id = sc.nextInt();
					exception.stdnoIdException(id);
					String sname = sc.next();
					exception.stdNameException(sname);
					rowCount = student.insertStudentData(id, sname, sc.next(), 
																sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
					if(!(student.showOneStudentData(id) == null)) {
						System.out.print(student.showOneStudentData(id) + "\n"
								+ "위 학생의 정보가 맞습니까? (1.예 / 2.아니요)\n"
								+ ">>");
					}
					else {
						System.out.println("학생 정보를 잘못 입력하셨습니다.");
						break;
					}
					
					while(true) {
						if(sc.hasNextInt()) {
							choiceMenu = sc.nextInt();
							System.out.println(student.commitOrRollback(choiceMenu));
							
							if(choiceMenu == 1 || choiceMenu == 2) {
								if(choiceMenu == 2) rowCount = 0;
								System.out.println(rowCount + "개의 행이 입력 되었습니다.\n");
								break;
							}
						}
						else {
							System.out.print(typeErrorMsg);
							sc.next();
						}
					}
				}
				catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}
				catch (InputMismatchException e) {
					System.out.print(typeErrorMsg);
				}
				catch (Exception e) {
					System.out.println("학생 정보를 잘못 입력하였습니다.");
				}
				finally {
					sc.nextLine();
				}
				break;
				
			case 2:
				System.out.print("\n<2. 학생 정보 조회>\n"
										+ "1. 학번 조회\n"
										+ "2. 전체 조회\n"
										+ ">>>");
				while(!sc.hasNextInt()) {
					System.out.print(typeErrorMsg);
					sc.next();
				}
				
				do {
					try {
						choiceMenu = sc.nextInt();
						if(choiceMenu == 1) {
							System.out.print("학번을 입력하세요>> ");
							
							id = sc.nextInt();
							exception.stdnoIdException(id);
							if (student.showOneStudentData(id) == null) {
								System.out.println("※ 학생 정보가 없습니다.\n");
								break;
							} 
							else {
								System.out.println(student.showOneStudentData(id));
							}
						}
						else if(choiceMenu == 2) {
							System.out.println(columnHeading);
							System.out.println(singleLine);
							studentDataRows = student.showAllStudentData();
							for(StudentData row : studentDataRows) {
								System.out.printf("%d\t\t %s\t\t %s\t\t %d\t\t %d\t\t %d\t\t %d\n"
										,row.getStudentId(), row.getName(), row.getGender()
										,row.getKor(), row.getEng(), row.getMath(), row.getSci());
							}
							break;
						}
						else {
							System.out.println("1번과 2번 둘 중 하나의 정수를 입력하세요.");
						}
					}
					catch (IllegalArgumentException e) {
						System.out.println(e.getMessage());
					}
					catch (InputMismatchException e) {
						System.out.println(typeErrorMsg);
					}
					finally {
						sc.nextLine();
					}
				} while(choiceMenu < 1 || choiceMenu > 2);
				
				break;
				
			case 3:
				System.out.print("\n<3. 성적 변경>\n"
										+ "성적 변경할 학생의 학번을 입력하세요\n"
										+ ">>");
				while(!sc.hasNextInt()) {
					System.out.print(typeErrorMsg);
					sc.next();
				}
				
				try {
					id = sc.nextInt();
					exception.stdnoIdException(id);
					if(!(student.showOneStudentData(id) == null)) {
						System.out.print(student.showOneStudentData(id) + "\n"
								+ "위 학생의 정보가 맞습니까? (1.예 / 2.아니요)\n"
								+ ">>");
						while(!sc.hasNextInt()) {
							System.out.print(typeErrorMsg);
							sc.next();
						}
						do {
							choiceMenu = sc.nextInt();
							if(choiceMenu == 2) {
								System.out.println("초기 화면으로 돌아갑니다.");
								break;
							}
							else if(choiceMenu != 1) {
								System.out.print("※ 1번과 2번 둘 중 하나만 입력하세요\n"
															+ ">>");
							}
						} while(choiceMenu != 1);
					}
					else {
						System.out.println("해당 학생의 정보가 없습니다.");
					}
					
					if(choiceMenu == 1) {
						System.out.print("변경할 과목 번호를 입력하세요\n"
													+ "1.국어\n"
													+ "2.영어\n"
													+ "3.수학\n"
													+ "4.과학\n"
													+ ">>");
						while(true) {
							if(!sc.hasNextInt()) {
								System.out.print(typeErrorMsg);
								sc.next();
								continue;
							}

							choiceMenu = sc.nextInt();
							if(choiceMenu < 1 || choiceMenu > 4) {
								System.out.print("1~4 사이의 숫자를 입력하세요>>");
								continue;
							}
							break;
						}
						
						System.out.print("변경할 점수를 입력하세요>>");
						while(!sc.hasNextInt()) {
							System.out.print(typeErrorMsg);
							sc.next();
						}
						int score = sc.nextInt();
						try {
							student.updateStudentScore(choiceMenu, score, id);
							student.conn.commit();
							System.out.println("점수가 변경 되었어요~");
						} 
						catch (SQLException e) {
							System.out.println("0~100 사이의 값을 입력하세요.");
						}
					}
				}
				catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}
				catch (InputMismatchException e) {
					System.out.print(typeErrorMsg);
				}
				finally {
					sc.nextLine();
				}
				break;
				
			case 4:
				System.out.print("\n<4. 학생 정보 삭제>\n"
								+ "삭제할 정보의 학번을 입력하세요\n"
								+ ">>");
				while(!sc.hasNextInt()) {
					System.out.print(typeErrorMsg);
					sc.next();
				}
				
				try {
					id = sc.nextInt();
					exception.stdnoIdException(id);
					if(!(student.showOneStudentData(id) == null)) {
						System.out.print(student.showOneStudentData(id) + "\n"
								+ "위 학생의 정보가 맞습니까? (1.예 / 2.아니요)\n"
								+ ">>");
						rowCount = student.deleteStudentData(id);
						while(true) {
							if(sc.hasNextInt()) {
								choiceMenu = sc.nextInt();
								System.out.println(student.commitOrRollback(choiceMenu));
								
								if(choiceMenu == 1 || choiceMenu == 2) {
									if(choiceMenu == 2) rowCount = 0;
									System.out.println(rowCount + "개의 행이 삭제 되었습니다.\n");
									break;
								}
							}
							else {
								System.out.print(typeErrorMsg);
								sc.next();
							}
						}
					}
					else {
						System.out.println("해당 학생의 정보가 없습니다.");
					}
				}
				catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}
				catch (InputMismatchException e) {
					System.out.print(typeErrorMsg);
				}
				finally {
					sc.nextLine();
				}
				break;
			}

			System.out.println();
		}
	}
}
