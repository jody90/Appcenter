package sortimo.formularmanager.global;

import java.util.HashMap;
import java.util.Map;

public class ConfigMaps {

	/**
	 * 
	 * @return Map mit allen verfuegbaren Laendern
	 */
	public Map<String, String> getCountrys() {
		Map<String, String> countries = new HashMap<String, String>();
		countries.put("DE", "Deutschland");
		countries.put("AT", "Östereich");
		countries.put("GB", "England");
		countries.put("FR", "Frankreich");
		countries.put("CH", "Schweiz");
		return countries;
	}
	
	/**
	 * 
	 * @return Map mit allen verfuegbaren Formulartypen
	 */
	public Map<String, String> getTypes() {
		Map<String, String> types = new HashMap<String, String>();
		types.put("antrag", "Antrag");
		types.put("umfrage", "Umfrage");
		types.put("wasweisich", "Was weis ich");
		return types;
	}
	
	/**
	 * 
	 * @return Map mit allen verfuegbaren Bearbeitet Statis
	 */
	public Map<String, String> getStates() {
		Map<String, String> types = new HashMap<String, String>();
		types.put("unfinished", "Unbearbeitet");
		types.put("in_process", "in Bearbeitung");
		types.put("finished", "Erledigt");
		types.put("rejected", "Abgelehnt");
		return types;
	}
	
	/**
	 * 
	 * @return Map mit allen verfuegbaren Status Icons
	 */
	public Map<String, String> getStateIcons() {
		Map<String, String> types = new HashMap<String, String>();
		types.put("1_unfinished", "fa fa-circle-o-notch");
		types.put("2_in_process", "fa fa-hourglass-half");
		types.put("3_rejected", "fa fa-ban");
		types.put("4_finished", "fa fa-check");
//		types.put("4_finished", "fa fa-check-circle-o");
		return types;
	}
	
//	/**
//	 * 
//	 * @param state Satus wie er in der DB steht
//	 * @return Uebersetzter Status wenn vorhanden, ansonsten der uebergebene State wieder zurueck
//	 */
//	public String translateState(String state) {
//		String tmpState = this.getStates().get(state);
//		return tmpState == null ? state : tmpState;
//	}
	
	/**
	 * 
	 * @return Map mit allen verfuegbaren Auswertungsmoeglichkeiten
	 */
	public Map<String, String> getEvaluationTypes() {
		Map<String, String> types = new HashMap<String, String>();
		types.put("chart", "Statistik");
		types.put("seperate", "Einzelformular");
		return types;
	}
	
}
