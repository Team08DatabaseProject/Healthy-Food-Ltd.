package div;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Created by HUMBUG on 12.04.2016.
 */
public class DoubleField extends TextField {

	@Override
	public void replaceText(int i, int il, String string) {
		string = string.replace(",", ".");
		if(getText().contains(".")) {
			if(string.matches("\\d+") || string.isEmpty()) {
				super.replaceText(i, il, string);
			}
		} else {
			if(string.matches("\\d*?\\.?\\d*?") || string.isEmpty()) {
				super.replaceText(i, il, string);
			}
		}
	}
	public void setDouble(Double newDouble) {
		setText(newDouble.toString());
	}

	public Double getDouble() {
		if(getText().length() == 0) {
			return null;
		} else {
			return Double.parseDouble(getText());
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
		} else if(getText().matches("(?:\\d*\\.)?\\d+")) {
			valid = true;
		} else {
			error = "Must be a decimal number.";
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
		return valid;
	}
}
