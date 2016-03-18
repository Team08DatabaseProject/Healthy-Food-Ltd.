package classpackage;

import java.util.Map;

/**
 * Created by axelkvistad on 3/18/16.
 */
public class EmployeePositions {
    public static Map<Integer, String> positions;

    public EmployeePositions(Map<Integer, String> positions) {
        this.positions = positions;
    }

    public Map<Integer, String> getPositions() {
        return positions;
    }

}
