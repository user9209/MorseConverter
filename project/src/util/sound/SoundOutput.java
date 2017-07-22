
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


import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SoundOutput {
    private static int pitch = 450; // in Hz
    private static int timeDit = 50; // in ms
    private static double volume = 0.5; // 0 -> 1
    private static float SAMPLE_RATE = 2000f; // in Hz

    /* These values should possibly stay fixed (as they are defined in the morse protocol)
     *   to avoid magic numbers, they are listed */
    private static final int DAH_IN_DITS = 3;
    private static final int SYMBOL_PAUSE_IN_DITS = 1;
    private static final int CHARACTER_PAUSE_IN_DITS = 3;
    private static final int WORD_PAUSE_IN_DITS = 7;

    public static int getPitch() { return pitch; }

    public static int getTimeDit() { return timeDit; }

    public static double getVolume() { return volume; }

    public static void setPitch(int pitch) {
        SoundOutput.pitch = pitch;
    }

    public static void setTimeDit(int timeDit) {
        SoundOutput.timeDit = timeDit;
    }

    public static void setVolume(double volume) {
        SoundOutput.volume = volume;
    }

    private static List<Byte> toneBytes(int hz, int msecs) {
        int sampleNo = (int) Math.floor(msecs * SAMPLE_RATE / 1000.0);
        List<Byte> sampleBuffer = new ArrayList<>();
        for (int i=0; i < sampleNo; i++) {
            double angle = 2.0 * Math.PI * hz * i / SAMPLE_RATE;
            sampleBuffer.add( (byte) (Math.sin(angle) * 127.0 * volume));
        }
        return sampleBuffer;
    }

    public static List<Byte> getDot() {
        return SoundOutput.toneBytes(pitch, timeDit);
    }

    public static List<Byte> getDash() {
        return SoundOutput.toneBytes(pitch,DAH_IN_DITS*timeDit);
    }

    public static List<Byte> getPauseBetweenSymbols() {
        int sampleNo = (int) Math.floor(SAMPLE_RATE * SYMBOL_PAUSE_IN_DITS * timeDit/1000.0);
        List<Byte> samples = new ArrayList<>(Collections.nCopies(sampleNo, (byte) 0));
        return samples;
    }

    public static List<Byte> getPauseBetweenCharacters() {
        int sampleNo = (int) Math.floor(SAMPLE_RATE * CHARACTER_PAUSE_IN_DITS * timeDit/1000.0);
        List<Byte> samples = new ArrayList<>(Collections.nCopies(sampleNo, (byte) 0));
        return samples;
    }

    public static List<Byte> getPauseBetweenWords() {
        int sampleNo = (int) Math.floor(SAMPLE_RATE * WORD_PAUSE_IN_DITS * timeDit/1000.0);
        List<Byte> samples = new ArrayList<>(Collections.nCopies(sampleNo, (byte) 0));
        return samples;
    }

    public static void toSound(byte[] sampleBuffer, SoundThread thread) throws LineUnavailableException {
        AudioFormat af = new AudioFormat(SAMPLE_RATE,8,1,true,false);
        SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
        sdl.open(af);
        sdl.start();

        int nextByteToWrite = 0; // all i < nextByteToWrite: already written to Line
        int totalBytes = sampleBuffer.length;
        while(nextByteToWrite < totalBytes && thread.isRunningThread()){
            // get space in buffer (if less bytes left to write, take that number -> Math.min)
            int spaceInBuffer = Math.min(sdl.available(), totalBytes-nextByteToWrite);
            // write that many bytes to buffer
            sdl.write(sampleBuffer,nextByteToWrite,spaceInBuffer);
            // move to next byte after
            nextByteToWrite += spaceInBuffer;
        }

        // fix sound click output
        sdl.write(new byte[]{(byte) 0,(byte) 0,(byte) 0,(byte) 0},0,4);

        sdl.drain();
        sdl.stop();
        sdl.close();
    }
}