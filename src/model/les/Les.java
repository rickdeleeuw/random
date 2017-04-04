package model.les;

import java.time.LocalDate;
import java.time.LocalTime;
import model.klas.Klas;
import model.persoon.Docent;
import model.vak.Vak;

public class Les {
	private LocalDate datum;
	private LocalTime beginTijd;
	private LocalTime eindTijd;
	private Vak hetVak;
	private Docent deDocent;
	private String lokaal;
	private Klas deKlas;

	public Les(LocalDate datum, LocalTime beginTijd,
			LocalTime eindTijd, Vak hetVak, Docent deDocent,
			String lokaal, Klas deKlas) {
		this.datum = datum;
		this.beginTijd = beginTijd;
		this.eindTijd = eindTijd;
		this.hetVak = hetVak;
		this.deDocent = deDocent;
		this.lokaal = lokaal;
		this.deKlas = deKlas;
	}

	public LocalDate getDatum() {
		return datum;
	}
	
	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}

	public LocalTime getBeginTijd() {
		return beginTijd;
	}

	public void setBeginTijd(LocalTime beginTijd) {
		this.beginTijd = beginTijd;
	}

	public LocalTime getEindTijd() {
		return eindTijd;
	}

	public void setEindTijd(LocalTime eindTijd) {
		this.eindTijd = eindTijd;
	}

	public Vak getHetVak() {
		return hetVak;
	}

	public void setHetVak(Vak hetVak) {
		this.hetVak = hetVak;
	}

	public Docent getDeDocent() {
		return deDocent;
	}

	public void setDeDocent(Docent deDocent) {
		this.deDocent = deDocent;
	}

	public String getLokaal() {
		return lokaal;
	}

	public void setLokaal(String lokaal) {
		this.lokaal = lokaal;
	}

	public Klas getDeKlas() {
		return deKlas;
	}

	public void setDeKlas(Klas deKlas) {
		this.deKlas = deKlas;
	}
	
	public boolean equals(Object andereObj) {
		boolean gelijkeObjecten = false;
		if (andereObj instanceof Les) {
			Les andereLes = (Les) andereObj;
			if (this.beginTijd.equals(andereLes.beginTijd) &&
					this.eindTijd.equals(andereLes.eindTijd) &&
					this.datum.equals(andereLes.datum) &&
					this.deDocent.equals(andereLes.deDocent) &&
					this.deKlas.equals(andereLes.deKlas) &&
					this.hetVak.equals(andereLes.hetVak) &&
					this.lokaal.equals(andereLes.lokaal)) {
				gelijkeObjecten = true;
			}
		}
		return gelijkeObjecten;
	}
}
