//checked
package model.persoon;

public class Student extends Persoon {

	private int studentNummer;
	private String groepId;
	private boolean ziek;

	public Student(
		String pVoornaam, 
		String pTussenvoegsel, 
		String pAchternaam, 
		String pWachtwoord, 
		String pGebruikersnaam,
		int pStudentNummer) {
		super(
			pVoornaam, 
			pTussenvoegsel, 
			pAchternaam, 
			pWachtwoord, 
			pGebruikersnaam);
		this.setStudentNummer(pStudentNummer);
		this.setGroepId("");
		this.ziek = false;
	}


 public String getGroepId() {
    return this.groepId;	
  }
 
  public void setGroepId(String pGroepId) {
    this.groepId= pGroepId;	
  }
 
	public int getStudentNummer() {
		return this.studentNummer;
	}

	private void setStudentNummer(int pStudentNummer) {
		this.studentNummer = pStudentNummer;
	}
	
	public boolean equals(Object andereObj) {
		boolean gelijkeObjecten = false;
		if (andereObj instanceof Student) {
			Student andereStudent = (Student) andereObj;
			if (this.groepId.equals(andereStudent.groepId) &&
					this.studentNummer == andereStudent.studentNummer &&
					this.getGebruikersnaam().equals(andereStudent.getGebruikersnaam()) &&
					this.getVoornaam().equals(andereStudent.getVoornaam()) &&
					this.getVolledigeAchternaam().equals(andereStudent.getVolledigeAchternaam())) {
				gelijkeObjecten = true;
			}
		}
		return gelijkeObjecten;
	}
	
}
