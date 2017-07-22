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


package util.file;

import javafx.stage.FileChooser;

import java.io.File;

public class FileInOutGUI {

    /**
     * Open a file
     * @return file path
     */
    public static String openFilename() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MORSE", "*.morse"),
                new FileChooser.ExtensionFilter("All Types", "*.*"),
                new FileChooser.ExtensionFilter("TXT", "*.txt"),
                new FileChooser.ExtensionFilter("DATA", "*.data")
        );
        File file = fileChooser.showOpenDialog(null);
        if ( file != null) {
            return file.getPath();
        } else {
            return null;
        }
    }

    /**
     * Saves a file
     * @return file path
     */
    public static String saveFilename() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MORSE", "*.morse"),
                new FileChooser.ExtensionFilter("All Types", "*.*"),
                new FileChooser.ExtensionFilter("TXT", "*.txt"),
                new FileChooser.ExtensionFilter("DATA", "*.data")
        );
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            return file.getPath();
        } else {
            return null;
        }
    }
}