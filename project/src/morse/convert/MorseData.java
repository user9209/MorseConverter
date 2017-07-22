
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

package morse.convert;

import morse.convert.morsetypes.Auto;
import morse.convert.morsetypes.MorseCode;
import morse.convert.morsetypes.Text;
import util.log.Log;

import java.util.HashMap;

/**
 * The MorseData Class defines methods for conversion from and to the intermediate type which it
 * stores (as all its children) in a local variable.<br><br>
 * <b>Every class implementing an input and/or output type have to instantiate this class</b>
 */
public abstract class MorseData {
    public enum MORSE_ZWISCHENDARSTELLUNG { DOT, DASH, SEPARATOR_SYMBOL, SEPARATOR_CHARACTER, SEPARATOR_WORD }

    public static final String TEXT_AUTO =  "Autodetect";
    public static final String TEXT_MORSE = "Morse Code";
    public static final String TEXT_TEXT =  "Text";

    public MORSE_ZWISCHENDARSTELLUNG[] getZwischendarstellung() {
        return zwischendarstellung;
    }

    /**
     * internal (intermediate) representation of the data
     */
    protected MORSE_ZWISCHENDARSTELLUNG[] zwischendarstellung;

    /**
     * mapping from morse data-type names to the actual classes
     */
    public final static HashMap<String, Class> morseInputDataTypes = new HashMap<>();
    public final static HashMap<String, Class> morseOutputDataTypes = new HashMap<>();
    static {
        morseInputDataTypes.put(TEXT_AUTO, Auto.class);
        morseInputDataTypes.put(TEXT_MORSE, MorseCode.class);
        morseInputDataTypes.put(TEXT_TEXT, Text.class);
    }

    static {
        morseOutputDataTypes.put(TEXT_AUTO, Auto.class);
        morseOutputDataTypes.put(TEXT_MORSE, MorseCode.class);
        morseOutputDataTypes.put(TEXT_TEXT, Text.class);
    }

    /**
     * Creates an intermediate MorseData object from the input data.
     * The conversion is given by the class on which it is called; the string should have matching type.
     *
     * @param input the string to be converted to intermediate representation
     * @return intermediate - the intermediate containing the converted input data
     */
    public abstract MorseData createIntermediate(String input);

    /**
     * Transforms the given intermediate representation of the input object
     * to the output representation of the class this function resides in.
     *
     * @param intermediateMorseData the intermediate representation object
     * @return string - the conversion of the intermediate data to the output type
     */
    public abstract String get(MorseData intermediateMorseData);

    protected static boolean begin = true;

    /**
     * Returns new instance of the class corresponding to objectType
     *
     * @param objectType - a new instance of this type is created and returned
     * @return inputTypeObject - the object which is returned
     */
    public static MorseData getInputTypeObject(String objectType) {
        try {
            return (MorseData) MorseData.morseInputDataTypes.get(objectType).newInstance();
        } catch (Exception e) {
            Log.logError("Class " + MorseData.morseInputDataTypes.get(objectType) + " could not be instantiated. " +
                    "Check if it was correctly spelled in the MorseData.morseInputDataTypes HashMap!");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns new instance of the class corresponding to objectType
     *
     * @param objectType - a new instance of this type is created and returned
     * @return outputTypeObject - the object which is returned
     */
    public static MorseData getOutputTypeObject(String objectType) {
        try {
            return (MorseData) MorseData.morseOutputDataTypes.get(objectType).newInstance();
        } catch (Exception e) {
            Log.logError("Class " + MorseData.morseOutputDataTypes.get(objectType) + " could not be instantiated. " +
                    "Check if it was correctly spelled in the MorseData.morseDataTypes HashMap!");
            e.printStackTrace();
            return null;
        }
    }
}
