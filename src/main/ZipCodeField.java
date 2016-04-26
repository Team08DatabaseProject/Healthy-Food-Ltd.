package main;

import javafx.css.PseudoClass;
import javafx.scene.control.Label;

/**
 * Created by HUMBUG on 07.04.2016.
 */
public class ZipCodeField extends IntField {

	private final PseudoClass errorClass = PseudoClass.getPseudoClass("error");

	@Override
	public void replaceText(int i, int il, String string) {
		if(getText().length() - (il - i) + string.length() <= 4) {
			super.replaceText(i, il, string);
		}
	}

	public boolean validate(boolean canBeEmpty, boolean displayError) {
		boolean valid = false;
		String error = "";
		if(getText().length() == 0) {
			if(canBeEmpty) {
				valid = true;
			} else {
				error = "Mandatory field.";
			}
		} else if(getText().matches("\\d{4}")) {
			valid = true;
		} else {
			error = "Zip code must be four digits long.";
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
			if(valid) {
				pseudoClassStateChanged(errorClass, false);
			} else {
				pseudoClassStateChanged(errorClass, true);
			}
		}
		return valid;
	}
}
