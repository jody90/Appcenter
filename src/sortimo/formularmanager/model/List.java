package sortimo.formularmanager.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class List {
	
	/**
	 * Ueberprueft ob ein Fomular gerade aktiv ist
	 * 
	 * @param validFrom Startzeit
	 * @param validTo Endzeit
	 * @return boolean
	 */
    public boolean isActive(String validFrom, String validTo) {
		boolean formIsActive = false;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
	    Date startDate;
	    Date endDate;
	    Date currentDate = new Date();

	    if (validFrom != "") {
		    try {
		    	startDate = dateFormat.parse(validFrom);
		    	endDate = dateFormat.parse(validTo);
		    	
		    	if (endDate.after(currentDate) && startDate.before(currentDate)) {
		    		formIsActive = true;
		    	}
		    } catch (ParseException e) {
		        e.printStackTrace();
		    }
	    }
	    return formIsActive;
    }

}
