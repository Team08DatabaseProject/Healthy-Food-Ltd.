package views.driver;

/**
 * Created by axelkvistad on 3/16/16.
 * For use in "Change order status" table
 */

public class OrderStatusController {

    private int orderNo;
    private String address;
    private String deadline;
    private String status;
    private String viktigInfo;

    public OrderStatusController(int orderNo, String address, String deadline, String status) {
        this.orderNo = orderNo;
        this.address = address;
        this.deadline = deadline;
        this.status = status;
        this.viktigInfo = "asdf";
    }

    public void setViktigInfo(String info) {
        viktigInfo = info;
    }

    public String getViktigInfo() {
        return viktigInfo;
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
