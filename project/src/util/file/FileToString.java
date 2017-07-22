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


package util.file;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileToString {

    /**
     * Saves a sting to a file
     * @param filename  filename write to
     * @param content   string to write in file
     * @return if successfully saved
     */
    public static boolean stringToFile(String filename, String content)
    {
        try {
            Files.write(Paths.get(filename),content.getBytes(StandardCharsets.UTF_8));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Load a string from a file
     * @param filename file to read
     * @return read string
     */
	public static String loadFromFile(String filename)
    {
        try {
            return  new String(Files.readAllBytes(Paths.get(filename)),StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "";
        }
    }
}
