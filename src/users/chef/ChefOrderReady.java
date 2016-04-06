package users.chef;

/**
 * Created by Trym Todalshaug on 26/03/2016.
 */
public class ChefOrderReady {

    private int orderNo;
    private String deadline;

    public ChefOrderReady(int orderNo, String deadline){
        this.orderNo = orderNo;
        this.deadline = deadline;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
