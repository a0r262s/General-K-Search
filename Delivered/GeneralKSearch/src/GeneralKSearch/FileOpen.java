// not please control the i indexes 
package GeneralKSearch;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.IOException;
import jxl.read.biff.BiffException;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import javax.swing.JFrame;
//----
import jxl.*;
import jxl.write.*;

public final class FileOpen extends JFrame {

//    public File inputWorkbook;//getFile();
//    public File outputWorkbook;
//    public  Workbook w;
//    public  WritableWorkbook w2;//***added 
    static ArrayList<Double> p_j_ArrayList = new ArrayList<Double>();
    static ArrayList<Double> p_i_star_ArrayList = new ArrayList<Double>();
    static ArrayList<Integer> index_ArrayList = new ArrayList<Integer>();
    static ArrayList<Double> p_j_max_Array = new ArrayList<Double>();
    int parseInt_M;
    int parseInt_m;
    int parseInt_k;
    static int parseInt_n;
    int j_current_period;
    double alpha_mid;
    int sum_i_index_star;
    static double total_revenue = 0;
    public File fileName;
    public File reused;
//    private JTextArea outputArea; // used for output
//    private JScrollPane scrollPane; // used to provide scrolling to output

// set up GUI
    public FileOpen() throws IOException, BiffException, WriteException {

        super("Open Excel File");


//        outputArea = new JTextArea();
//
//// add outputArea to scrollPane
//        scrollPane = new JScrollPane(outputArea);
//
//        add(scrollPane, BorderLayout.CENTER); // add scrollPane to GUI
//
//        setSize(400, 400); // set GUI size
//        setVisible(true); // display GUI

//getFile();



    } // end FileOpen constructor

// allow user to specify file inputWorkbook
    public File getFile() {
// display file dialog, so user can choose file to open
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setFileSelectionMode(
                JFileChooser.FILES_ONLY);// changed. 
        int result = fileChooser.showOpenDialog(this);

// if user clicked Cancel button on dialog, return
        if (result == JFileChooser.CANCEL_OPTION) {
            System.exit(1);
        }

        fileName = fileChooser.getSelectedFile(); // get selected file
// display error if invalid
        if ((fileName == null) || (fileName.getName().equals(""))) {
            JOptionPane.showMessageDialog(this, "Invalid File Name",
                    "Invalid File Name", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } // end if

       
        return fileName;

    } // end method getFile
    

    public void read(File infgetread) throws IOException, BiffException, WriteException {
        File inputWorkbook = infgetread;//getFile();
//File outputWorkbook = inputWorkbook;



//        File outputWorkbook = inputWorkbook;
        if (!inputWorkbook.exists()) // if inputWorkbook exists, output information about it
        {
            JOptionPane.showMessageDialog(this, inputWorkbook
                    + " does not exist.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        Workbook w = Workbook.getWorkbook(inputWorkbook);
//w2= Workbook.createWorkbook(outputWorkbook, w);
//        Workbook w = Workbook.getWorkbook(inputWorkbook);
//        WritableWorkbook w2 = Workbook.createWorkbook(outputWorkbook, w);//***added 

//        new File("C:/Users/a0262/Desktop/Test2.xls"

// Get the first sheet
        Sheet sheet = w.getSheet(0);
//        Sheet sheet1 = w.getSheet(1);
        for (int i = 1; i < sheet.getRows(); i++) {//we do not need the first row

            Cell cell_col = sheet.getCell(1, i);

            if (cell_col.getType() == CellType.NUMBER) {
//                outputArea.append(cell_col.getContents() + "\n");
                p_j_ArrayList.add(i - 1, Double.parseDouble(cell_col.getContents()));

            }//if
        }

        parseInt_M = Integer.parseInt(sheet.getCell(5, 1).getContents());

        parseInt_m = Integer.parseInt(sheet.getCell(4, 1).getContents());

        parseInt_n = Integer.parseInt(sheet.getCell(3, 1).getContents());

        parseInt_k = Integer.parseInt(sheet.getCell(2, 1).getContents());
        //-----********--------
        findAlpha(parseInt_k, parseInt_m, parseInt_M);
        p_star_i_find(alpha_mid, parseInt_k, parseInt_m, parseInt_M);
        corresponding_i(parseInt_k, parseInt_n);
        max_Value_p_j_find(p_j_ArrayList, parseInt_n);

//        w.close();
//         System.out.printf("\n \n §§§§§§§§§§§§§§§§ " + infgetread+ "\n");
        modifySecond(infgetread);
    }// read method

    public double findAlpha(int k, int m, int M) {
        double a, b;
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

        return alpha_mid;
    } //findAlpha method

    public static ArrayList p_star_i_find(double mid, int k, int m, int M) {
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
        return p_i_star_ArrayList;
    }// p_find method 

    public static void corresponding_i(int k_units, int n) {
//        ArrayList<Integer> index_ArrayList = new ArrayList<Integer>();
        for (int j = 0; j < n; j++) {
            for (int i = 1; i <= k_units + 1; i++) {
                if ((p_j_ArrayList.get(j) - p_i_star_ArrayList.get(i)) < 0) {
//                System.out.print("\n KILL   "+(i-1));
                    index_ArrayList.add(i - 1);
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

            for (int m = 1; m <= k - 1; m++) {
                if (p_j_ArrayList.get(m) > maxValue_p_j) {
                    maxValue_p_j = p_j_ArrayList.get(m);

                } //if 
            }//for   
            p_j_max_Array.add(maxValue_p_j);

        }//for
//     p_j_max_Array.add((double)0);
        p_j_max_Array.add((double) 0);

    }//max 

    public static double algorithm_DET(int n, int k_units) {

        int reminder = 0;

        //  double maxValue_p_j;
        int i_index_star_before = 0;
        for (int j = 0; j < n; j++) { /* j is the jth period */
            if (j == 0) {
                total_revenue += index_ArrayList.get(0) * p_j_ArrayList.get(0);
                i_index_star_before += index_ArrayList.get(0);
                reminder = k_units - index_ArrayList.get(0);
            }
//                else {
            if ((p_i_star_ArrayList.get(index_ArrayList.get(j)) > p_j_max_Array.get(j))
                    && (j != (n - 1)) && (j != 0) && (reminder > 0)) {

                total_revenue += (index_ArrayList.get(j) - i_index_star_before) * p_j_ArrayList.get(j);
                i_index_star_before += (index_ArrayList.get(j) - i_index_star_before);
                reminder = k_units - i_index_star_before;

            }//else if 
//                }//else 

//                else
            if (j == (n - 1) && (reminder > 0)) {
                total_revenue += p_j_ArrayList.get(n - 1) * reminder;

            }//if 
        }//for  

        return total_revenue;
    }//method DET 

    void modifyFirst(File ingetf, int d1, int d2, int d3, int d4) 
            throws WriteException, IOException, BiffException {//R
//        File inputWorkbook = fileName;
//        System.out.println(inputWorkbook);
//        File outputWorkbook = inputWorkbook;
//        System.out.println("    MODIFY FiRST    "+ingetf);
//        if (!inputWorkbook.exists()) // if inputWorkbook exists, output information about it
//        {
//            JOptionPane.showMessageDialog(this, inputWorkbook
//                    + " does not exist.", "ERROR", JOptionPane.ERROR_MESSAGE);
//        }

//        Workbook w = Workbook.getWorkbook(inputWorkbook);
        Workbook w = Workbook.getWorkbook(ingetf);
        WritableWorkbook w2 = Workbook.createWorkbook(ingetf, w);//***added 

        WritableSheet sheet = w2.getSheet(0);//R
//        WritableCell cell = null;
////    CellFormat cf = null;
//        Label l = null;
//        WritableCellFeatures wcf = null;
        jxl.write.Number numberk = new jxl.write.Number(2, 1, d1);//k
        sheet.addCell(numberk);
        jxl.write.Number numbern = new jxl.write.Number(3, 1, d2);//n
        sheet.addCell(numbern);
        jxl.write.Number numberm = new jxl.write.Number(4, 1, d3);//m
        sheet.addCell(numberm);
        jxl.write.Number numberM = new jxl.write.Number(5, 1, d4);//M
        sheet.addCell(numberM);
        w2.write();
        w2.close();

    }//modify first

    public void modifySecond(File ingetf2) throws WriteException, IOException, BiffException {
//        File inputWorkbook = fileName;
//         System.out.println(ingetf2 + "   SECOND");
//        File outputWorkbook = inputWorkbook;
        if (!ingetf2.exists()) // if inputWorkbook exists, output information about it
        {
            JOptionPane.showMessageDialog(this, ingetf2
                    + " does not exist.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        Workbook w = Workbook.getWorkbook(ingetf2);
        WritableWorkbook w2 = Workbook.createWorkbook(ingetf2, w);//***added 

        WritableSheet sheet = w2.getSheet(0);//R

        //********************************
        Label label_1 = new Label(2, 0, "k units");
        sheet.addCell(label_1);
        Label label_2 = new Label(3, 0, "n periods");
        sheet.addCell(label_2);
        Label label_3 = new Label(4, 0, "m minimum");
        sheet.addCell(label_3);
        Label label_4 = new Label(5, 0, "M maximum");
        sheet.addCell(label_4);
        //**************************************
        Label label = new Label(7, 0, "The Corresponding p i");
        sheet.addCell(label);
        for (int i = 0; i < parseInt_n; i++) {
            jxl.write.Number number2 = new jxl.write.Number(7, i + 1, p_i_star_ArrayList.get(index_ArrayList.get(i)));
            sheet.addCell(number2);
        }
        //***********************************  
        Label label5 = new Label(8, 0, "Max p j-1");
        sheet.addCell(label5);
        sheet.addCell(new Label(8, 1, "Don't Care"));
        for (int i = 1; i < parseInt_n - 1; i++) {
            jxl.write.Number number3 = new jxl.write.Number(8, i + 1, p_j_max_Array.get(i));
            sheet.addCell(number3);
        }
        sheet.addCell(new Label(8, parseInt_n, "Don't Care"));
        //*******************************
        Label label1 = new Label(9, 0, "p*i > max [p1, ..., p j-1]. Yes or No");
        sheet.addCell(label1);
        sheet.addCell(new Label(9, 1, "Always Yes"));
        for (int i = 1; i < parseInt_n - 1; i++) {
            if (Double.parseDouble(sheet.getCell(7, i + 1).getContents())
                    > Double.parseDouble(sheet.getCell(8, i + 1).getContents())) {
                sheet.addCell(new Label(9, i + 1, "Yes"));
            } else {
                sheet.addCell(new Label(9, i + 1, "No"));
            }//else
        }//for 
        sheet.addCell(new Label(9, parseInt_n, "Don't Care"));
        ///**************************
        Label label3 = new Label(10, 0, "Total Revenue");
        sheet.addCell(label3);
        jxl.write.Number number = new jxl.write.Number(10, 1, algorithm_DET(parseInt_n, parseInt_k));
        sheet.addCell(number);

        //********************
        Label label6 = new Label(6, 0, "The Corresponding  i");
        sheet.addCell(label6);

        for (int i = 0; i < parseInt_n; i++) {
            jxl.write.Number number1 = new jxl.write.Number(6, i + 1, index_ArrayList.get(i));
            sheet.addCell(number1);
        }
        ///********************************  
        w2.write();
        w2.close();

    }//modify second
}// end class FileOpen 