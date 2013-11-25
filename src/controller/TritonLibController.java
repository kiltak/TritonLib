package controller;

import controller.utils.FileIO;
import controller.utils.midi.TritonMidiIO;
import view.TritonLib;
import model.Triton;
import model.sound.Bank;
import model.sound.combination.Combination;
import model.sound.program.Program;

public class TritonLibController {
    public TritonLibController () {
        readPcgFile("./docs/PCG/PRELOAD.PCG");
    }
    
    /**
     * Initialize the midi channels being used.
     * @param in
     * @param out
     */
    public void setMidiChannels (int in, int out) {
    	_midiIO.initMidiIn(in);
    	_midiIO.initMidiOut(out);
    }
    
    public void swapPrograms (int bankA, int offsetA, int bankB, int offsetB) {
        // Make sure the combination links get updated
    }
    
    public void swapCombinations (int bankA, int offsetA, int bankB, int offsetB) {
        Combination a = _triton.getSingleCombi(bankA, offsetA);
        Combination b = _triton.getSingleCombi(bankB, offsetB);
        
        _triton.setSingleCombi(a, bankB, offsetB);
        _triton.setSingleCombi(b, bankA, offsetA);
    }
    
    /*********************
     * IO
     *********************/
    
    /**
     * Read a PCG file.  Make sure to set all the program to combination links.
     * @param filename
     */
    public void readPcgFile (String filename) {
        FileIO.readPcgFile(_triton, filename);
        _triton.linkAllCombisToProgs();
    }
    
    /**
     * Write a PCG file.
     * @param filename
     */
    public void writePcgFile (String filename) {
        FileIO.writePcgFile(_triton, filename);
    }
    
    /***************************
     * VIEW UPDATES
     ***************************/

    public void setView (TritonLib newView) {
        _view = newView;
    }
    
    public void displayEverything () {
        displayAllPrograms();
        displayAllCombinations();
    }
    
    public void displayAllPrograms () {
        Bank[] banks = _triton.getAllProgs();
        _view.displayBanks(banks);
    }
    
    public void displayProgramBank (int bank) {
        Bank b = _triton.getProgBank(bank);
        _view.displayBank(b);
    }
    
    public void displaySingleProgram (int bank, int offset) {
        Program p = _triton.getSingleProg(bank, offset);
        _view.displaySound(p);
    }
    
    ////////////
    
    public void displayAllCombinations () {
        Bank[] banks = _triton.getAllCombis();
        _view.displayBanks(banks);
    }
    
    public void displayCombinationBank (int bank) {
        Bank b = _triton.getCombiBank(bank);
        _view.displayBank(b);
    }
    
    public void displaySingleCombination (int bank, int offset) {
        Combination c = _triton.getSingleCombi(bank, offset);
        _view.displaySound (c);
    }
    
    /***************************
     * 
     ***************************/
    
    public void shutdown () {
        _midiIO.close();
    }
    
    private Triton _triton = new Triton();  // Should I instantiate the model here???  Feels wrong.
    private TritonLib _view;
    private TritonMidiIO _midiIO = new TritonMidiIO (_triton);
}
