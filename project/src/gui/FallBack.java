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

package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Displays error that FX was not found.
 */
public class FallBack extends JFrame {

    public FallBack() {
        super();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        int frameWidth = 300;
        int frameHeight = 280;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle("JavaFX not found");
        setResizable(false);
        Container cp = getContentPane();
        cp.setLayout(null);

        JTextArea ta_message = new JTextArea("");
        JScrollPane tasp_message = new JScrollPane(ta_message);
        tasp_message.setBounds(10, 10, 280, 200);
        ta_message.setText("# Requirements #\n\nSorry, this software is designed to use Java FX.\n\nPlease check that you use a current version\nof JRE 8+ and JavaFX.\n\nOn Linux you can install oracle java or openjfx for\nJavaFX support.\n\nOn Ubuntu or Debian you can use\n\"sudo apt-get install openjfx\".");
        ta_message.setEditable(false);
        ta_message.setLineWrap(true);
        cp.add(tasp_message);

        JButton b_exit = new JButton();
        b_exit.setBounds(64, 220, 145, 25);
        b_exit.setText("Exit");
        b_exit.setMargin(new Insets(2, 2, 2, 2));
        b_exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                exitButton_ActionPerformed(evt);
            }
        });
        cp.add(b_exit);

        setVisible(true);
    }

    public static void main(String[] args) {
        new FallBack();
    }

    private void exitButton_ActionPerformed(ActionEvent evt) {
        System.exit(0);
    }
}
