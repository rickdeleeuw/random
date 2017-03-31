package model.vak;

public class Vak {
	private String vakCode;
	private String naam;
	
	public Vak(String vakCode, String naam){
		this.vakCode = vakCode;
		this.naam = naam;
	}

	public String getVakCode() {
		return vakCode;
	}

	public void setVakCode(String vakCode) {
		this.vakCode = vakCode;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}
	
	public boolean equals(Object andereObj) {
		boolean gelijkeObjecten = false;
		if (andereObj instanceof Vak) {
			Vak andereVak = (Vak) andereObj;
			if (this.vakCode.equals(andereVak.vakCode) &&
					this.naam.equals(andereVak.naam)) {
				gelijkeObjecten = true;
			}
		}
		return gelijkeObjecten;
	}
}
