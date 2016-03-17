package Driver;

/**
 * Created by axelkvistad on 3/16/16.
 * For use in "Change order status" table
 */
public class DriverOrderStatus {

    private int orderNo;
    private String address;
    private String deadline;
    private String status;

    public DriverOrderStatus(int orderNo, String address, String deadline, String status) {
        this.orderNo = orderNo;
        this.address = address;
        this.deadline = deadline;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
