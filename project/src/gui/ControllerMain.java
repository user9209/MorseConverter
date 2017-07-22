
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
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import morse.MorseBackend;
import morse.convert.MorseData;
import morse.convert.morsetypes.MorseCode;
import util.file.FileInOutGUI;
import util.file.FileToString;
import util.light.LightOutput;
import util.light.LightThread;
import util.log.Log;
import util.sound.SoundOutput;
import util.sound.SoundThread;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * The Controller is responsible for setting up everything in the GUI, which is not already in the fxml.
 * Also all GUI elements and their actions, which are defined in the fxml get a counterpart here for
 * programmatic use.
 */
public class ControllerMain implements Initializable {

    // Store the ControllerMain for external access
    private static ControllerMain me;

    // are true, if key is pressed
    private static boolean isControlPressed = false;
    private static boolean isAltPressed = false;
    private static boolean isShiftPressed = false;

    // translate engine morse <-> text and sound and light output
    private static MorseBackend translator;

    // if output text is colored
    private boolean coloredOutput = false;

    /**
     * External access to this class
     * @return this class instance
     */
    public static MorseBackend getMorseInfo()
    {
        return translator;
    }



    /* FXML field injection */
    /* Every field here gets the object referenced by the equally-named fx:id-strings (from the fxml file) */

    // MENU
    @FXML // fx:id="fileMenu"
    Menu fileMenu;
    @FXML // fx:id="loadInputMenuItem"
    MenuItem loadInputMenuItem;
    @FXML // fx:id="saveOutputMenuItem"
    MenuItem saveOutputMenuItem;
    @FXML // fx:id="quitMenuItem"
    MenuItem quitMenuItem;
    @FXML // fx:id="helpMenu"
    Menu helpMenu;
    @FXML // fx:id="aboutMenuItem"
    MenuItem aboutMenuItem;

    // TOOLBAR
    @FXML
    Slider volumeSlider;
    @FXML
    ComboBox<Integer> pitchComboBox;
    @FXML
    ComboBox<Integer> ditLengthComboBox;

    // INPUT
    @FXML // fx:id="inputVBox"
    VBox inputVBox;
    @FXML // fx:id="inputComboBox"
    ComboBox<String> inputComboBox;
    @FXML // fx:id="outputCombo"
    Button swapButton;
    @FXML
    AnchorPane inputAnchor;
    @FXML // fx:id="inputTextArea"
    TextArea inputTextArea;
    @FXML // fx:id="dotInputButton"
    Button dotInputButton;
    @FXML // fx:id="dashInputButton"
    Button dashInputButton;
    @FXML // fx:id="spaceInputButton"
    Button spaceInputButton;

    // LIGHT
    @FXML
    ImageView light;
    @FXML
    Button runLight;

    // OUTPUT
    @FXML // fx:id="outputVBox"
    VBox outputVBox;
    @FXML // fx:id="outputComboBox"
    ComboBox<String> outputComboBox;
    @FXML // fx:id="convertButton"
    Button convertButton;
    @FXML // fx:id="outputTextArea"
    TextArea outputTextArea;
    @FXML // fx:id="playSoundButton"
    Button playSoundButton;

    // SIDEPANE
    @FXML // fx:id="anchorPaneMorseTreeImageView"
    AnchorPane morseTreeImageAnchorPane;
    @FXML // fx:id="morseTreeImageView"
    ImageView morseTreeImageView;

    /* FXML action injection */
    /* Every method here gets the equally-named method connected to an action of an object in the fxml file */

    @FXML
    void onFileMenuItemClicked() {
         /* needs to exist for test, not really necessary though... */
    }

    @FXML
    void onHelpMenuItemClicked() {
        /* needs to exist for test, not really necessary though... */
    }

    @FXML
    void onRunLightClicked(){
        if(translator != null) {

            // as stop button
            if(LightThread.isRunning()) {
                LightThread.stopLight();
            }
            // as start button
            else {
                translator.toLight(light);
            }
        }

        // if errors are logged
        if(Log.haslog())
        {
            outputTextArea.setText(Log.getLog());
        }
    }

    @FXML
    void onLoadInputMenuItemClicked() {
        // get filename
        String filename = FileInOutGUI.openFilename();
        if(filename != null) {
            // load file content to input field
            inputTextArea.setText(FileToString.loadFromFile(filename));
            onTranslateButtonClicked();
        }
    }

    @FXML
    void onSaveOutputMenuItemClicked() {
        // get filename
        String filename = FileInOutGUI.saveFilename();
        if(filename != null) {
            // update output and store it to a file
            onTranslateButtonClicked();
            FileToString.stringToFile(filename, outputTextArea.getText());
        }
	}

    @FXML
    void onQuitMenuItemClicked() {
        // graceful way to exit JavaFX
        Platform.exit();
    }

    @FXML
    void onAboutMenuItemClicked() {
        if(StageStore.existsStage("about"))
        {
            // open about window
            StageStore.getStage("about").show();
        }
        else {

            // create about window
            Stage newStage = StageCreator.getStageFromFXML("Morse Converter - About","About.fxml");

            // If not loaded -> abort
            if(newStage == null)
                return;

            // add and open about window
            StageStore.addStage("about",newStage);
        }

        // hide the main
        StageStore.getStage("main").hide();
    }

    @FXML
    void onInputMethodComboBoxClicked() {
        // translate when input type ComboBox changed (user intention to change sth)
        onTranslateButtonClicked();
    }

    @FXML
    void onInputTypeChanged() {
        // translate when input type changed
        onTranslateButtonClicked();
    }

    /**
     * swaps the content of the input / output fields and the combo boxes
     */
    @FXML
    void onSwapButtonClicked() {

        // do not switch if output has errors
        if(coloredOutput)
            return;

        String newInput = outputTextArea.getText();

        // clear input because select() commands run onTranslateButtonClicked();
        inputTextArea.setText("");

        // swap input and output morseDataTypes (see google translate)
        String temp = inputComboBox.getSelectionModel().getSelectedItem();
        inputComboBox.getSelectionModel().select(outputComboBox.getSelectionModel().getSelectedItem());
        outputComboBox.getSelectionModel().select(temp);

        inputTextArea.setText(newInput);
        onTranslateButtonClicked();
    }

    @FXML
    void onInputTextChanged() {
        // translate every time the text changes
        onTranslateButtonClicked();
    }

    @FXML
    void onDotButtonClicked() {
        // append dot in input text field
        inputTextArea.appendText(MorseCode.MODEL_DOT);
        onInputTextChanged();
    }

    @FXML
    void onDashButtonClicked() {
        // append dot in input text field
        inputTextArea.appendText(MorseCode.MODEL_DASH);
        onInputTextChanged();
    }

    @FXML
    void onSpaceButtonClicked() {
        // append dot in input text field
        inputTextArea.appendText(MorseCode.MODEL_SPACE);
        onInputTextChanged();
    }

    @FXML
    void onOutputMethodComboBoxClicked() {
        // translate when output type ComboBox changed (user intention to change sth)
        onTranslateButtonClicked();
    }

    @FXML
    void onOutputTypeChanged() {
        // translate when output type changed
        onTranslateButtonClicked();
    }

    /**
     * Translator function
     */
    @FXML
    void onTranslateButtonClicked() {
        /* translate: input of input type to output of output type */
        String inputText = inputTextArea.getText().trim();

        if(inputText.isEmpty()) {
            translator = null;
            outputTextArea.setText("");
            return;
        }

        if(coloredOutput)
            setBlackOutput();

        String inputType = inputComboBox.getSelectionModel().getSelectedItem();
        String outputType = outputComboBox.getSelectionModel().getSelectedItem();

         /* store inputTypeObject e.g. for Sound output */
        try {
            translator = new MorseBackend(inputText, inputType, outputType);
        } catch (Exception e) {
            // may cause by invalid morse code
            translator = null;
            setRedOutput();
            outputTextArea.setText("No valid input");

            // throw log
            Log.getLog();
            return;
        }

        String out = translator.getOutput();
        if(Log.haslog())
        {
            setRedOutput();
            if(out == null)
                outputTextArea.setText(Log.getLog());
            else
                outputTextArea.setText(Log.getLog() + "\n\n" + out);
        }
        else if(out == null)
        {
            translator = null;
            setRedOutput();
            outputTextArea.setText("No valid output");
        }
        else {
            outputTextArea.setText(out);
        }
    }

    @FXML
    void onSoundButtonClicked() {
        if(translator != null) {
            // as stop button
            if(SoundThread.isRunning()) {
                SoundThread.stopSound();
            }
            // play sound
            else {
                translator.toSound();
            }
        }

        if(Log.haslog())
        {
            outputTextArea.setText(Log.getLog());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        me = this;

        /* fill and initialize comboBoxes */
        setupComboBox(inputComboBox, MorseData.morseInputDataTypes.keySet());
        setupComboBox(outputComboBox, MorseData.morseOutputDataTypes.keySet());

        /* setup textBoxes */
        inputTextArea.setPromptText("Input");
        outputTextArea.setPromptText("Output");

        /* setup volume slider */
        volumeSlider.setValue(SoundOutput.getVolume());
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            SoundOutput.setVolume(newValue.doubleValue());
        });
        /* setup pitch comboBox */
        pitchComboBox.getItems().setAll(100, 150, 200, 250, 300, 350, 400, 450, 500, 550,
                600, 650, 700, 750, 800, 850, 900, 950); // only until SoundOutput.SAMPLE_RATE / 2 (Nyquist)
        pitchComboBox.getSelectionModel().select(new Integer(SoundOutput.getPitch()));
        pitchComboBox.setConverter(new IntegerStringConverter());
        pitchComboBox.setOnAction((event)-> {
            SoundOutput.setPitch(pitchComboBox.getSelectionModel().getSelectedItem());
        });
        /* setup ditlength comboBox */
        ditLengthComboBox.getItems().setAll(50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150);
        ditLengthComboBox.getSelectionModel().select(new Integer(SoundOutput.getTimeDit()));
        ditLengthComboBox.setConverter(new IntegerStringConverter());
        ditLengthComboBox.setOnAction((event) -> {
            SoundOutput.setTimeDit(ditLengthComboBox.getSelectionModel().getSelectedItem());
            LightOutput.setTime(ditLengthComboBox.getSelectionModel().getSelectedItem());
        });

        /* so the image resizes with the window */
        morseTreeImageView.fitHeightProperty().bind(morseTreeImageAnchorPane.heightProperty());
        morseTreeImageView.fitWidthProperty().bind(morseTreeImageAnchorPane.widthProperty());

        /* smooth resizing */
        HBox.setHgrow(inputVBox, Priority.SOMETIMES);
        HBox.setHgrow(outputVBox, Priority.SOMETIMES);
        HBox.setHgrow(morseTreeImageAnchorPane, Priority.ALWAYS);
    }

    /**
     * Fills ComboBoxes with data from given set. Sets two different initial values and handles the case
     * where the set doesn't contain 2 different elements.
     *  @param comboBox
     * @param typeNames
     */
    static void setupComboBox(ComboBox<String> comboBox, Set<String> typeNames) {
        // TODO test
        comboBox.getItems().setAll(typeNames.stream().sorted().toArray(String[]::new));
        String firstType;

        /* the types have to be defined in the HashMap in MorseData and implemented as class */
        if (typeNames.size() < 1) { // no types!
            firstType = "NO_TYPES_DEFINED";
        } else { // ok
            firstType = comboBox.getItems().get(0);
        }
        comboBox.getSelectionModel().select(firstType);
    }

    /**
     * Set textarea to error color
     */
    private void setRedOutput()
    {
        if(!coloredOutput) {
            outputTextArea.setStyle("-fx-text-fill: #FF0000;");
            coloredOutput = true;
        }
    }

    /**
     * Set textarea to normal color
     */
    private void setBlackOutput()
    {
        if(coloredOutput) {
            outputTextArea.setStyle("-fx-text-fill: #000000;");
            coloredOutput = false;
        }
    }

    /**
     * Registers the hotkeys
     */
    public static void loadHotkeys()
    {
        StageStore.getStage("main").getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent k) {
                switch (k.getCode())
                {
                    case M:
                        if(isControlPressed) {
                            MorseCode.spaceOut = !MorseCode.spaceOut;
                            me.onTranslateButtonClicked();
                        }
                        break;
                    case S:
                        if(isControlPressed)
                            me.onSaveOutputMenuItemClicked();
                        break;
                    case O:
                        if(isControlPressed)
                            me.onLoadInputMenuItemClicked();
                        break;
                    case CONTROL:
                        isControlPressed = true;
                        break;
                    case ALT:
                        isAltPressed = true;
                        break;
                    case SHIFT:
                        isShiftPressed = true;
                        break;
                    default:
                }
            }
        });

        StageStore.getStage("main").getScene().setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent k) {
                switch (k.getCode())
                {
                    case CONTROL:
                        isControlPressed = false;
                        break;
                    case ALT:
                        isAltPressed = false;
                        break;
                    case SHIFT:
                        isShiftPressed = false;
                        break;
                    default:
                }
            }
        });
    }
}
