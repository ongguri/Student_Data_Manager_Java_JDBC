package StudentTableMgr;

import java.sql.SQLException;
import java.util.ArrayList;

public class StudentCrud extends ConnectionJdbc {
	
	StudentCrud() {
		super();
	}
	
	public int insertStudentData(int stdno, String sname, String sg, int kor, int eng, int math, int sci) {
		int rowCount = 0;
		this.sql = "INSERT INTO student VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		try {
			this.pstmt = conn.prepareStatement(this.sql);
			this.pstmt.setInt(1, stdno);
			this.pstmt.setString(2, sname);
			this.pstmt.setString(3, sg);
			this.pstmt.setInt(4, kor);
			this.pstmt.setInt(5, eng);
			this.pstmt.setInt(6, math);
			this.pstmt.setInt(7, sci);
			rowCount = this.pstmt.executeUpdate();
		} 
		catch (SQLException e) {
			e.getMessage();
		}
		finally {
			this.closePreparedStatement();
		}
		
		return rowCount;
	}
	
	public int deleteStudentData(int stdno) {
		int rowCount = 0;
		
		this.sql = "DELETE student WHERE stdno = ?";
		
		try {
			this.pstmt = conn.prepareStatement(this.sql);
			this.pstmt.setInt(1, stdno);
			rowCount = this.pstmt.executeUpdate();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.closePreparedStatement();
		}
		
		return rowCount;
	}
	
	public int updateScore(int score, int stdno) {
		int rowCount = 0;
		try {
			this.pstmt = conn.prepareStatement(this.sql);
			this.pstmt.setInt(1, score);
			this.pstmt.setInt(2, stdno);
			rowCount = this.pstmt.executeUpdate();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.closePreparedStatement();
		}
		return rowCount;
	}
	
	public int updateStudentScore(int subject, int score, int stdno) {
		int rowCount = 0;
		
		if(subject == 1) {
			this.sql = "UPDATE student SET kor = ? WHERE stdno = ?";
		}
		else if(subject == 2) {
			this.sql = "UPDATE student SET eng = ? WHERE stdno = ?";
		}
		else if(subject == 3) {
			this.sql = "UPDATE student SET math = ? WHERE stdno = ?";
		}
		else if(subject == 4) {
			this.sql = "UPDATE student SET sci = ? WHERE stdno = ?";
		}
		rowCount = this.updateScore(score, stdno);
		
		return rowCount;
	}
	
	public ArrayList<StudentData> showAllStudentData() {
		ArrayList<StudentData> studentDataList = new ArrayList<StudentData>();
		this.sql = "SELECT * FROM student";
		
		try {
			this.pstmt = conn.prepareStatement(this.sql);
			this.rs = this.pstmt.executeQuery();
			
			while(this.rs.next()) {
				studentDataList.add(this.addNewStudentInstance());
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.closePreparedStatement();
		}
		
		return studentDataList;
	}
	
	public StudentData showOneStudentData(int stdno) {
		this.sql = "SELECT * FROM student WHERE stdno = ?";
		StudentData oneStudentData = null;
		
		try {
			this.pstmt = conn.prepareStatement(this.sql);
			this.pstmt.setInt(1, stdno);
			this.rs = this.pstmt.executeQuery();	
			
			if(this.rs.next()) {
				oneStudentData = this.addNewStudentInstance();
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.closePreparedStatement();
		}
		return oneStudentData;
	}
	
	public StudentData addNewStudentInstance() {
		StudentData dataInstance = null;
		try {
			dataInstance = new StudentData(this.rs.getInt("stdno"), this.rs.getString("sname")
					, this.rs.getString("sgender"), this.rs.getInt("kor"), this.rs.getInt("eng")
					, this.rs.getInt("math"), this.rs.getInt("sci"));
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return dataInstance;
	}
	
	public String commitOrRollback(int choiceMenu) {
		String result = "";

		if(choiceMenu < 1 || choiceMenu > 2) {
			result = "1번과 2번 둘 중 하나만 입력하세요.";
		}
		else if(choiceMenu == 1) {
			try {
				this.conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if(choiceMenu == 2) {
			try {
				this.conn.rollback();
				result = "정보 업데이트를 취소합니다.";
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public void closeStatement() {
		try {
			if(!this.stmt.isClosed()) {
				this.stmt.close();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void closePreparedStatement() {
		try {
			if(!this.pstmt.isClosed()) {
				this.pstmt.close();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void closeConnection() {
		try {
			if(!this.conn.isClosed()) {
				this.conn.close();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
