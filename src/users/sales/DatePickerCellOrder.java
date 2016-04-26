package users.sales;

import classpackage.Order;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;

public class DatePickerCellOrder<S, T> extends TableCell<Order, LocalDate> {

    private DatePicker datePicker;
    private ObservableList<Order> orderData;

    public DatePickerCellOrder(ObservableList<Order> listOrders) {

        super();

        this.orderData = listOrders;

        if (datePicker == null) {
            createDatePicker();
        }
        setGraphic(datePicker);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                datePicker.requestFocus();
            }
        });
    }

    @Override
    public void updateItem(LocalDate item, boolean empty) {

        super.updateItem(item, empty);
        LocalDateStringConverter converter = new LocalDateStringConverter();

        if (null == this.datePicker) {
            System.out.println("datePicker is NULL");
        }

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {

            if (isEditing()) {
                setContentDisplay(ContentDisplay.TEXT_ONLY);

            } else {
                int day, month, year;
                day = item.getDayOfMonth();
                month = item.getMonthValue();
                year = item.getYear();
                LocalDate ld = LocalDate.of(year, month, day);
                datePicker.setValue(ld);
                setText(item.getDayOfMonth() + "/" + item.getMonthValue() + "/" + item.getYear());
                setGraphic(this.datePicker);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }
        }
    }

    private void setDatepickerDate(String dateAsStr) {

        LocalDate ld = null;
        int day, month, year;

        day = month = year = 0;
        try {
            day = Integer.parseInt(dateAsStr.substring(0, 3));
            month = Integer.parseInt(dateAsStr.substring(3, 5));
            year = Integer.parseInt(dateAsStr.substring(6, dateAsStr.length()));
        } catch (NumberFormatException e) {
            System.out.println("setDatepickerDate / unexpected error " + e);
        }

        ld = LocalDate.of(year, month, day);
        datePicker.setValue(ld);
    }

    private void createDatePicker() {
        this.datePicker = new DatePicker();
        datePicker.setPromptText("dd/mm/yyyy");
        datePicker.setEditable(true);

        datePicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                LocalDate date = datePicker.getValue();
                setText(date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear());
                commitEdit(date);
            }
        });

        setAlignment(Pos.CENTER);
    }

    @Override
    public void startEdit() {
        super.startEdit();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    public ObservableList<Order> getOrderData() {
        return orderData;
    }

    public void setOrderData(ObservableList<Order> orderData) {
        this.orderData = orderData;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public void setDatePicker(DatePicker datePicker) {
        this.datePicker = datePicker;
    }

}