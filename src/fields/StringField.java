package fields;

import javafx.css.PseudoClass;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Map;

/**
 * Created by HUMBUG on 12.04.2016.
 */
public class StringField extends TextField {

	private final PseudoClass errorClass = PseudoClass.getPseudoClass("error");

	public static final int CAN_BE_EMPTY = 0;
	public static final int MIN_LENGTH = 1;
	public static final int MAX_LENGTH = 2;

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
