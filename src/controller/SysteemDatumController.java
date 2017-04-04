package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

import model.PrIS;
import model.les.Les;
import server.Conversation;
import server.Handler;

public class SysteemDatumController implements Handler {
	private PrIS informatieSysteem;
	/**
	 * De SysteemDatumController klasse moet alle systeem (en test)-gerelateerde aanvragen
	 * afhandelen. Methode handle() kijkt welke URI is opgevraagd en laat
	 * dan de juiste methode het werk doen. Je kunt voor elke nieuwe URI
	 * een nieuwe methode schrijven.
	 * 
	 * @param infoSys - het toegangspunt tot het domeinmodel
	 */
	public SysteemDatumController(PrIS infoSys) {
		informatieSysteem = infoSys;
	}

	public void handle(Conversation conversation) {
	  if (conversation.getRequestedURI().startsWith("/systeemdatum/lesinfo")) {
			ophalenLesInfo(conversation);
		}
	}
	
  private void ophalenLesInfo(Conversation conversation) {
  	//<to do> begin
  	//De volgende statements moeten gewijzigd worden zodat daadwerkelijk de eerste en laatste lesdatum wordt bepaald
	ArrayList<Les> deLessen = informatieSysteem.getDeLessen();
	System.out.println(deLessen);
	LocalDate maxDate = deLessen.stream().map(Les::getDatum).max(LocalDate::compareTo).get();
	LocalDate minDate = deLessen.stream().map(Les::getDatum).min(LocalDate::compareTo).get();
	System.out.println(maxDate);
	System.out.println(minDate);
	Calendar lEersteLesDatum = new GregorianCalendar(minDate.getYear(),(minDate.getMonthValue()-1), minDate.getDayOfMonth());
	Calendar lLaatsteLesDatum = new GregorianCalendar(maxDate.getYear(),(maxDate.getMonthValue()-1), maxDate.getDayOfMonth());
	//Calendar lLaatsteLesDatum = Calendar.getInstance();
    //<to do> end
		
		JsonObjectBuilder lJsonObjectBuilder = Json.createObjectBuilder();
		//Deze volgorde mag niet worden gewijzigd i.v.m. JS. (Hier mag dus ook geen andere JSON voor komen.)
		lJsonObjectBuilder 
			.add("eerste_lesdatum", PrIS.calToStandaardDatumString(lEersteLesDatum))
			.add("laatste_lesdatum", PrIS.calToStandaardDatumString(lLaatsteLesDatum));

		String lJsonOut = lJsonObjectBuilder.build().toString();
		
		conversation.sendJSONMessage(lJsonOut);										// terugsturen naar de Polymer-GUI!	}
  }
}
