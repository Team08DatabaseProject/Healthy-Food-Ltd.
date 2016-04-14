package classpackage;

/**
 * Created by paul thomas on 16.03.2016.
 */
public class Address {
    private int addressId;
    private String address;
    private int zipCode;
    private String place;

    public Address(int addressId, String address, int zipCode, String place) {
        this.addressId = addressId;
        this.address = address;
        this.zipCode = zipCode;
        this.place = place;
    }

    public Address(String address, int zipCode, String place) {
        this.address = address;
        this.zipCode = zipCode;
        this.place = place;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int adressId) {
        this.addressId = adressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
