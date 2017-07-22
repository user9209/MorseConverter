
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

public class Auto extends MorseData {
    private static boolean morse;

    @Override
    public MorseData createIntermediate(String input) {

        if(MorseAlphabet.isMorseCode(input))
        {
            morse = true;
            return new MorseCode().createIntermediate(input);
        }
        else
        {
            morse = false;
            return new Text().createIntermediate(input);
        }
    }

    @Override
    public String get(MorseData intermediateMorseData) {
        if(morse)
            return new Text().get(intermediateMorseData);
        else
            return new MorseCode().get(intermediateMorseData);
    }
}
