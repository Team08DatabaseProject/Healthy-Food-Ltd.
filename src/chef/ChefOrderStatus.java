package chef;

/**
 * Created by Trym Todalshaug on 26/03/2016.
 */
public class ChefOrderStatus {

    private int orderNo;
    private String deadline;
    private String status;

    public ChefOrderStatus(int orderNo, String deadline, String status){
        this.orderNo = orderNo;
        this.deadline = deadline;
        this.status = status;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
