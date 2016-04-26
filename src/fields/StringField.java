package fields;

import javafx.css.PseudoClass;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Map;

/**
 * Extends TextField. Provides a validation method for checking if a user has input information correctly.
 */
public class StringField extends TextField {

	private final PseudoClass errorClass = PseudoClass.getPseudoClass("error");

	// Constants for validation rules.
	public static final int CAN_BE_EMPTY = 0;
	public static final int MIN_LENGTH = 1;
	public static final int MAX_LENGTH = 2;

	/**
	 * Validates the field against the rules provided in the Map.
	 * @param rules A map containing rules made up of the static constants in this class's declaration.
	 * @param displayError  Set to true if an error message should be displayed in a Label. It will automatically
	 *                      find and use a Label with the sme ID as the ZipCodeFIeld + "ErrorMsg".
	 * @return	True if the field validates.
	 *
	 */

	public boolean validate(Map<Integer, Integer> rules, boolean displayError) {
		boolean valid = true;
		String error = "";
		for(Map.Entry<Integer, Integer> rule : rules.entrySet()) {
			switch(rule.getKey()) {
				case CAN_BE_EMPTY:
					if(rule.getValue() == 0 && getText().length() == 0) {
						valid = false;
						error = "Mandatory field.";
					}
					break;
				case MIN_LENGTH:
					if(rule.getValue() > getText().length()) {
						valid = false;
						error = "Must contain at least " + rule.getValue() + " characters.";
					}
					break;
				case MAX_LENGTH:
					if(rule.getValue() < getText().length()) {
						valid = false;
						error ="Can't contain more than " + rule.getValue() + " characters.";
					}
					break;
			}
		}
		if(displayError) {
			Label errorMsg = (Label) getScene().lookup("#" + getId() + "ErrorMsg");
			if(errorMsg != null) {
				if(!valid) {
					errorMsg.setText(error);
					errorMsg.setVisible(true);
				} else {
					errorMsg.setVisible(false);
				}
			}
		}
		if(valid) {
			pseudoClassStateChanged(errorClass, false);
		} else {
			pseudoClassStateChanged(errorClass, true);
		}
		return valid;
	}
}
