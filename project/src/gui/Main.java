
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

import gui.helper.StageCreator;
import gui.helper.StageStore;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import morse.MorseBackend;
import morse.convert.MorseData;
import morse.convert.morsetypes.MorseCode;

/**
 * Main class of our program
 * The main JavaFX application is started here.
 */
public class Main extends Application {

    /**
     * Main main
     * @param args
     */
    public static void main(String[] args) {
        switch (args.length)
        {
            case 0:
                try {
                    Main.class.getClassLoader().loadClass("javafx.application.Application");
                    launch(args);
                } catch (ClassNotFoundException e) {
                    FallBack.main(args);
                }
                break;
            case 1:
                String out  = null;
                try {
                    out = new MorseBackend(args[0], MorseData.TEXT_AUTO, MorseData.TEXT_AUTO).getOutput();
                } catch (Exception e) {
                    System.err.println("Not a valid input!");
                    System.exit(0);
                    return;
                }

                if(out == null || out .isEmpty()) {
                    System.err.println("Nothing to output!");
                    System.exit(0);
                    return;
                }
                System.out.println(out.replace(MorseCode.DOT_CHAR,'.').replace(MorseCode.DASH_CHAR,'-'));
                System.exit(0);
                break;
            default:
                System.out.println("To start JavaFX use no parameters.\n\n" +
                        "To use the command line use:\n" +
                        "<jar-file> <text or morse>\n\n" +
                        "Sound and light are not supported in the command line."
                );
                System.exit(0);
                break;
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        // ignores the given stage !

        // Hidden windows will not closed for optimisation
        Platform.setImplicitExit(false);

        /* create and setup main stage */
        Stage mainStage = StageCreator.getStageFromFXML("Morse Converter","gui.fxml");
        StageStore.addStage("main", mainStage);

        /* load hotkeys */
        ControllerMain.loadHotkeys();

        // Close App on X (Needs to be AFTER addStage() to overwrite default close)
        mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });

        // No resize
        mainStage.setResizable(false);
    }
}
