package StudentTableMgr;

public class StudentData {
	
	private int studentId;
	private String name;
	private String gender;
	private int kor;
	private int eng;
	private int math;
	private int sci;
	
	public StudentData(int studentId, String name, String gender, int kor, int eng, int math, int sci) {
		this.studentId = studentId;
		this.name = name;
		this.gender = gender;
		this.kor = kor;
		this.eng = eng;
		this.math = math;
		this.sci = sci;
	}
	
	public int getStudentId() {
		return this.studentId;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getGender() {
		return this.gender;
	}
	
	public int getKor() {
		return this.kor;
	}
	
	public int getEng() {
		return this.eng;
	}
	
	public int getMath() {
		return this.math;
	}
	
	public int getSci() {
		return this.sci;
	}
	
	public String toString() {
		String result = "학번:\t  " + this.getStudentId() + "\n"
							+ "이름:\t  " + this.getName() + "\n"
							+ "성별:\t  " + this.getGender() + "\n"
							+"-------------------------\n"
							+ "|  국어    영어   수학    과학  |" + "\n"
							+ "||-----||-----||-----||-----||\n"
							+ "|   " + this.getKor() + "      " + this.getEng() + "      "
							+ this.getMath() + "     " + this.getSci() + "  |\n"
							+ "-------------------------";
							
		
		return result;
	}

}
