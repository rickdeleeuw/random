package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import model.PrIS;
import model.klas.Klas;
import model.les.Les;
import model.persoon.Docent;
import model.persoon.Student;
import model.presentie.Presentie;
import model.vak.Vak;
import server.Conversation;
import server.Handler;

class PresentieController implements Handler {
	private PrIS informatieSysteem;
	
	/**
	 * De PresentieController klasse moet alle student-gerelateerde aanvragen
	 * afhandelen. Methode handle() kijkt welke URI is opgevraagd en laat
	 * dan de juiste methode het werk doen. Je kunt voor elke nieuwe URI
	 * een nieuwe methode schrijven.
	 * 
	 * @param infoSys - het toegangspunt tot het domeinmodel
	 */
	public PresentieController(PrIS infoSys) {
		informatieSysteem = infoSys;
	}
	
	public void handle(Conversation conversation) {
		if (conversation.getRequestedURI().startsWith("/docent/vakken/ophalen")) {
			vakken_ophalen(conversation);
		} else if (conversation.getRequestedURI().startsWith("/docent/klassen/ophalen")) {
			klassen_ophalen(conversation);
		} else if (conversation.getRequestedURI().startsWith("/docent/presentie/ophalen")) {
			presentie_ophalen(conversation);
		} else if (conversation.getRequestedURI().startsWith("/docent/studenten/ophalen")) {
			studenten_ophalen(conversation);
		}
	}
	
	/**
	 * Deze methode haalt eerst de opgestuurde JSON-data op. Daarna worden
	 * de benodigde gegevens uit het domeinmodel gehaald. Deze gegevens worden
	 * dan weer omgezet naar JSON en teruggestuurd naar de Polymer-GUI!
	 * 
	 * @param conversation - alle informatie over het request
	 */
	private void vakken_ophalen(Conversation conversation) {
		JsonObject lJsonObjIn = (JsonObject) conversation.getRequestBodyAsJSON();
		String lGebruikersnaam = lJsonObjIn.getString("username");						// Zoeken met op eigen username
		
		Docent lDocentZelf = informatieSysteem.getDocent(lGebruikersnaam);
		ArrayList<Vak> docentVakken = new ArrayList<Vak>();
		for (Les l : informatieSysteem.getDeLessen()) {
			if (l.getDeDocent().equals(lDocentZelf) && !docentVakken.contains(l.getHetVak())) {
				docentVakken.add(l.getHetVak());
			}
		}

		JsonArrayBuilder lJsonArrayBuilder = Json.createArrayBuilder();
		JsonObjectBuilder lJsonObjectBuilder = Json.createObjectBuilder();
		
		for (Vak v : docentVakken) {
			lJsonObjectBuilder.add("vakCode", v.getVakCode());
			lJsonArrayBuilder.add(lJsonObjectBuilder);
		}
		
		String lJsonOut = lJsonArrayBuilder.build().toString();
		
		conversation.sendJSONMessage(lJsonOut);
	}
	
	public void klassen_ophalen(Conversation conversation) {
		JsonObject lJsonObjIn = (JsonObject) conversation.getRequestBodyAsJSON();

		String lGebruikersnaam = lJsonObjIn.getString("username");						// Zoeken met op eigen username
		String lVakCode = lJsonObjIn.getString("vakCode");
		
		Docent lDocentZelf = informatieSysteem.getDocent(lGebruikersnaam);
		
		Vak lVakSelected = null;
		for (Vak v : informatieSysteem.getDeVakken()) {
			if (v.getVakCode().equals(lVakCode)) {
				lVakSelected = v;
			}
		}

		ArrayList<Klas> vakKlassen = new ArrayList<Klas>();
		for (Les l : informatieSysteem.getDeLessen()) {
			if (l.getDeDocent().equals(lDocentZelf) &&
					l.getHetVak().equals(lVakSelected) &&
					!vakKlassen.contains(l.getDeKlas())) {
				vakKlassen.add(l.getDeKlas());
			}
		}
		
		JsonArrayBuilder lJsonArrayBuilder = Json.createArrayBuilder();
		JsonObjectBuilder lJsonObjectBuilder = Json.createObjectBuilder();
		
		for (Klas k : vakKlassen) {
			lJsonObjectBuilder.add("klasCode", k.getKlasCode());
			lJsonArrayBuilder.add(lJsonObjectBuilder);
		}
		
		String lJsonOut = lJsonArrayBuilder.build().toString();
		
		conversation.sendJSONMessage(lJsonOut);
	}
	
	public void presentie_ophalen(Conversation conversation) {
		JsonObject lJsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();
		System.out.println("jsonobj: " + lJsonObjectIn);
		
		String lKlasCode = lJsonObjectIn.getString("klasCode");
		String lVakCode = lJsonObjectIn.getString("vakCode");
		String lSysteemDatum = lJsonObjectIn.getString("systeemDatum");
		System.out.println("Vak, klas en datum uit json: " + lVakCode + ", " + lKlasCode + ", " + lSysteemDatum);
		
		Klas lKlas = null;
				for (Klas k : informatieSysteem.getDeKlassen()) {
			if (k.getKlasCode().equals(lKlasCode)) {
				lKlas = k;
			}
		}
		System.out.println("klascode van klas: " + lKlas.getKlasCode());
				
		Vak lVak = null;
		for (Vak v : informatieSysteem.getDeVakken()) {
			if (v.getVakCode().equals(lVakCode)) {
				lVak = v;
			}
		}
		System.out.println("vakcode van vak: " + lVak.getVakCode());
		
		ArrayList<Les> lLessen = new ArrayList<Les>();
		for (Les l : informatieSysteem.getDeLessen()) {
			if (l.getDeKlas().equals(lKlas) &&
					l.getHetVak().equals(lVak)) {
				lLessen.add(l);
				System.out.println("les: " + l);
			}
		}
		System.out.println("lessen: " + lLessen);
		
		Les lLes = null;
		String[] systeemDatumElements = lSysteemDatum.split("-");
		LocalDate systeemDatumLocalDate = LocalDate.of(Integer.parseInt(systeemDatumElements[2]),
													Integer.parseInt(systeemDatumElements[1]),
													Integer.parseInt(systeemDatumElements[0]));
		for (Les l : lLessen) {
			if (l.getDatum().equals(systeemDatumLocalDate)) {
				lLes = l;
			}
		}
		
		ArrayList<Student> lStudenten = new ArrayList<Student>();
		for (Student s : lKlas.getStudenten()) {
			lStudenten.add(s);
			System.out.println(s.getStudentNummer());
		}
		
		JsonObjectBuilder lJsonObjectBuilder = Json.createObjectBuilder();
		JsonArrayBuilder lJsonArrayBuilder = Json.createArrayBuilder();
		
		
		if (lLes != null) {
			for (Student s : lStudenten) {
				Presentie lPresentie = new Presentie(lLes,s);
				if (!(informatieSysteem.getDePresenties().contains(lPresentie))) {
					informatieSysteem.getDePresenties().add(lPresentie);
				} else {
					for (Presentie p : informatieSysteem.getDePresenties()) {
						if (p.equals(lPresentie)) {
							lPresentie = p;
						}
					}
				}
			
				lJsonObjectBuilder.add("firstName", s.getVoornaam())
									.add("lastName", s.getVolledigeAchternaam())
									.add("id", s.getStudentNummer())
									.add("aanwezig", lPresentie.getAanwezig());
				lJsonArrayBuilder.add(lJsonObjectBuilder);
			}
		}
		/*JsonObject lJsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();
		System.out.println("jsonobj: " + lJsonObjectIn);
		String lKlasCode = lJsonObjectIn.getString("klasCode");
		String lVakCode = lJsonObjectIn.getString("vakCode");
		System.out.println("Vak en klas uit json: " + lVakCode + ", " + lKlasCode);
		Klas lKlas = null;
		for (Klas k : informatieSysteem.getDeKlassen()) {
			if (k.getKlasCode().equals(lKlasCode)) {
				lKlas = k;
			}
		}
		System.out.println("klascode van klas: " + lKlas.getKlasCode());
		Vak lVak = null;
		for (Vak v : informatieSysteem.getDeVakken()) {
			if (v.getVakCode().equals(lVakCode)) {
				lVak = v;
			}
		}
		System.out.println("vakcode van vak: " + lVak.getVakCode());
		ArrayList<Les> lLessen = new ArrayList<Les>();
		for (Les l : informatieSysteem.getDeLessen()) {
			if (l.getDeKlas().equals(lKlas) &&
					l.getHetVak().equals(lVak)) {
				lLessen.add(l);
				System.out.println("les: " + l);
			}
		}
		System.out.println("lessen: " + lLessen);
		
		ArrayList<Student> lStudenten = new ArrayList<Student>();
		for (Student s : lKlas.getStudenten()) {
			lStudenten.add(s);
			System.out.println(s.getGebruikersnaam());
		}
		
		JsonObjectBuilder lJsonObjectBuilder = Json.createObjectBuilder();
		JsonArrayBuilder lJsonArrayBuilder = Json.createArrayBuilder();
		
		
		for (Student s : lStudenten) {
			lJsonObjectBuilder.add("id", s.getStudentNummer());
			System.out.println(s.getStudentNummer());
			for (Les l : lLessen) {
				Presentie lPresentie = new Presentie(l, s);
				if (!informatieSysteem.getDePresenties().contains(lPresentie)) {
					System.out.println(lPresentie.getDeLes().getDatum() + ",   " + lPresentie.getDeStudent().getStudentNummer());
					informatieSysteem.addPresentie(lPresentie);
				} else {
					for (Presentie p : informatieSysteem.getDePresenties()) {
						if (lPresentie.equals(p)) {
							lPresentie = p;
						}
					}
				}
					lJsonObjectBuilder.add("aanwezig", lPresentie.getAanwezig())
										.add("datum", lPresentie.getDeLes().getDatum()
												.format(DateTimeFormatter.ofPattern("dd LLLL yyyy")));
			}
			lJsonArrayBuilder.add(lJsonObjectBuilder);
		}*/
		
		/*	for (Les l : lLessen) {
				for (Student s : lStudenten) {
					lJsonObjectBuilder.add("id", s.getStudentNummer());
					System.out.println(s.getStudentNummer());
				Presentie lPresentie = new Presentie(l, s);
				if (!informatieSysteem.getDePresenties().contains(lPresentie)) {
					System.out.println(lPresentie.getDeLes().getDatum() + ",   " + lPresentie.getDeStudent().getStudentNummer());
					informatieSysteem.addPresentie(lPresentie);
				} else {
					for (Presentie p : informatieSysteem.getDePresenties()) {
						if (lPresentie.equals(p)) {
							lPresentie = p;
						}
					}
				}
					lJsonObjectBuilder.add("aanwezig", lPresentie.getAanwezig())
										.add("datum", lPresentie.getDeLes().getDatum()
												.format(DateTimeFormatter.ofPattern("dd LLLL yyyy")));
				lJsonArrayBuilder.add(lJsonObjectBuilder);
				}
			
		}*/
		
		String lJsonOutStr = lJsonArrayBuilder.build().toString();
		System.out.println(lJsonOutStr);
		conversation.sendJSONMessage(lJsonOutStr);	
	}
	
	public void studenten_ophalen(Conversation conversation) {
		JsonObject lJsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();

		String lKlasCode = lJsonObjectIn.getString("klasCode");
		
		Klas lKlas = null;
		for (Klas k : informatieSysteem.getDeKlassen()) {
			if (k.getKlasCode().equals(lKlasCode)) {
				lKlas = k;
			}
		}
		
		ArrayList<Student> lStudentenVanKlas = lKlas.getStudenten();
		
		JsonObjectBuilder lJsonObjectBuilder = Json.createObjectBuilder();
		JsonArrayBuilder lJsonArrayBuilder = Json.createArrayBuilder();
	
		for (Student s : lStudentenVanKlas) {
			lJsonObjectBuilder
				.add("id", s.getStudentNummer())																	//vul het JsonObject		     
				.add("firstName", s.getVoornaam())	
				.add("lastName", s.getVolledigeAchternaam());					     
			  
			 lJsonArrayBuilder.add(lJsonObjectBuilder);													//voeg het JsonObject aan het array toe				     
		}

		String lJsonOutStr = lJsonArrayBuilder.build().toString();												// maak er een string van
		conversation.sendJSONMessage(lJsonOutStr);	
	}
}