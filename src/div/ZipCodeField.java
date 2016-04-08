package div;

/**
 * Created by HUMBUG on 07.04.2016.
 */
public class ZipCodeField extends IntField {

	@Override
	public void replaceText(int i, int il, String string) {
		if(getText().length() < 4) {
			super.replaceText(i, il, string);
		} else if(getText().length() == 4 && string.length() == 0) {
			super.replaceText(i, il, string);
		}
	}

	public boolean validate() {
		if(getText().matches("\\d+") && getText().length() == 4) {
			return true;
		} else {
			return false;
		}
	}
}
