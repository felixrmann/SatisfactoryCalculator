package SatisfactoryCalculator.Main;

import SatisfactoryCalculator.View.MainFrame;
import javafx.application.Application;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-November-13
 */

public class SatisfactoryCalculator {
    public static void main(String[] args) {
        new Thread(() -> Application.launch(MainFrame.class)).start();
    }
}
