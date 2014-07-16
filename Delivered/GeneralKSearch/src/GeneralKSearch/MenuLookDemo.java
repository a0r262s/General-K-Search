package GeneralKSearch;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

public class MenuLookDemo extends JFrame implements ActionListener, ItemListener {

    final static int GAP = 10;
    private JLabel lab1 = new JLabel("Enter number of Units (k): ");
    private JLabel lab2 = new JLabel("Enter number of total Priods (n): ");
    private JLabel lab3 = new JLabel("Enter lower bound of inputs (m): ");
    private JLabel lab4 = new JLabel("Enter upper bound of inputs (M): ");
//      private JLabel lab5 = new JLabel("Open Your File ): ");
    public JTextField input1 = new JTextField(10);
    public JTextField input2 = new JTextField(10);
    public JTextField input3 = new JTextField(10);
    public JTextField input4 = new JTextField(10);
//      public JTextField input5 = new JTextField(10);
    private JButton okay = new JButton("OK ");
     private JButton okay1 = new JButton(" Set File and Parameters ");
    private static final int WIDTH = 370;
    private static final int HEIGHT = 170;
    int dval1, dval2 = 5, dval3 = 1, dval4 = 10;
//    boolean b1 = false, b2 = false, b3 = false, b4 = false;
///////////////////////////////////////////////
    boolean bmf = false;
     File file ;
    JTextArea output;
    JScrollPane scrollPane;
    String newline = "\n";
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    private JMenuItem menuItem2_0;
    private JMenuItem menuItem3;
    private JMenuItem menuItem4;
    private JMenuItem menuItem5;

    public MenuLookDemo() {
        super("Set Inputs");
        setSize(WIDTH, HEIGHT);

        // Get the content pane and set it up.
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        // Instantiate the message label


        // Connect the listener (this applet) with the JTextField
        // and the JButton's.
        input1.addActionListener(this); // textfield
        input2.addActionListener(this);
        input3.addActionListener(this);
        input4.addActionListener(this);
//          input5.addActionListener(this);
        okay.addActionListener(this);
        okay1.addActionListener(this);
        okay1.setActionCommand("setter");


        //Add components, in panels first, then in the frame.
        JPanel p1 = new JPanel(new GridLayout(0, 1));
        p1.add(lab1);
        p1.add(lab2);
        p1.add(lab3);
        p1.add(lab4);

        JPanel p3 = new JPanel(new GridLayout(0, 1));
        p3.add(input1);
        p3.add(input2);
        p3.add(input3);
        p3.add(input4);

        JPanel p5 = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        p5.setLayout(new FlowLayout());
       p5.add(okay1);
        p5.add(okay);
       
        p5.setBorder(BorderFactory.createEmptyBorder(0, 0,
                GAP - 5, GAP - 5));
        add(p1, BorderLayout.CENTER);
        add(p3, BorderLayout.LINE_END);
        add(p5, BorderLayout.PAGE_END);
    }//constructor 

    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu;
        //JMenuItem menuItem;
        //Create the menu bar.
        menuBar = new JMenuBar();
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);

//        menuItem1 = new JMenuItem("Open",
//                KeyEvent.VK_T);
//        //menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
//        menuItem1.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_1, ActionEvent.ALT_MASK));
//        menuItem1.getAccessibleContext().setAccessibleDescription(
//                "This doesn't really do anything");
//        menuItem1.addActionListener(this);
//        menu.add(menuItem1);
        menu.addSeparator();

        menuItem2_0 = new JMenuItem("Set Inputs ",
                KeyEvent.VK_T);
        //menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
        menuItem2_0.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_2, ActionEvent.ALT_MASK));
        menuItem2_0.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        menuItem2_0.addActionListener(this);
        menu.add(menuItem2_0);
        menu.addSeparator();

        menuItem2 = new JMenuItem("Show Result ",
                KeyEvent.VK_T);
        //menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
        menuItem2.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_2, ActionEvent.ALT_MASK));
        menuItem2.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        menuItem2.addActionListener(this);
        menu.add(menuItem2);
        menu.addSeparator();

        menuItem3 = new JMenuItem("Exit",
                KeyEvent.VK_T);
        //menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
        menuItem3.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_3, ActionEvent.ALT_MASK));
        menuItem3.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        menuItem3.addActionListener(this);
        menu.add(menuItem3);


        //Build second menu in the menu bar.
        menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_N);
        menu.getAccessibleContext().setAccessibleDescription(
                "This menu does nothing");
        menuBar.add(menu);

        menuItem4 = new JMenuItem("Contents",
                KeyEvent.VK_T);
        //menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
        menuItem4.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_4, ActionEvent.ALT_MASK));
        menuItem4.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        menuItem4.addActionListener(this);
        menu.add(menuItem4);

        menuItem5 = new JMenuItem("About",
                KeyEvent.VK_T);
        //menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
        menuItem5.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_5, ActionEvent.ALT_MASK));
        menuItem5.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        menuItem5.addActionListener(this);
        menu.add(menuItem5);
        return menuBar;

    }

    public Container createContentPane() {
        //Create the content-pane-to-be.
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setOpaque(true);

        //Create a scrolled text area.
        output = new JTextArea(10, 50);
        output.setEditable(false);
        scrollPane = new JScrollPane(output);

        //Add the text area to the content pane.
        contentPane.add(scrollPane, BorderLayout.CENTER);
        output.append("Click on Set Inputs under File menu." + newline);
        output.append("Give the parameters through the text fields." + newline + 
                "Click on Set Files and Parameters." +newline+"Select .xls input file." + newline + "Click on OK button."+newline);
         output.append("Click on Show Result to see the table of results." + newline+
                 "The last column of the table shows the Total Revenue."+newline);
        return contentPane;
    }

    public int computations (){
        
        int dvalc= -1;
        String sval1 = input1.getText();
if (    !"".equals(input1.getText())) {
               
               dvalc = Integer.parseInt(sval1);
              
//                b1=true;
            }
return dvalc;
    }
    @Override
    public  void actionPerformed(ActionEvent e) {
//        File file = null;
        int dval1in = -1, dval2in = -1, dval3in = -1, dval4in = -1;

        FileOpen application = null;
        try {
            application = new FileOpen();
        } catch (IOException ex) {
            Logger.getLogger(MenuLookDemo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(MenuLookDemo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WriteException ex) {
            Logger.getLogger(MenuLookDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        MenuLookDemo frame = new MenuLookDemo();
        if (e.getSource() == menuItem1) { //open
//            file = application.getFile();
//             e.setSource(menuItem2_0) ;
//                
                   }
        
        Object source = e.getSource();
        if (source == menuItem2_0 ){
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
           
             e.setSource(okay1);
        }
        if (source == okay1 ){ //                || source == input2 || source == input3 || source == input4) {
  file = application.getFile();
           if (!"".equals(input1.getText()))
                dval1 = Integer.parseInt(input1.getText());
    //            dval2 = dval1in;
               if (!"".equals(input2.getText())) {
                String sval2 = input2.getText();
                dval2in = Integer.parseInt(sval2);
                dval2 = dval2in;
               }
               if (!"".equals(input1.getText())){
//                System.out.print("\n INPUT 2  " + dval2in);
                String sval3 = input3.getText();
                dval3in = Integer.parseInt(sval3);
                dval3 = dval3in;
               }
               if (!"".equals(input1.getText())){
//                System.out.print("\n INPUT 3  " + dval3in);
                String sval4 = input4.getText();
                dval4in = Integer.parseInt(sval4);
//                System.out.print("\n INPUT 4 " + dval4in);
                dval4 = dval4in;
               }
           
//                       System.out.print("\n dval1  *****  " + dval1);
                     
            try {
                application.modifyFirst(file, dval1, dval2, dval3, dval4);
            } catch (WriteException ex) {
                Logger.getLogger(MenuLookDemo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MenuLookDemo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BiffException ex) {
                Logger.getLogger(MenuLookDemo.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                application.read(file);
            } catch (IOException ex) {
                Logger.getLogger(MenuLookDemo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BiffException ex) {
                Logger.getLogger(MenuLookDemo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (WriteException ex) {
                Logger.getLogger(MenuLookDemo.class.getName()).log(Level.SEVERE, null, ex);
            }
//            e.setSource(okay);
        }
                if (e.getSource()==okay)
                 this.dispose();
            
               
   
    //////SHOW RESULT 
                 if (e.getSource() == menuItem2 ) {//showresult 
                     
                javax.swing.SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        M_Invokes.createAndShowGUI();
                    }
                });
//                output.append("The result as a table are represented." + newline);
            }//show result
                
               


            if (e.getSource() == menuItem2_0) {//set inputs 
                frame.setVisible(true);
            }//set inputs

            

            /*constant parts*/
            if (e.getSource() == menuItem4) {//contents 
                OpenTextFileIntoJTextArea.readAboutFile("/input/help.txt", "Help");
            }
            if (e.getSource() == menuItem5) {//about 
                OpenTextFileIntoJTextArea.readAboutFile("/input/about.txt", "About");
            }
            if (e.getSource() == menuItem3) {//exit

                System.exit(0);
            }

            
          

    }//action listener

    @Override
    public void itemStateChanged(ItemEvent e) {
        JMenuItem source = (JMenuItem) (e.getSource());
        String s = "Item event detected."
                + newline
                + "    Event source: " + source.getText()
                + " (an instance of " + getClassName(source) + ")"
                + newline
                + "    New state: "
                + ((e.getStateChange() == ItemEvent.SELECTED)
                ? "selected" : "unselected");
        output.append(s + newline);
        output.setCaretPosition(output.getDocument().getLength());
    }

    // Returns just the class name -- no package info.
    protected String getClassName(Object o) {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex + 1);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI1() {
        //Create and set up the window.
        JFrame frame = new JFrame("Start");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        MenuLookDemo demo = new MenuLookDemo();
        frame.setJMenuBar(demo.createMenuBar());
        frame.setContentPane(demo.createContentPane());

        //Display the window.
        frame.setSize(450, 260);
        frame.setVisible(true);

    }

    public static void main(String[] args) throws IOException, BiffException, WriteException {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                createAndShowGUI1();
            }
        });
//        FileOpen application = new FileOpen();
    }
}