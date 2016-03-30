package Driver;

/** Created by Axel
 * 11.03.2016
 * For use in "Orders ready for delivery" table
 */

public class DriverOrderDelivery {
    private int orderNo;
    private String address;
    private String deadline;
    private int viktigInformasjon;

    public DriverOrderDelivery(int orderNo, String address, String deadline) {
        this.orderNo = orderNo;
        this.address = address;
        this.deadline = deadline;
    }

    public void setViktigInformasjon(int bleh) {
        viktigInformasjon = bleh;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public String getAddress() {
        return address;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}