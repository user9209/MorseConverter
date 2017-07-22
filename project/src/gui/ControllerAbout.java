
/*
 *     Copyright (C) 2017
 *     David Alt <david_leonard.alt@stud.tu-darmstadt.de>
 *     Fabio d'Aquino Hilt <fabio.daquinohilt@stud.tu-darmstadt.de>
 *     Georg Schmidt <gs-develop@gs-sys.de>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package gui;

import gui.helper.StageStore;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The Controller is responsible for setting up everything in the GUI, which is not already in the fxml.
 * Also all GUI elements and their actions, which are defined in the fxml get a counterpart here for
 * programmatic use.
 */
public class ControllerAbout implements Initializable {

    // TextArea
    @FXML
    TextArea ta_Author;
    @FXML
    TextArea ta_Licence;
    @FXML
    TextArea ta_Website;

    // AnchorPane
    @FXML
    AnchorPane a_Author;
    @FXML
    AnchorPane a_Licence;
    @FXML
    AnchorPane a_Website;

    // Button
    @FXML
    Button BackToMainButton;

    @FXML
    void backToMain() {
        // hide current window, show main window
        StageStore.showStage("main");
        StageStore.hideStage("about");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // nothing to do
    }
}
