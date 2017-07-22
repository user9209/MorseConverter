
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

package morse.convert.morsetypes;

import morse.alphabet.MorseAlphabet;
import morse.convert.MorseData;
import util.log.Log;

import java.util.ArrayList;

/**
 * MorseCode enables the representation of morse data as (string) dashes ('−') and dots ('·').
 * It provides methods for conversion to and from the intermediate representation.
 */
public class MorseCode extends MorseData {

    /* Strings used to build morse codes */
    public final static char DOT_CHAR = '·';
    public final static char DASH_CHAR = '−';
    public final static char SYMBOL_SEPARATOR_CHAR = ' ';
    public final static char SEPARATOR_CHARACTER_CHAR = '!';
    public final static char SEPARATOR_WORD_CHAR = '|';

    /* Strings used to encode Morse dits and dash */
    // TODO only used for input buttons!? remove? (else rename)
    public final static String MODEL_DOT = DOT_CHAR + "" + SYMBOL_SEPARATOR_CHAR;
    public final static String MODEL_DASH = DASH_CHAR + "" + SYMBOL_SEPARATOR_CHAR;
    public final static String MODEL_SPACE = "" + SYMBOL_SEPARATOR_CHAR;
    public static boolean spaceOut = true; // switch, if morse code should contain ! and | or spaces

    @Override
    public MorseData createIntermediate(String input) {

        // replace to intern symbols
        input = input.replaceAll("\\.","·").
                replaceAll("_", "−").
                replaceAll("-","−");

        // check if spaces should filled in
        if (input.matches(".*(" + DOT_CHAR + "|" + DASH_CHAR +")(" + DOT_CHAR + "|" + DASH_CHAR +").*"))
            input = MorseAlphabet.insertSpaces(input);

        // removes some spaces and replace some characters
        input = input.replaceAll(" \\| ", "\\|").
                replaceAll(" ! ", "!").
                replaceAll("   +", "\\|").
                replaceAll("  ", "!").trim();

        // something failed
        if(!MorseAlphabet.isMorseCode(input))
        {
            Log.logWarning("Invalid morse code!");
            return null;
        }

        this.zwischendarstellung = morseToMorseZwischendarstellung(input);
        return this;
    }

    @Override
    public String get(MorseData intermediateMorseData) {
        if(intermediateMorseData == null)
            return null;

        return formatOutput(toMorse(intermediateMorseData.getZwischendarstellung()));
    }

    /**
     * Should spaces or "|" and "!" used?
     * @param in text
     * @return replaced text
     */
    private String formatOutput(String in) {
        if(spaceOut) {
            return in.replaceAll("\\|", "   ").
                    replaceAll("!", "  ");
        }
        else
        {
            return in.replaceAll("\\|", " \\| ").
                    replaceAll("!", " ! ");
        }
    }

    /**
     * Morse text to Zwischendarstellung
     * @param morse string
     * @return Zwischendarstellung
     */
    public static MORSE_ZWISCHENDARSTELLUNG[] morseToMorseZwischendarstellung(String morse) {
        ArrayList<MORSE_ZWISCHENDARSTELLUNG> list = new ArrayList<>(1000);
        begin = true;

        for (Character cc : morse.toCharArray()) {
            list.add(toMorseZwischendarstellung(cc));
        }

        return list.toArray(new MORSE_ZWISCHENDARSTELLUNG[0]);
    }

    /**
     * Morse char to Zwischendarstellung
     * @param dashOrDot single char
     * @return Zwischendarstellung
     */
    public static MORSE_ZWISCHENDARSTELLUNG toMorseZwischendarstellung(Character dashOrDot) {

        switch (dashOrDot)
        {
            case DOT_CHAR:
                return MORSE_ZWISCHENDARSTELLUNG.DOT;
            case DASH_CHAR:
                return MORSE_ZWISCHENDARSTELLUNG.DASH;
            case SYMBOL_SEPARATOR_CHAR:
                return MORSE_ZWISCHENDARSTELLUNG.SEPARATOR_SYMBOL;
            case SEPARATOR_CHARACTER_CHAR:
                begin = true;
                return MORSE_ZWISCHENDARSTELLUNG.SEPARATOR_CHARACTER;
            case SEPARATOR_WORD_CHAR:
                begin = true;
                return MORSE_ZWISCHENDARSTELLUNG.SEPARATOR_WORD;
            default:
                Log.logWarning("toMorseZwischendarstellung failed for " + dashOrDot);
                return null;
        }
    }

    /**
     * Returns a string of the morse signals
     * @param zwischendarstellung
     * @return a string of the morse signals
     */
    public static String toMorse(MORSE_ZWISCHENDARSTELLUNG[] zwischendarstellung) {

        StringBuilder sb = new StringBuilder();

        for(MORSE_ZWISCHENDARSTELLUNG z : zwischendarstellung)
        {
            try {
                sb.append(toMorseSingle(z));
            } catch (Exception ignored) {
                // logged before
            }
        }
        return sb.toString();
    }

    /**
     * Zwischendarstellung to morse char
     * @param in
     * @return morse char
     */
    public static char toMorseSingle(MORSE_ZWISCHENDARSTELLUNG in) throws Exception{

        switch (in)
        {
            case DOT:
                return DOT_CHAR;
            case DASH:
                return DASH_CHAR;
            case SEPARATOR_SYMBOL:
                return SYMBOL_SEPARATOR_CHAR;
            case SEPARATOR_CHARACTER:
                return SEPARATOR_CHARACTER_CHAR;
            case SEPARATOR_WORD:
                return SEPARATOR_WORD_CHAR;
            default:
                Log.logWarning("toMorseSingle failed for " + in.name());
                throw new Exception("toMorseSingle failed for " + in.name());
        }
    }
}
