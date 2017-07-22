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


package morse.alphabet;

import util.log.Log;

import java.util.HashMap;

public class MorseAlphabet {

    private HashMap<Character,String> textToMorse;
    private HashMap<String,Character> morseToText;

    public MorseAlphabet()
    {
        textToMorse = new HashMap<>(100);
        morseToText = new HashMap<>(100);

        // A-Z
        textToMorse.put('A',"· −");
        textToMorse.put('B',"− · · ·");
        textToMorse.put('C',"− · − ·");
        textToMorse.put('D',"− · ·");
        textToMorse.put('E',"·");
        textToMorse.put('F',"· · − ·");
        textToMorse.put('G',"− − ·");
        textToMorse.put('H',"· · · ·");
        textToMorse.put('I',"· ·");
        textToMorse.put('J',"· − − −");
        textToMorse.put('K',"− · −");
        textToMorse.put('L',"· − · ·");
        textToMorse.put('M',"− −");
        textToMorse.put('N',"− ·");
        textToMorse.put('O',"− − −");
        textToMorse.put('P',"· − − ·");
        textToMorse.put('Q',"− − · −");
        textToMorse.put('R',"· − ·");
        textToMorse.put('S',"· · ·");
        textToMorse.put('T',"−");
        textToMorse.put('U',"· · −");
        textToMorse.put('V',"· · · −");
        textToMorse.put('W',"· − −");
        textToMorse.put('X',"− · · −");
        textToMorse.put('Y',"− · − −");
        textToMorse.put('Z',"− − · ·");

        // 0-9
        textToMorse.put('1',"· − − − −");
        textToMorse.put('2',"· · − − −");
        textToMorse.put('3',"· · · − −");
        textToMorse.put('4',"· · · · −");
        textToMorse.put('5',"· · · · ·");
        textToMorse.put('6',"− · · · ·");
        textToMorse.put('7',"− − · · ·");
        textToMorse.put('8',"− − − · ·");
        textToMorse.put('9',"− − − − ·");
        textToMorse.put('0',"− − − − −");

        // SpecialChars
        textToMorse.put('À',"· − − · −");
        textToMorse.put('Å',"· − − · −");
        textToMorse.put('È',"· − · · −");
        textToMorse.put('É',"· · − · ·");
        textToMorse.put('Ñ',"− − · − −");

//        textToMorse.put('Ä',"· − · −"); // like \n !
        textToMorse.put('Ö',"− − − ·");
        textToMorse.put('Ü',"· · − −");
        textToMorse.put('ß',"· · · − − · ·");
        // textToMorse.put('CH',"− − − −");
        textToMorse.put('.',"· − · − · −");
        textToMorse.put(',',"− − · · − −");
        textToMorse.put(':',"− − − · · ·");
        textToMorse.put(';',"− · − · − ·");
        textToMorse.put('?',"· · − − · ·");
        textToMorse.put('-',"− · · · · −");
        textToMorse.put('_',"· · − − · −");
        textToMorse.put('(',"− · − − ·");
        textToMorse.put(')',"− · − − · −");
        textToMorse.put('\'',"· − − − − ·");
        textToMorse.put('=',"− · · · −");
        textToMorse.put('+',"· − · − ·");
        textToMorse.put('/',"− · · − ·");
        textToMorse.put('@',"· − − · − ·");

        // Signals
        textToMorse.put('\n',"· − · −"); // Neue Zeile oder Ä
        textToMorse.put('|',"· · · − ·"); // verstanden
        textToMorse.put('!',"· · · · · · · ·"); // Fehler; Irrung; Wiederholung ab letztem vollständigen Wort
        // textToMorse.put('§',"− · · · −"); // Pause
        // textToMorse.put('[',"− · − · −"); // Spruchanfang
        // textToMorse.put(']',"· − · − ·"); // Spruchende
        // textToMorse.put('§',""); // Verkehrsende

        textToMorse.put(' ',"|"); // ignore word separator


        // A-Z
        morseToText.put("· −",'A');
        morseToText.put("− · · ·",'B');
        morseToText.put("− · − ·",'C');
        morseToText.put("− · ·",'D');
        morseToText.put("·",'E');
        morseToText.put("· · − ·",'F');
        morseToText.put("− − ·",'G');
        morseToText.put("· · · ·",'H');
        morseToText.put("· ·",'I');
        morseToText.put("· − − −",'J');
        morseToText.put("− · −",'K');
        morseToText.put("· − · ·",'L');
        morseToText.put("− −",'M');
        morseToText.put("− ·",'N');
        morseToText.put("− − −",'O');
        morseToText.put("· − − ·",'P');
        morseToText.put("− − · −",'Q');
        morseToText.put("· − ·",'R');
        morseToText.put("· · ·",'S');
        morseToText.put("−",'T');
        morseToText.put("· · −",'U');
        morseToText.put("· · · −",'V');
        morseToText.put("· − −",'W');
        morseToText.put("− · · −",'X');
        morseToText.put("− · − −",'Y');
        morseToText.put("− − · ·",'Z');

        // 0-9
        morseToText.put("· − − − −",'1');
        morseToText.put("· · − − −",'2');
        morseToText.put("· · · − −",'3');
        morseToText.put("· · · · −",'4');
        morseToText.put("· · · · ·",'5');
        morseToText.put("− · · · ·",'6');
        morseToText.put("− − · · ·",'7');
        morseToText.put("− − − · ·",'8');
        morseToText.put("− − − − ·",'9');
        morseToText.put("− − − − −",'0');

        // SpecialChars

        morseToText.put("· − − · −",'À');
        morseToText.put("· − − · −",'Å');
        morseToText.put("· − · · −",'È');
        morseToText.put("· · − · ·",'É');
        morseToText.put("− − · − −",'Ñ');

//        morseToText.put("· − · −",'Ä');  // like \n !
        morseToText.put("− − − ·",'Ö');
        morseToText.put("· · − −",'Ü');
        morseToText.put("· · · − − · ·",'ß');
        // morseToText.put("− − − −",'CH');
        morseToText.put("· − · − · −",'.');
        morseToText.put("− − · · − −",',');
        morseToText.put("− − − · · ·",':');
        morseToText.put("− · − · − ·",';');
        morseToText.put("· · − − · ·",'?');
        morseToText.put("− · · · · −",'-');
        morseToText.put("· · − − · −",'_');
        morseToText.put("− · − − ·",'(');
        morseToText.put("− · − − · −",')');
        morseToText.put("· − − − − ·",'\'');
        morseToText.put("− · · · −",'=');
        morseToText.put("· − · − ·",'+');
        morseToText.put("− · · − ·",'/');
        morseToText.put("· − − · − ·",'@');

        // Signals
        morseToText.put("· − · −",'\n'); // Neue Zeile oder Ä
        morseToText.put("· · · − ·",'|'); // verstanden
        morseToText.put("· · · · · · · ·",'!'); // Fehler; Irrung; Wiederholung ab letztem vollständigen Wort
        //morseToText.put("",'§'); // Verkehrsende
        //morseToText.put("− · · · −",'§'); // Pause
        //morseToText.put("− · − · −",'['); // Spruchanfang
        //morseToText.put("· − · − ·",']'); // Spruchende

        morseToText.put("|",' '); // ignore word separator
    }



    /**
     * String text to morse text
     * @param in string text
     * @return morse text
     */
    public static String toMorse(String in) {
        StringBuilder sb = new StringBuilder();

        for (Character c : in.toCharArray())
        {
            if(c == ' ') {
                sb.setLength(sb.length() - 1);

                try {
                    sb.append(MorseAlphabet.toMorse(c));
                }
                catch (Exception ignored) {
                    Log.logWarning("'" + c + "' has failed to be translated!");
                }
            } else {
                try {
                    //Character seperator
                    sb.append(MorseAlphabet.toMorse(c)).append("!");
                }
                catch (Exception ignored) {
                    Log.logWarning("'" + c + "' has failed to be translated!");
                }
            }
        }
        sb.setLength(sb.length() - 1);

        return sb.toString();
    }

    /**
     * Calls the hashmap
     * @param in Character
     * @return Morse text
     * @throws Exception    if not found
     */
    public static String toMorse(Character in) throws Exception{
        MorseAlphabet m = new MorseAlphabet();

        String result = m.textToMorse.get(in);

        if(result == null)
        {
            throw  new Exception("'" + in + "' has failed to be translated!");
        }

        return result;
    }

    /**
     * Morse text to string text
     * @param in morse text
     * @return  string text
     */
    public static String toText(String in) {
        StringBuilder sb = new StringBuilder();

        for (String morseChar : in.replaceAll("\\|", "!\\|!").split("!"))
        {
            try {
                sb.append(MorseAlphabet.toTextChar(morseChar));
            } catch (Exception e) {
                Log.logWarning("'" + morseChar + "' has not be translated");
            }
        }
        return sb.toString();
    }

    /**
     * Call text char from hashmap
     * @param in   Morse text
     * @return Text char
     * @throws Exception    if not found
     */
    private static char toTextChar(String in) throws Exception{
        MorseAlphabet m = new MorseAlphabet();

        Character result = m.morseToText.get(in);

        if(result == null)
        {
            throw new Exception("'" + in + "' has not be translated");
        }
        return result;
    }

    /**
     * Checks if given sting is a morse code
     * @param in text
     * @return is morse code
     */
    public static boolean isMorseCode(String in) {
        return in != null && in.matches("[−· !|.\\-_]+");
    }

    /**
     * Removes all not include text elements
     * @param in text
     * @return text only with valid elements
     */
    public static String filterInputText(String in) {
        if(in != null)
            return in.toUpperCase().replaceAll("[^A-Z0-9 ÀÅÈÉÑÖÜß.,:;?\\-_()=+/@\\n|!]", "").trim();
        return null;
    }

    /**
     * Inserts spaces for exmaple "..." -> ". . ."
     * @param x string in
     * @return string out with added spaces at each char of the string
     */
    public static String insertSpaces(String x)
    {
        char[] in = x.toCharArray();
        StringBuilder sb = new StringBuilder(in.length * 2);

        for (char t: in) {
            if(t == ' ')
                sb.append(" ");
            else
                sb.append(t).append(" ");
        }

        return sb.toString().trim();

    }
}
