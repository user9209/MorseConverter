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

package gui.helper;

import gui.Main;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.HashMap;

/**
 * Manages multiple stages
 */
public class StageStore {

    private static HashMap<String,Stage> stages = new HashMap<>();

    /**
     * Returns the sage with the given name
     * @param stageName name of the stage
     * @return stage or null
     */
    public static Stage getStage(String stageName) {
        return stages.get(stageName);
    }

    public static void addStage(String stageName, Stage stage) {
        if(existsStage(stageName) || stage == null)
            return;

        /* set Icon */
        loadIcon(stage);

        // default closing is calling main window and hide this stage
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                showStage("main");
                hideStage(stageName);
            }
        });

        // store it
        stages.put(stageName, stage);
    }

    /**
     * If stage with the given name exists
     * @param stageName name of the stage
     * @return  true or false
     */
    public static boolean existsStage(String stageName) {
        return stages.containsKey(stageName);
    }

    /**
     * Closes the stage with the given name
     * @param stageName name of the stage
     */
    public static void closeStage(String stageName)
    {
        if(existsStage(stageName))
            getStage(stageName).close();
    }

    /**
     * Hides the stage with the given name
     * @param stageName name of the stage
     */
    public static void hideStage(String stageName)
    {
        if(existsStage(stageName))
            getStage(stageName).hide();
    }

    /**
     * Shows the stage with the given name
     * @param stageName name of the stage
     */
    public static void showStage(String stageName)
    {
        if(existsStage(stageName))
            getStage(stageName).show();
    }

    /**
     * Closes the stage with the given name
     * @param node node for example a Stage - Object
     */
    public static void closeStage(Node node) {

        if(node == null)
            return;

        // get a handle to the stage
        Stage stage = (Stage) node.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    /**
     * Loads a icon an place it to the window
     * @param stage name of the stage
     */
    private static void loadIcon(Stage stage) {
        try {
            stage.getIcons().add(new Image(Main.class.getClassLoader().getResourceAsStream("pic/morse_icon.png")));
        }catch (Exception ignored)
        {
            ignored.printStackTrace();
            System.out.println("Icon failed to load.");
        }
    }
}
