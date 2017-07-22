
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

import javafx.scene.image.ImageView;
import morse.convert.MorseData;
import util.light.LightThread;

public class Light extends MorseData {

    @Override
    public MorseData createIntermediate(String input) {
        // not exist for this class
        return null;
    }

    @Override
    public String get(MorseData intermediateMorseData) {
        // not exist for this class
        return null;
    }

    /**
     * Generates Light signals as blinking button
     * @param zwischendarstellung
     */
    public static void toLight(MORSE_ZWISCHENDARSTELLUNG[] zwischendarstellung,ImageView lamp) {
        LightThread.playLight(zwischendarstellung, lamp);
    }
}
