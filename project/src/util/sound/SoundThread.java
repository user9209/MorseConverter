
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

package util.sound;

import morse.convert.MorseData;

import javax.sound.sampled.LineUnavailableException;
import java.util.ArrayList;


public class SoundThread extends Thread{

    private static SoundThread thread;
    private MorseData.MORSE_ZWISCHENDARSTELLUNG[] soundData;
    private boolean run  = false;

    public static void playSound(MorseData.MORSE_ZWISCHENDARSTELLUNG[] soundData)
    {
        if(soundData == null)
            return;

        thread = new SoundThread(soundData);

        thread.start();
    }

    public static void stopSound()
    {
        if(thread.isRunningThread())
            thread.stopSoundThread();
    }

    public SoundThread(MorseData.MORSE_ZWISCHENDARSTELLUNG[] soundData) {
        this.soundData = soundData;
    }

    @Override
    public void run() {
        outputSoundLoop();
        run = false;
    }

    @Override
    public synchronized void start() {
        if(soundData == null || isRunning())
            return;

        super.start();
        run = true;
    }

    public void outputSoundLoop() {
        if(soundData == null)
            return;

        // store all samples once
        ArrayList<Byte> bufferLL = new ArrayList<>();

        for (MorseData.MORSE_ZWISCHENDARSTELLUNG c : soundData)
        {
            if(!run)
                return;

            switch (c) {
                case DOT:
                    bufferLL.addAll(SoundOutput.getDot());
                    break;
                case DASH:
                    bufferLL.addAll(SoundOutput.getDash());
                    break;
                case SEPARATOR_SYMBOL:
                    bufferLL.addAll(SoundOutput.getPauseBetweenSymbols());
                    break;
                case SEPARATOR_CHARACTER:
                    bufferLL.addAll(SoundOutput.getPauseBetweenCharacters());
                    break;
                case SEPARATOR_WORD:
                    bufferLL.addAll(SoundOutput.getPauseBetweenWords());
                    break;
                default:
                    System.out.println(c);
            }
        }
        // add final pause to prevent clicking sound
        bufferLL.addAll(SoundOutput.getPauseBetweenSymbols());

        byte[] buffer = new byte[bufferLL.size()];
        for (int i = 0; i < bufferLL.size(); i++){
            buffer[i] = bufferLL.get(i);
        }

        try {
            SoundOutput.toSound(buffer, this);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
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
