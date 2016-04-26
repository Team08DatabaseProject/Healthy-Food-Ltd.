package fields;

import javafx.css.PseudoClass;
import javafx.scene.control.Label;

/**
 * Extends IntField which again extends StringField. Provides extended validation on the IntField for
 * zip codes.
 *
 * The overriden replaceText method makes sure that users can't type or paste in text strings that are not zip codes.
 */
public class ZipCodeField extends IntField {

	private final PseudoClass errorClass = PseudoClass.getPseudoClass("error");

	@Override
	public void replaceText(int i, int il, String string) {
		if(getText().length() - (il - i) + string.length() <= 4) {
			super.replaceText(i, il, string);
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
