package ADTPriorityQueue;

import java.util.Date;

public class FinalExam {

	private String subjectName;
	private Integer score;
	private Date date;
	
	public FinalExam(String sN, int score, Date date) {
		subjectName= sN;
		this.score= score;
		this.date= date;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public Integer getScore() {
		return score;
	}

	public Date getDate() {
		return date;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
