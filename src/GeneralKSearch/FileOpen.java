// not please control the i indexes 
package GeneralKSearch;

import java.awt.BorderLayout;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.io.IOException;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFrame;

public final class FileOpen extends JFrame {

    ArrayList<Double> p_j_ArrayList = new ArrayList<Double>();
    ArrayList<Double> p_i_star_ArrayList = new ArrayList<Double>();
    ArrayList<Integer> index_ArrayList = new ArrayList<Integer>();
    ArrayList<Double> p_j_max_Array = new ArrayList<Double>();
    int parseInt_M;
    int parseInt_m;
    int parseInt_k;
    int parseInt_n;
    int j_current_period;
    double alpha_mid;
    int sum_i_index_star;
    private JTextArea outputArea; // used for output
    private JScrollPane scrollPane; // used to provide scrolling to output

// set up GUI
    public FileOpen() throws IOException, BiffException {

        super("Open Excel File");


        outputArea = new JTextArea();

// add outputArea to scrollPane
        scrollPane = new JScrollPane(outputArea);

        add(scrollPane, BorderLayout.CENTER); // add scrollPane to GUI

        setSize(400, 400); // set GUI size
        setVisible(true); // display GUI

        read();
        findAlpha(parseInt_k, parseInt_m, parseInt_M);

        p_star_i_find(alpha_mid, parseInt_k, parseInt_m, parseInt_M);
        corresponding_i(parseInt_k, parseInt_n);
        max_Value_p_j_find(p_j_ArrayList, parseInt_n);
        algorithm_DET(parseInt_n, parseInt_k);


    } // end FileOpen constructor

// allow user to specify file inputWorkbook
    private File getFile() {
// display file dialog, so user can choose file to open
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setFileSelectionMode(
                JFileChooser.FILES_ONLY);// changed. 
        int result = fileChooser.showOpenDialog(this);

// if user clicked Cancel button on dialog, return
        if (result == JFileChooser.CANCEL_OPTION) {
            System.exit(1);
        }

        File fileName = fileChooser.getSelectedFile(); // get selected file
// display error if invalid
        if ((fileName == null) || (fileName.getName().equals(""))) {
            JOptionPane.showMessageDialog(this, "Invalid File Name",
                    "Invalid File Name", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } // end if

        return fileName;
    } // end method getFile

    public void read() throws IOException, BiffException {
        File inputWorkbook = getFile();
        if (!inputWorkbook.exists()) // if inputWorkbook exists, output information about it
        {
            JOptionPane.showMessageDialog(this, inputWorkbook
                    + " does not exist.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        Workbook w;
        w = Workbook.getWorkbook(inputWorkbook);
// Get the first sheet
        Sheet sheet = w.getSheet(0);
        for (int i = 1; i < sheet.getRows(); i++) {//we do not need the first row

            Cell cell_col = sheet.getCell(1, i);

            if (cell_col.getType() == CellType.NUMBER) {
//                        System.out.println("I got a number "
//								+ cell_col.getContents());
                outputArea.append(cell_col.getContents() + "\n");
                p_j_ArrayList.add(i - 1, Double.parseDouble(cell_col.getContents()));
//here are added the contents from index zero                 
            }
        }

//     outputArea.append("M maximum is:" + sheet.getCell(5,1).getContents() + "\n"); 
        parseInt_M = Integer.parseInt(sheet.getCell(5, 1).getContents());
//     outputArea.append("m minimum is:"+sheet.getCell(4,1).getContents()+ "\n");
        parseInt_m = Integer.parseInt(sheet.getCell(4, 1).getContents());
//     outputArea.append("number of Periods is:"+sheet.getCell(3,1).getContents()+ "\n");
        parseInt_n = Integer.parseInt(sheet.getCell(3, 1).getContents());
//     outputArea.append("number of Units is:"+sheet.getCell(2,1).getContents()+ "\n");
        parseInt_k = Integer.parseInt(sheet.getCell(2, 1).getContents());

    }// read method

    public double findAlpha(int k, int m, int M) {
        double a, b;
//        final double precision = 0.00001; 
        double outputMid, outputA;

        a = 1;
        b = M / m;
        alpha_mid = (a + b) / 2;
        outputMid = (alpha_mid - 1) * Math.pow((1 + alpha_mid / k), k) - (M / m - 1);
        if (outputMid > 0) {
            b = alpha_mid;
        } else {
            a = alpha_mid;
        }
        alpha_mid = (a + b) / 2;
// we know that F(1) is < 0 and F (M/m) is > 0. 
// here wehave new a and b. 
        for (int i = 0; i < 200; i++) {
            outputMid = (alpha_mid - 1) * Math.pow((1 + alpha_mid / k), k) - (M / m - 1);
            outputA = (a - 1) * Math.pow((1 + a / k), k) - (M / m - 1);
            if (outputMid * outputA > 0) {
                a = alpha_mid;
            } else {
                b = alpha_mid;
            }

            alpha_mid = (a + b) / 2;
        }
//          System.out.print(alpha_mid);
        return alpha_mid;
    } //findAlpha method

    public ArrayList p_star_i_find(double mid, int k, int m, int M) {
        p_i_star_ArrayList.add(0, (double) 0);// p*0 in index 0

        p_i_star_ArrayList.add(1, mid * m);// p*1
        double before_p_star_i = 0;
        for (int i = 2; i <= k; i++) {
            for (int l = 1; l < i; l++) {
                before_p_star_i += p_i_star_ArrayList.get(i - l);
            }//for
            p_i_star_ArrayList.add(i, (mid / k) * (before_p_star_i + (k - i + 1) * m));// p*i
            before_p_star_i = 0;
        }     //for 
        p_i_star_ArrayList.add(k + 1, (double) M);// p*k+1 in index k

//          System.out.println("\n" + p_i_star_ArrayList.get(0)); START FROM ZERO 
//           System.out.println("\n" + p_i_star_ArrayList.get(1));
//            System.out.println("\n" + p_i_star_ArrayList.get(k+1));
//             System.out.println("\n" + p_i_star_ArrayList.get(2));
//              System.out.println("\n" + p_i_star_ArrayList.get(3));
//               System.out.println("\n is k plus 1" + p_i_star_ArrayList.get(4));


        return p_i_star_ArrayList;
    }// p_find method 

    public void corresponding_i(int k_units, int n) {
//        ArrayList<Integer> index_ArrayList = new ArrayList<Integer>();
        for (int j = 0; j < n; j++) {
            for (int i = 1; i <= k_units + 1; i++) {
                if ((p_j_ArrayList.get(j) - p_i_star_ArrayList.get(i)) < 0) {
//                System.out.print("\n KILL   "+(i-1));
                    index_ArrayList.add(i - 1);
                    System.out.println("  ***ARRAY  " + index_ArrayList.get(j));
                    break;
                }// if 
            }//for 

        }//for   
    }

    public void max_Value_p_j_find(ArrayList pjArrayList, int n) {
        double maxValue_p_j = 0;
        p_j_max_Array.add((double) 0);
        for (int k = 1; k < n - 1; k++) {
            maxValue_p_j = p_j_ArrayList.get(0);

            for (int m = 1; m < k - 1; m++) {
                if (p_j_ArrayList.get(m) > maxValue_p_j) {
                    maxValue_p_j = p_j_ArrayList.get(m);

                } //if 
            }//for   
            p_j_max_Array.add(maxValue_p_j);
//        System.out.println(" \n HHHHHHHHHHHHH   " + p_j_max_Array.get(k-2));
// n is equal to 5 
        }//for
//     p_j_max_Array.add((double)0);
        p_j_max_Array.add((double) 0);
//      System.out.println(" \n HHHHHHHHHHHHH   " + p_j_max_Array.get(3));
//      System.out.println(" \n HHHHHHHHHHHHH   " + p_j_max_Array.get(4));
    }//max 

    public double algorithm_DET(int n, int k_units) {

        int reminder =0;
        double total_revenue = 0;
        //  double maxValue_p_j;
        int i_index_star_before = 0;
        for (int j = 0; j < n; j++) { /* j is the jth period */
            if (j == 0) {
                total_revenue += index_ArrayList.get(0) * p_j_ArrayList.get(0);
                System.out.println(" \n TOTAL " + total_revenue);
//                    System.out.println("\n INDEX " + index_ArrayList.get(0));
//                    System.out.println("\n JJJ " + p_j_ArrayList.get(0));
                i_index_star_before += index_ArrayList.get(0);
                System.out.println(" \n BEFORE " + i_index_star_before);
                reminder = k_units- index_ArrayList.get(0);
                System.out.println(" \n REMINDER  " + reminder);
            }
//                else {

            if ((p_i_star_ArrayList.get(index_ArrayList.get(j)) > p_j_max_Array.get(j))
                    && (j != (n - 1)) && (j != 0)&& (reminder > 0)) {
//            System.out.println("\n \n AAAAAAAAA  " + p_i_star_ArrayList.get(j));
                System.out.println("\n \n BBBBBBBBBBB  " + p_i_star_ArrayList.get(index_ArrayList.get(j)) + " __" + j + " __" + index_ArrayList.get(j));
                System.out.println("\n  \n CCCCCCCCCCCCC  " + p_j_max_Array.get(j) + " __" + j);
//            System.out.println("\n \n DDDDDDDDDDDDD  " + p_j_ArrayList.get(j));
                total_revenue += (index_ArrayList.get(j) - i_index_star_before) * p_j_ArrayList.get(j);
                System.out.println(" \n TOTAL TOTAL " + total_revenue);
                i_index_star_before += (index_ArrayList.get(j) - i_index_star_before);
                System.out.println(" \n BEFORE BEFORE  " + i_index_star_before);
                reminder = k_units - i_index_star_before;
                System.out.println(" \n REMINDER  REMINDER " + reminder);
            }//else if 
//                }//else 

//                else
            if (j == (n - 1) && (reminder > 0)) {
                total_revenue += p_j_ArrayList.get(n - 1) * reminder;
                System.out.println("\n  \n >>>>>>>>>>>>>>>  " + p_j_ArrayList.get(n - 1) + " __" + j);
            }//elseif 
        }//for  

//        System.out.print("\n ERGEBNISS       " + total_revenue);
        outputArea.append("The Total revenue obtained by DET is " + total_revenue);

        return total_revenue;


    }//method DET 
}// end class FileOpen 