package GeneralKSearch;
//import javax.swing.*;
import java.util.*;
import java.io.*;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class OpenTextFileIntoJTextArea {
// InputStream is = this.getClass().getResourceAsStream( "picture.gif" );
    public static void readAboutFile(String S, String Titel){
//      public static void readAboutFile(String Titel){
        try {
            //Read a text file named MyTextFile.txt
            //You can download this text file at the link below
            //You also can change to other text file by change it's name
            //Don't forget to put .txt
            //If your text file locate at other location,
            //you must put it full location.
            //For example :
            //C:\\Documents and Settings\\evergreen\\MyTextFile.txt
         InputStream is = OpenTextFileIntoJTextArea.class.getResourceAsStream(S);
//            FileReader readTextFile = new FileReader(S);

            //Create a scanner object from FileReader
//            Scanner fileReaderScan = new Scanner(readTextFile);
            Scanner fileReaderScan = new Scanner(is);

            //Create a String that will store all text in the text file
            String storeAllString = "";

            //Put all text from text file into created String
            while (fileReaderScan.hasNextLine()) {
                String temp = fileReaderScan.nextLine() + "\n";

                storeAllString = storeAllString + temp;
            }

            //Set JTextArea text to created String
            JTextArea textArea = new JTextArea(storeAllString);

            //Set JTextArea to line wrap
            textArea.setLineWrap(true);

            //Set JTextArea text to word wrap
            textArea.setWrapStyleWord(true);

            //Create scrollbar for text area
            JScrollPane scrollBarForTextArea = new JScrollPane(textArea,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            //Create a window using JFrame with title ( Open text file into JTextArea )
            JFrame frame = new JFrame(Titel);

            //Add scrollbar into JFrame
            frame.add(scrollBarForTextArea);

            //Set default close operation for JFrame
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //Set JFrame size
            frame.setSize(500, 500);

            //Make JFrame locate at center on screen
            frame.setLocationRelativeTo(null); // location help and about

            //Make JFrame visible
            frame.setVisible(true);
        } catch (Exception exception) {
            //Print Error in file processing if it can't process your text file
            System.out.println("Error in file processing");
        }
    }
}