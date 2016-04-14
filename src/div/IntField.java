package div;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Created by HUMBUG on 07.04.2016.
 */
public class IntField extends TextField {

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
			System.out.println("Test");
			Label errorMsg = (Label) getScene().lookup("#" + getId() + "ErrorMsg");
			if(errorMsg != null) {
				if(!valid) {
					errorMsg.setText("Mandatory field.");
					errorMsg.setVisible(true);
				} else {
					errorMsg.setVisible(false);
				}
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
