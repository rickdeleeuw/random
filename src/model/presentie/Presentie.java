package model.presentie;

import model.les.Les;
import model.persoon.Student;

public class Presentie {
	private Les deLes;
	private Student deStudent;
	private boolean aanwezig;
	private String reden;
	
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

	public boolean getAanwezig() {
		return aanwezig;
	}

	public void setAanwezig(boolean aanwezig) {
		this.aanwezig = aanwezig;
	}
	
	public String getReden() {
		return reden;
	}

	public void setReden(String reden) {
		this.reden = reden;
	}

	public boolean equals(Object andereObj) {
		boolean gelijkeObjecten = false;
		if (andereObj instanceof Presentie) {
			Presentie anderePresentie = (Presentie) andereObj;
			if (this.deLes.equals(anderePresentie.deLes) &&
					this.deStudent.equals(anderePresentie.deStudent) &&
					this.aanwezig == anderePresentie.aanwezig) {
				gelijkeObjecten = true;
			}
		}
		return gelijkeObjecten;
	}
}
