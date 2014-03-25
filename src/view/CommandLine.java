package view;

import java.util.Scanner;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

import triton.model.sound.Bank;
import triton.model.sound.Location;
import triton.model.sound.combination.Combination;
import triton.model.sound.program.Program;
import controller.CommandLineController;

class CommandLine implements View {
    
    CommandLineController _controller = null;
    
    public void setController(CommandLineController controller) {
        _controller = controller;
    }
    
    @SuppressWarnings("unused")
    private static void listDevices() {
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (int i = 0; i < infos.length; ++i) {
            System.out.println(i + " => " + infos[i]);
        }
    }
    
    /**
     * Create the commands that will be used in the loop. This is what the user
     * can do with this program
     * 
     * @return String array - each command is one string entry.
     */
    private static String[] createCmds() {
        return new String[] { "q - quit", "l - load the sounds into the keyboard",
                "g - get the sounds from the keyboard", "o - open pcg file",
                "s - save the sounds on the computer to disk", "p - print everything out",
                "d - print single program", "c - print single combination",
                "swapp - swap two programs", "swapc - swap two combinations" };
    }
    
    public static void main(String args[]) throws InvalidMidiDataException,
            MidiUnavailableException, InterruptedException {
        CommandLineController controller = new CommandLineController();
        CommandLine lib = new CommandLine();
        lib.setController(controller);
        controller.setView(lib);
        
        Scanner input = new Scanner(System.in);
        
        // listDevices();
        // System.out.println ("Enter input device:");
        // String inputVal = input.next();
        // System.out.println ("Enter output device:");
        // String outputVal = input.next();
        // lib._controller.setMidiChannels(inputVal, outputVal);
        //
        // lib._controller.setMidiChannels(1, 5);
        
        // lib._controller.readPcgFile("docs/PCG/LEADS.PCG");
        // lib._controller.readPcgFile("docs/PCG/Legacy2.pcg");
        lib._controller.readPcgFile("/media/DC18-239C/private/MIDIprograms/TritonLib/docs/PCG/Legacy.pcg");
        
        boolean quit = false;
        String cmd;
        String filename;
        int bank, offset;
        String[] cmds = createCmds();
        while (!quit) {
            for (String optionCmds : cmds) {
                System.out.println(optionCmds);
            }
            System.out.print("Enter command: ");
            cmd = input.next().toLowerCase();
            
            if ("d".equalsIgnoreCase(cmd)) { // check the dependencies of a program
                System.out.print("Bank: ");
                bank = Integer.parseInt(input.next());
                System.out.print("Offset: ");
                offset = Integer.parseInt(input.next());
                lib._controller.displaySingleProgram(bank, offset);
            }
            if ("c".equalsIgnoreCase(cmd)) { // check the dependencies of a program
                System.out.print("Bank: ");
                bank = Integer.parseInt(input.next());
                System.out.print("Offset: ");
                offset = Integer.parseInt(input.next());
                lib._controller.displaySingleCombination(bank, offset);
            }
            if ("l".equalsIgnoreCase(cmd)) { // load the sounds to the keyboard
            }
            if ("g".equalsIgnoreCase(cmd)) { // get the sounds from the keyboard
                lib._controller.requestEverything();
            }
            if ("o".equalsIgnoreCase(cmd)) { // open a pcg file
                System.out.print("Enter filename: ");
                filename = input.next();
                lib._controller.readPcgFile(filename);
            }
            if ("s".equalsIgnoreCase(cmd)) { // save the computer sounds to disk
                System.out.print("Enter filename: ");
                filename = input.next();
                lib._controller.writePcgFile(filename);
            }
            if ("q".equalsIgnoreCase(cmd)) { // quit
                quit = true;
            }
            if ("p".equalsIgnoreCase(cmd)) { // print things out
                System.out.println("--- Progs ---");
                lib._controller.displayAllPrograms();
                System.out.println("--- Combis ---");
                lib._controller.displayAllCombinations();
            }
            if ("swapp".equalsIgnoreCase(cmd)) {
                System.out.print("Enter Bank A:");
                int bankA = Integer.parseInt(input.next());
                System.out.print("Enter Offset A:");
                int offsetA = Integer.parseInt(input.next());
                System.out.print("Enter Bank B:");
                int bankB = Integer.parseInt(input.next());
                System.out.print("Enter Offset B:");
                int offsetB = Integer.parseInt(input.next());
                lib._controller.swapPrograms(bankA, offsetA, bankB, offsetB);
            }
            if ("swapc".equalsIgnoreCase(cmd)) {
                System.out.print("Enter Bank A:");
                int bankA = Integer.parseInt(input.next());
                System.out.print("Enter Offset A:");
                int offsetA = Integer.parseInt(input.next());
                System.out.print("Enter Bank B:");
                int bankB = Integer.parseInt(input.next());
                System.out.print("Enter Offset B:");
                int offsetB = Integer.parseInt(input.next());
                lib._controller.swapCombinations(bankA, offsetA, bankB, offsetB);
            }
            else {
                System.out.println("Didn't understand command - " + cmd);
            }
        }
        
        lib._controller.shutdown();
        input.close();
        
        System.out.println("Goodbye");
    }
    
    @Override
    public void displaySound(Program p) {
        System.out.println("Single prog: " + p.getName());
        for (Location l : p.getCombis()) {
            System.out.println("   " + l);
        }
    }
    
    @Override
    public void displaySound(Combination c) {
        System.out.println("Single combi: " + c.getName());
        for (Location l : c.getProgramLocations()) {
            System.out.println("   " + l);
        }
    }
    
    @Override
    public void displayBank(Bank b) {
        System.out.println("--- " + b.getName() + " ---");
        for (String s : b.getNames()) {
            System.out.println(s);
        }
    }
    
    @Override
    public void displayBanks(Bank[] b) {
        String retString = "";
        // Get the headers
        String[] hdrs = null;
        for (Bank bank : b) {
            if (bank != null) {
                hdrs = bank.getBankNames();
                break;
            }
        }
        if (hdrs == null) { // Nothing in any of the banks
            return;
        }
        // Print the headers
        retString += "     ";
        for (String s : hdrs) {
            retString += String.format("        %s        ", s);
        }
        retString += "\n";
        // Print the names
        for (int prog = 0; prog < 128; ++prog) {
            retString += String.format("%03d: ", prog);
            for (int bank = 0; bank < b.length; ++bank) {
                if (b[bank] != null) {
                    retString += String.format("%20s", b[bank].get(prog).getName());
                }
                else {
                    retString += "                    ";
                }
            }
            retString += "\n";
        }
        
        System.out.println(retString);
    }
}