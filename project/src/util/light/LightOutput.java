
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

package util.light;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.concurrent.TimeUnit;


public class LightOutput{

    private static int time = 50;

    /* These values should possibly stay fixed (as they are defined in the morse protocol)
     *   to avoid magic numbers, they are listed */
    private static final int DAH_IN_DITS = 3;
    private static final int SYMBOL_PAUSE_IN_DITS = 1;
    private static final int CHARACTER_PAUSE_IN_DITS = 3;
    private static final int WORD_PAUSE_IN_DITS = 7;

    private static ImageView lamp;
    private final static Image lampOnImage;
    private final static Image lampOffImage;
            static {
                lampOnImage = new Image(LightOutput.class.getClassLoader().getResourceAsStream("pic/gluehbirne_on.png"));
                lampOffImage = new Image(LightOutput.class.getClassLoader().getResourceAsStream("pic/gluehbirne.png"));
            }

    public static void setTime(Integer time) {
        LightOutput.time = time;
    }
    
    public static void setLamp(ImageView lamp) {
        LightOutput.lamp = lamp;
    }

    public static void lampOn()
    {
        lamp.setImage(lampOnImage);
    }

    public static void lampOff()
    {
        lamp.setImage(lampOffImage);
    }

    public static void callDot() {

        lampOn();
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {

        }
        lampOff();
    }

    public static void callDash() {
        lampOn();
        try {
            TimeUnit.MILLISECONDS.sleep(time * DAH_IN_DITS);
        } catch (InterruptedException e) {

        }
        lampOff();
    }

    public static void callPauseBetweenSymbols() {
        try {
            TimeUnit.MILLISECONDS.sleep(time * SYMBOL_PAUSE_IN_DITS);
        } catch (InterruptedException e) {

        }
    }

    public static void callPauseBetweenCharacters() {
        try {
            TimeUnit.MILLISECONDS.sleep(time * CHARACTER_PAUSE_IN_DITS);
        } catch (InterruptedException e) {

        }
    }

    public static void callPauseBetweenWords() {
        try {
            TimeUnit.MILLISECONDS.sleep(time * WORD_PAUSE_IN_DITS);
        } catch (InterruptedException e) {

        }
    }
}
