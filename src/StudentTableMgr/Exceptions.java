package StudentTableMgr;

public class Exceptions {
	
	public void stdnoIdException(int stdno) {
		IllegalArgumentException stdnoLengthException = new IllegalArgumentException("※ 7자리의 학번을 입력하세요.");
	
		if(stdno / 1000000 == 0) {
			throw stdnoLengthException;
		}
	}
	
	public void stdNameException(String sname) {
		
		if(!sname.matches("^[가-힣]*$")) {
			throw new IllegalArgumentException("※ 올바른 한글 이름을 입력해주세요.");
		}
	}
	
//	public boolean typeException() {
//		boolean typeCheck = true;
//		
//		
//		return typeCheck;
//	}

}
