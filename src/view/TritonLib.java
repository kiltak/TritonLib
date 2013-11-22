package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

import model.sound.Bank;
import model.sound.Location;
import model.sound.combination.Combination;
import model.sound.program.Program;
import controller.TritonLibController;

public class TritonLib extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L; // Shut up Eclipse...
    private static TritonLibController _controller;

    public TritonLib(JFrame frame) {
        this.setPreferredSize(new Dimension(800, 400)); // width, height

        // Print button to be removed later
        JButton printBtn = new JButton("Print");
        printBtn.setActionCommand("print");
        printBtn.addActionListener(this);
        add(printBtn);
        
        NameChartPanel chart = new NameChartPanel();
        add(chart);

        // Set up the menu
        setMenu(frame);
    }

    private static JFrame createGUI() {
        // Create and set up the window.
        JFrame frame = new JFrame("TritonLib");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TritonLib newContentPane = new TritonLib(frame);
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        return frame;
    }

    private void displayGUI(JFrame frame) {
        frame.pack();
        frame.setVisible(true);
    }

    private void setMenu(JFrame frame) {
        JMenuBar menubar;
        JMenu menu;
        JMenuItem menuItem;

        // Create the menu bar.
        menubar = new JMenuBar();

        // Build the first menu.
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        menubar.add(menu);

        menuItem = new JMenuItem("Open PCG File");
        menuItem.setActionCommand("openPcg");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Write PCG File");
        menuItem.setActionCommand("writePcg");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        frame.setJMenuBar(menubar);
    }

    public void actionPerformed(ActionEvent e) {
        if ("openPcg".equals(e.getActionCommand())) {
            System.out.println("need to read a file");
        }
        if ("writePcg".equals(e.getActionCommand())) {
            System.out.println("need to write a file");
        }
        if ("print".equals(e.getActionCommand())) {
            System.out.println("need to print");
            _controller.displayEverything();
        }
    }

    public void displaySound(Program p) {
        System.out.println("Single prog: " + p.getName());
        for (Location l : p.getCombis()) {
            System.out.println("   " + l);
        }
    }

    public void displaySound(Combination c) {
        System.out.println("Single combi: " + c.getName());
        for (Location l : c.getProgramLocations()) {
            System.out.println("   " + l);
        }
    }

    public void displayBank(Bank b) {
        System.out.println("--- " + b.getName() + " ---");
        for (String s : b.getNames()) {
            System.out.println(s);
        }
    }

    public void displayBanks(Bank[] b) {
        String retString = "";
        // Print the headers
        retString += "     ";
        for (int bank = 0; bank < b.length; ++bank) {
            if (b[bank] != null) {
                retString += String.format("        %s        ",
                        b[bank].getName());
            }
        }
        retString += "\n";
        // Print the names
        for (int prog = 0; prog < 128; ++prog) {
            retString += String.format("%03d: ", prog);
            for (int bank = 0; bank < b.length; ++bank) {
                if (b[bank] != null) {
                    retString += String.format("%20s", b[bank].get(prog)
                            .getName());
                }
            }
            retString += "\n";
        }

        System.out.println(retString);
    }

    private static void setController(TritonLibController controller) {
        _controller = controller;
    }

    public static void main(String[] args) {
        final JFrame frame = createGUI();
        final TritonLib gui = new TritonLib(frame);

        TritonLibController controller = new TritonLibController();
        controller.setView(gui);
        setController(controller);

        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                gui.displayGUI(frame);
            }
        });
    }
}
