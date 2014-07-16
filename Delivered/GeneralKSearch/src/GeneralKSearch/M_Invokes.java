package GeneralKSearch;
//import javax.swing.JFrame;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class M_Invokes extends JPanel {
//public static int rows = FileOpen.parseInt_n;
//public static ArrayList<Integer> Corresponding_Array = FileOpen.index_ArrayList;

    private boolean DEBUG = false;

    public M_Invokes() {

        super(new GridLayout(1, 0));

        String[] columnNames = {"The Corresponding i",
            "The Corresponding p i",
            "Max p j-1",
            "p* i >< Max p j-1  ",
            "Total Revenue"};

        // Declare 2D array
        Object[][] data = new Object[FileOpen.parseInt_n][5];
// Initialize 2D array values

        for (int j = 0; j < FileOpen.parseInt_n; j++) {
            data[j][0] = new Integer(FileOpen.index_ArrayList.get(j));
        }

        for (int j = 0; j < FileOpen.parseInt_n; j++) {
            data[j][1] = new Double(FileOpen.p_i_star_ArrayList.get(FileOpen.index_ArrayList.get(j)));
        }
        for (int j = 0; j < FileOpen.parseInt_n; j++) {
            data[j][2] = new Double(FileOpen.p_j_max_Array.get(j));
        }
        data[0][3] = "Always Yes";
        for (int j = 1; j < FileOpen.parseInt_n - 1; j++) {
            if (FileOpen.p_i_star_ArrayList.get(FileOpen.index_ArrayList.get(j)) > FileOpen.p_j_max_Array.get(j)) {
                data[j][3] = "Yes";
            } else {
                data[j][3] = "No";
            }
        }
        data[FileOpen.parseInt_n - 1][3] = "Don't Care";
        data[0][4] = new Double(FileOpen.total_revenue);

//        Object[][] data = {
//	    {"Kathy", "Smith",
//	     "Snowboarding", new Integer(5), new Boolean(false)},
//	    {"John", "Doe",
//	     "Rowing", new Integer(3), new Boolean(true)},
//	    {"Sue", "Black",
//	     "Knitting", new Integer(2), new Boolean(false)},
//	    {"Jane", "White",
//	     "Speed reading", new Integer(20), new Boolean(true)},
//	    {"Joe", "Brown",
//	     "Pool", new Integer(10), new Boolean(false)}
//        };

        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(720, 120));
        table.setFillsViewportHeight(true);

        if (DEBUG) {
            table.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    printDebugData(table);
                }
            });
        }

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }

    private void printDebugData(JTable table) {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();

        System.out.println("Value of data: ");
        for (int i = 0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j = 0; j < numCols; j++) {
                System.out.print("  " + model.getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Output as An Table");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);////********

        //Create and set up the content pane.
        M_Invokes newContentPane = new M_Invokes();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);// frame location table output
    }
    }
//	public static void main(String[] args) throws IOException, BiffException, WriteException {
////	Scanner input = new Scanner( System.in );	
////            M_Invokes test = new M_Invokes();
////            System.out.print( "Enter Excel file name here: " );
////           test.read( input.nextLine() );
////            FileOpen application = new FileOpen();
////                application.read();
//             
//            //Schedule a job for the event-dispatching thread:
//        //creating and showing this application's GUI.
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                createAndShowGUI();
//            }
//        });
////            application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );//2
//            	//test.setInputFile(path);
//		//test.read();
//	
//}

