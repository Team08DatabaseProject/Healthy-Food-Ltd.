package fields;

import javafx.css.PseudoClass;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Extends TextField. Provides validation for fields containing doubles as well as methods for getting and setting
 * the contents of the TextField as doubles.
 *
 * The overriden replaceText method makes sure users can type or paste in text strings that are not doubles.
 */
public class DoubleField extends TextField {

	private final PseudoClass errorClass = PseudoClass.getPseudoClass("error");

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

	/**
	 * Validates the field. Checks if the field is empty or not and returns false if it can't be empty.
	 * @param canBeEmpty Set to true if field can be empty. False if not.
	 * @param displayError  Set to true if an error message should be displayed in a Label. It will automatically
	 *                      find and use a Label with the sme ID as the ZipCodeFIeld + "ErrorMsg".
	 * @return	True if the field validates.
	 *
	 */
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
			if(valid) {
				pseudoClassStateChanged(errorClass, false);
			} else {
				pseudoClassStateChanged(errorClass, true);
			}
		}
		return valid;
	}
}
