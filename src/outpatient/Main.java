package outpatient;







import GUI.HospitalGUI;
import javax.swing.*;
import java.sql.SQLException;
import static outpatient.Helper.getConfigTxt;
import static outpatient.HospitalConsole.consoleProgram;


public class Main {
    private static String configPath = "config.txt";


    public static void main(String[] args) {
        String config = getConfigTxt(configPath);
        
        try {
            startProgram(config);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void startProgram(String config) throws SQLException {
        switch (config) {
            case "1":
                consoleProgram();
                break;
            case "2":
                SwingUtilities.invokeLater(() -> new HospitalGUI());
                break;
            default:
                consoleProgram();
                break;
        }
    }







}
