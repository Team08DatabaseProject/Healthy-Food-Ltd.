package div;

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
}
