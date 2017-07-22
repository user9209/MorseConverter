
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

import static morse.alphabet.MorseAlphabet.filterInputText;

/**
 * The Text class provides methods to convert text to and from the intermedia morse representation.
 */
public class Text extends MorseData {

    @Override
    public MorseData createIntermediate(String input) {
        /* filters input to match replacement library for morse symbols */
        input = filterInputText(input);

        if(input.isEmpty())
            return null;

        this.zwischendarstellung = toMorseZwischendarstellung(input);
        return this;
    }

    @Override
    public String get(MorseData intermediateMorseData) {
        if(intermediateMorseData == null)
            return null;

        return toText(intermediateMorseData.getZwischendarstellung());
    }

    /**
     * Normal text to Zwischendarstellung
     * @param text
     * @return Zwischendarstellung
     */
    public static MORSE_ZWISCHENDARSTELLUNG[] toMorseZwischendarstellung(String text) {
        ArrayList<MORSE_ZWISCHENDARSTELLUNG> list = new ArrayList<>(1000);
        begin = true;
        for (Character c : text.toCharArray())
        {
            if(begin)
                begin = false;
            else
                list.add(MORSE_ZWISCHENDARSTELLUNG.SEPARATOR_CHARACTER);
            try {
                for (Character cc : MorseAlphabet.toMorse(c).toCharArray()) {

                    if(cc == '|')
                        // Remove last separator
                        list.remove(list.size() -1);

                    list.add(MorseCode.toMorseZwischendarstellung(cc));
                }
            } catch (Exception e) {
               Log.logWarning("Ignored "+ c);
            }
        }

        return list.toArray(new MORSE_ZWISCHENDARSTELLUNG[0]);
    }

    /**
     * Returns the normal text
     * @param in
     * @return the normal text
     */
    public static String toText(MORSE_ZWISCHENDARSTELLUNG[] in) {
        return MorseAlphabet.toText(MorseCode.toMorse(in));
    }
}
