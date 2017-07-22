
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

import morse.convert.MorseData;
import util.sound.SoundThread;

public class Sound extends MorseData {

    @Override
    public MorseData createIntermediate(String input) {
        // not exist for this class
        return null;
    }

    @Override
    public String get(MorseData morseDataObject) {
        // not exist for this class
        return "Not implemented!";
    }

    /**
     * Plays the sound of the insert morse code
     * @param in
     */
    public static void toSound(MORSE_ZWISCHENDARSTELLUNG[] in) {

        if(in != null)
            SoundThread.playSound(in);
    }
}
