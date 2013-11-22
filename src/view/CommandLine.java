package view;

import java.util.Scanner;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

import model.sound.Bank;
import model.sound.Location;
import model.sound.combination.Combination;
import model.sound.program.Program;
import controller.CommandLineController;

class CommandLine implements View {
    public void setController (CommandLineController controller) {
        _controller = controller;
    }
    
    private static void listDevices () {
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (int i = 0; i < infos.length; ++i) {
            System.out.println(i + " => " + infos[i]);
        }
    }
    
    /**
     * Create the commands that will be used in the loop.  This is what the user can do with this
     * program
     * 
     * @return String array - each command is one string entry.
     */
    private static String[] createCmds () {
        return new String[] {
                              "q - quit",
                              "l - load the sounds into the keyboard",
                              "g - get the sounds from the keyboard",
                              "o - open pcg file",
                              "s - save the sounds on the computer to disk",
                              "p - print everything out",
                              "d - print single program",
                              "c - print single combination"
                             };
    }
    
    public static void main(String args[]) throws InvalidMidiDataException, MidiUnavailableException, InterruptedException{
        CommandLineController controller = new CommandLineController();
        CommandLine lib = new CommandLine();
        lib.setController(controller);
        controller.setView(lib);

//        /************** Start staged data *************/
//        // Combination Bank
//        FileReader fr = new FileReader("src/staged/dump/combiBank");
//        SysexMessage msg;
//        while ((msg = fr.getNextMsg()) != null) {
//            lib.key.send(msg, -1L);
//        }
//        fr.close();
//        // Program Bank
//        fr = new FileReader("src/staged/dump/progBank");
//        while ((msg = fr.getNextMsg()) != null) {
//            lib.key.send(msg, -1L);
//        }
//        fr.close();
//        // Global
//        fr = new FileReader("src/staged/dump/global");
//        while ((msg = fr.getNextMsg()) != null) {
//            lib.key.send(msg, -1L);
//        }
//        fr.close();
//        /************** End staged data *************/
        
        Scanner input = new Scanner(System.in);
        
//        listDevices();
//        System.out.println ("Enter input device:");
//        String inputVal = input.next();
//        System.out.println ("Enter output device:");
//        String outputVal = input.next();
//        lib._controller.setMidiChannels(inputVal, outputVal);
//
//        lib._controller.setMidiChannels(1, 5);

        lib._controller.readPcgFile("/media/DC18-239C/private/MIDIprograms/TritonLib/docs/PCG/PRELOAD.PCG");
        
        boolean quit = false;
        String cmd;
        String filename;
        int bank, offset;
        String[] cmds = createCmds();
        while (!quit) {
            for (String optionCmds: cmds) {
                System.out.println (optionCmds);
            }
            System.out.print ("Enter command: ");
            cmd = input.next().toLowerCase();

            switch (cmd) {
                case "d": // check the dependencies of a program
                    System.out.print ("Bank: ");
                    bank = Integer.parseInt(input.next());
                    System.out.print ("Offset: ");
                    offset = Integer.parseInt(input.next());
                    lib._controller.displaySingleProgram(bank, offset);
                    break;
                case "c": // check the dependencies of a program
                    System.out.print ("Bank: ");
                    bank = Integer.parseInt(input.next());
                    System.out.print ("Offset: ");
                    offset = Integer.parseInt(input.next());
                    lib._controller.displaySingleCombination(bank, offset);
                    break;
                case "l":  // load the sounds to the keyboard  
                    break;
                case "g":  // get the sounds from the keyboard
//                    lib.key.requestAllPrograms(0);
//                    lib.key.requestAllCombis(0);
//                    lib.key.requestGlobalData(0);
                    break;
                case "o": // open a pcg file
                    System.out.print ("Enter filename: ");
                    filename = input.next();
                    lib._controller.readPcgFile(filename);
                    break;
                case "s":  // save the computer sounds to disk
                    System.out.print ("Enter filename: ");
                    filename = input.next();
                    lib._controller.writePcgFile(filename);
                    break;
                case "q":  // quit
                    quit = true;
                    break;
                case "p":  // print things out
                    System.out.println ("--- Progs ---");
                    lib._controller.displayAllPrograms();
                    System.out.println ("--- Combis ---");
                    lib._controller.displayAllCombinations();
                    break;
                default:
                    System.out.println ("Didn't understand command - " + cmd);
                        
            }
        }

        lib._controller.shutdown();
        input.close();
        
        System.out.println ("Goodbye");
    }
    
    CommandLineController _controller = null;

    @Override
    public void displaySound(Program p) {
        System.out.println ("Single prog: " + p.getName());
        for (Location l : p.getCombis()) {
            System.out.println ("   " + l);
        }
    }

    @Override
    public void displaySound(Combination c) {
        System.out.println ("Single combi: " + c.getName());
        for (Location l : c.getProgramLocations()) {
            System.out.println ("   " + l);
        }
    }

    @Override
    public void displayBank(Bank b) {
        System.out.println ("--- " + b.getName() + " ---");
        for (String s : b.getNames()) {
            System.out.println (s);
        }
    }

    @Override
    public void displayBanks(Bank[] b) {
        String retString = "";
        // Print the headers
        retString += "     ";
        for (int bank = 0; bank < b.length; ++bank) {
            if (b[bank] != null) {
                retString += String.format("        %s        ", b[bank].getName());
            }
        }
        retString += "\n";
        // Print the names
        for (int prog = 0; prog < 128; ++prog) {
            retString += String.format("%03d: ", prog);
            for (int bank = 0; bank < b.length; ++bank) {
                if (b[bank] != null) {
                    retString += String.format("%20s",b[bank].get(prog).getName());
                }
            }
            retString += "\n";
        }
        
        System.out.println (retString);
    }
}