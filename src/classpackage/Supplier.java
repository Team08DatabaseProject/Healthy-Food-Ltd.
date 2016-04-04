package classpackage;

/**
 * Created by paul thomas on 16.03.2016.
 */
public class Supplier {
    private int supplierId;
    private int phoneNumber;
    private Address thisAddress;

//    from database
    public Supplier(int supplierId, int phoneNumber, Address thisAddress) {
        this.supplierId = supplierId;
        this.phoneNumber = phoneNumber;
        this.thisAddress = thisAddress;
    }
//    To database
    public Supplier(int phoneNumber, Address thisAddress) {
        this.phoneNumber = phoneNumber;
        this.thisAddress = thisAddress;
    }

}
