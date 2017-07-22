
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

package morse;

import javafx.scene.image.ImageView;
import morse.alphabet.MorseAlphabet;
import morse.convert.MorseData;
import morse.convert.morsetypes.Light;
import morse.convert.morsetypes.Sound;
import util.log.Log;

import static morse.convert.MorseData.getInputTypeObject;
import static morse.convert.MorseData.getOutputTypeObject;

public class MorseBackend {

    private String outputText;
    private String cacheMorse;
    private String cacheText;
    private MorseData intermediate;

    public MorseBackend(String inputText, String inputType, String outputType) throws Exception {

        /* get the classes of the chosen (string) types */
        MorseData inputObject = getInputTypeObject(inputType);
        MorseData outputObject = getOutputTypeObject(outputType);
        if(inputObject == null || outputObject == null)
        {
            Log.logError("inputType or outputType failed!");
            return;
        }
        intermediate = inputObject.createIntermediate(inputText);
        outputText = outputObject.get(intermediate);

        if(outputText == null){
            Log.logError(inputText + " can't be translated");
            throw new Exception(inputText + " can't be translated");
        }


        // Cache the inputs
        switch (outputType) {
            case MorseData.TEXT_AUTO:
                if (MorseAlphabet.isMorseCode(outputText)) {
                    cacheMorse = outputText;
                }
                else
                {
                    cacheText = outputText;
                }
                break;
            case MorseData.TEXT_MORSE:
                if (MorseAlphabet.isMorseCode(outputText)) {
                    cacheMorse = outputText;
                } else {
                    Log.logError(inputText + " seems to be no morse code");
                    throw new Exception(inputText + " seems to be no morse code");
                }
                break;
            default:
                cacheText = outputText;
                break;
        }
    }

    public String getOutput() {
        return outputText;
    }

    public String getOutput(String output) throws Exception {
        switch (output)
        {
            case MorseData.TEXT_AUTO:
                return getOutputTypeObject(MorseData.TEXT_AUTO).get(intermediate);
            case MorseData.TEXT_MORSE:
                if(cacheMorse == null)
                {
                    MorseData outputObject = getOutputTypeObject(MorseData.TEXT_MORSE);
                    cacheMorse = outputObject.get(intermediate);
                }
                return cacheMorse;
            case MorseData.TEXT_TEXT:
                if(cacheText == null)
                {
                    MorseData outputObject = getOutputTypeObject(MorseData.TEXT_TEXT);
                    cacheText = outputObject.get(intermediate);
                }
                return cacheText;

                default:
                    Log.logError("Unknown output type!");
                    throw new Exception("Unknown output type!");
        }
    }

    public void toSound()
    {
        Sound.toSound(intermediate.getZwischendarstellung());
    }

    public void toLight(ImageView lamp)
    {
        Light.toLight(intermediate.getZwischendarstellung(),lamp);
    }
}
