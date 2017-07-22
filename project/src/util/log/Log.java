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

package util.log;

import java.util.Date;

public class Log {
    private static StringBuilder sb = new StringBuilder();
    private static boolean haslog = false;

    public static void logError(String errorMessage)
    {
        sb.append("Error @(").append(new Date().toString()).append("): ").append(errorMessage).append("\n");
        haslog = true;
    }

    public static void logWarning(String message)
    {
        sb.append("Warning @(").append(new Date().toString()).append("): ").append(message).append("\n");
        haslog = true;
    }

    public static void logInfo(String info)
    {
        sb.append("Info @(").append(new Date().toString()).append("): ").append(info).append("\n");
        haslog = true;
    }

    public static String getLog()
    {

        String out = sb.toString().trim();
        sb.setLength(0);
        haslog = false;

        // unfiltered log on console
        //System.out.println(out);

        // Remove timestamp for user
        return out.replaceAll(" @\\(.+\\)","");
    }

    public static boolean haslog(){
        return haslog;
    }
}
