package classpackage;

/**
 * Created by paul thomas on 16.03.2016.
 */
public class Address {
    private int addressId;
    private String address;
    private ZipCode zipCode;

    public Address(int addressId, String address, ZipCode zipCode) {
        this.addressId = addressId;
        this.address = address;
        this.zipCode = zipCode;
    }

    public Address(String address, ZipCode zipCode) {
        this.address = address;
        this.zipCode = zipCode;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAdrdess(String address) {
        this.address = address;
    }

    public ZipCode getZipCode() {
        return zipCode;
    }

    public void setZipCode(ZipCode zipCode) {
        this.zipCode = zipCode;
    }
}
