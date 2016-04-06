package classpackage;

/**
 * Created by paul thomas on 01.04.2016.
 */
public class ZipCode {
    private int zipCode;
    private String place;

    public ZipCode(int zipCode, String place) {
        this.zipCode = zipCode;
        this.place = place;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
