
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

import javafx.scene.image.ImageView;
import morse.convert.MorseData;

public class LightThread extends Thread{

    private static LightThread thread;
    private MorseData.MORSE_ZWISCHENDARSTELLUNG[] lightData;
    private boolean run  = false;

    public static void playLight(MorseData.MORSE_ZWISCHENDARSTELLUNG[] lightData, ImageView lamp)
    {
        if(lightData == null || lamp == null)
            return;

        thread = new LightThread(lightData);
        LightOutput.setLamp(lamp);

        thread.start();
    }

    public static void stopLight()
    {
        if(thread != null && thread.isRunningThread())
            thread.stopSoundThread();
    }

    public LightThread(MorseData.MORSE_ZWISCHENDARSTELLUNG[] lightData) {
        this.lightData = lightData;
    }

    @Override
    public void run() {
        outputLightLoop();
    }

    @Override
    public synchronized void start() {
        if(lightData == null || isRunning())
            return;

        super.start();
        run = true;
    }

    public void outputLightLoop() {
        if(lightData == null)
            return;

        for (MorseData.MORSE_ZWISCHENDARSTELLUNG c : lightData)
        {
            if(!run)
                return;

            switch (c) {
                case DOT:
                    LightOutput.callDot();
                    break;
                case DASH:
                    LightOutput.callDash();
                    break;
                case SEPARATOR_SYMBOL:
                    LightOutput.callPauseBetweenSymbols();
                    break;
                case SEPARATOR_CHARACTER:
                    LightOutput.callPauseBetweenCharacters();
                    break;
                case SEPARATOR_WORD:
                    LightOutput.callPauseBetweenWords();
                    break;
                default:
                    System.out.println(c);
            }
        }
        run = false;
    }

    public static boolean isRunning() {
        return thread != null && thread.isRunningThread();
    }

    public boolean isRunningThread()
    {
        return run;
    }

    public void stopSoundThread(){
        if(run)
        {
            run = false;
        }
    }
}
