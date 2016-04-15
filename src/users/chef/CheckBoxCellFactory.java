package users.chef;
import classpackage.Dish;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.util.Callback;

public class CheckBoxCellFactory implements Callback {
    @Override
    public TableCell call(Object param) {
        CheckBoxTableCell<Dish, Boolean> checkBoxCell = new CheckBoxTableCell<>();
        return checkBoxCell;
    }
}