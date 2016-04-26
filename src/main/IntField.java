package main;

import javafx.css.PseudoClass;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Created by HUMBUG on 07.04.2016.
 */
public class IntField extends TextField {

	private final PseudoClass errorClass = PseudoClass.getPseudoClass("error");

	@Override
	public void replaceText(int i, int il, String string) {
		if(string.matches("\\d+") || string.isEmpty()) {
			super.replaceText(i, il, string);
		}
	}

	public boolean validate(boolean canBeEmpty, boolean displayError) {
		boolean valid = true;
		if(!canBeEmpty && getText().length() == 0) {
		 valid = false;
		}
		if(displayError) {
			Label errorMsg = (Label) getScene().lookup("#" + getId() + "ErrorMsg");
			if(errorMsg != null) {
				if(!valid) {
					errorMsg.setText("Mandatory field.");
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

	public void setInt(Integer newInt) {
		setText(newInt.toString());
	}

	public Integer getInt() {
		if(getText().length() == 0) {
			return null;
		} else {
			return Integer.parseInt(getText());
		}
	}
}
