package ADTPriorityQueue;

import ADTList.*;

public class Student {

	private String file;
	private String name;
	private PositionList<FinalExam> finals;
	
	public Student(String file, String name) {
		this.file= file;
		this.name= name;
		finals= new DoubleLinkedList<FinalExam>();
	}

	public String getFile() {
		return file;
	}

	public String getName() {
		return name;
	}

	public PositionList<FinalExam> getFinals() {
		return finals;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFinals(PositionList<FinalExam> finals) {
		this.finals = finals;
	}
	
	
}
