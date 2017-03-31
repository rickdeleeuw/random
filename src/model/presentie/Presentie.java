package model.presentie;

import model.les.Les;
import model.persoon.Student;

public class Presentie {
	private Les deLes;
	private Student deStudent;
	private boolean aanwezig;
	
	public Presentie(Les deLes, Student deStudent) {
		this.deLes = deLes;
		this.deStudent = deStudent;
		this.aanwezig = true;
	}
	
	public Presentie(Les deLes, Student deStudent, boolean aanwezig) {
		this(deLes, deStudent);
		this.aanwezig = aanwezig;
	}

	public Les getDeLes() {
		return deLes;
	}

	public void setDeLes(Les deLes) {
		this.deLes = deLes;
	}

	public Student getDeStudent() {
		return deStudent;
	}

	public void setDeStudent(Student deStudent) {
		this.deStudent = deStudent;
	}

	public boolean isAanwezig() {
		return aanwezig;
	}

	public void setAanwezig(boolean aanwezig) {
		this.aanwezig = aanwezig;
	}
}
