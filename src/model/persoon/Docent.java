package model.persoon;

public class Docent extends Persoon {

	private int docentNummer;
	
	public Docent(String voornaam, String tussenvoegsel, String achternaam, String wachtwoord, String gebruikersnaam, int docentNummer) {
		super(voornaam, tussenvoegsel, achternaam, wachtwoord, gebruikersnaam);
		this.docentNummer = docentNummer;
	}

	public int getDocentNummer() {
		return docentNummer;
	}

	public void setDocentNummer(int docentNummer) {
		this.docentNummer = docentNummer;
	}
	
	public boolean equals(Object andereObj) {
		boolean gelijkeObjecten = false;
		if (andereObj instanceof Docent) {
			Docent andereDocent = (Docent) andereObj;
			if (this.docentNummer == andereDocent.docentNummer) {
				gelijkeObjecten = true;
			}
		}
		return gelijkeObjecten;
	}

}
