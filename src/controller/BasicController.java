package controller;

import java.util.Set;

import javax.sound.midi.SysexMessage;

import triton.model.Triton;
import triton.model.sound.Bank;
import triton.model.sound.Location;
import triton.model.sound.combination.Combination;
import triton.model.sound.program.Program;
import util.TritonRequest;
import view.View;
import controller.utils.FileIO;
import controller.utils.midi.TritonMidiIO;

public class BasicController {
    
    /**
     * Initialize the midi channels being used.
     * @param in
     * @param out
     */
    public void setMidiChannels (int in, int out) {
        _midiIO.initMidiIn(in);
        _midiIO.initMidiOut(out);
    }
    
    /**
     * Swap two programs.  This is a little tricky, because I don't want to mess up the combination
     * that are linked to these programs.
     * 
     * @param bankA
     * @param offsetA
     * @param bankB
     * @param offsetB
     */
    public void swapPrograms (int bankA, int offsetA, int bankB, int offsetB) {
        Program a = _triton.getSingleProg(bankA, offsetA);
        Program b = _triton.getSingleProg(bankB, offsetB);

        _triton.setSingleProg(b, bankA, offsetA);
        _triton.setSingleProg(a, bankB, offsetB);
        
        // Make sure the combination links get updated
        Location oldALoc = new Location (bankA, offsetA);
        Location oldBLoc = new Location (bankB, offsetB);
        
        // Fix program A's links
        Set<Location> locs = a.getCombis();
        for (Location l : locs) {
            Combination c = _triton.getSingleCombi(l.getBank(), l.getOffset());
            c.updateProgramLocations(oldALoc, oldBLoc);
        }
        
        // Fix program B's links
        locs = b.getCombis();
        for (Location l : locs) {
            Combination c = _triton.getSingleCombi(l.getBank(), l.getOffset());
            c.updateProgramLocations(oldBLoc, oldALoc);
        }
    }
    
    /**
     * Swap two combinations.  Pretty straight forward.
     * 
     * @param bankA
     * @param offsetA
     * @param bankB
     * @param offsetB
     */
    public void swapCombinations (int bankA, int offsetA, int bankB, int offsetB) {
        Combination a = _triton.getSingleCombi(bankA, offsetA);
        Combination b = _triton.getSingleCombi(bankB, offsetB);
        
        _triton.setSingleCombi(a, bankB, offsetB);
        _triton.setSingleCombi(b, bankA, offsetA);
    }
    
    /**
     * Rename the sound.
     * 
     * @param type     What type of sound to rename (Program or Combination).
     * @param bank
     * @param offset
     * @param newName
     */
    public void rename (int type, int bank, int offset, String newName) {
        switch (type) {
            case Bank.PROG:
                Program p = _triton.getSingleProg(bank, offset);
                p.setName(newName);
                break;
            case Bank.COMBI:
                Combination c = _triton.getSingleCombi(bank, offset);
                c.setName(newName);
                break;
        }
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
//        _triton.linkAllCombisToProgs();
    }
    
    /**
     * Write a PCG file.
     * @param filename
     */
    public void writePcgFile (String filename) {
        FileIO.writePcgFile(_triton, filename);
    }
    
    /***************************
     * REQUESTS
     ***************************/
    
    public void requestEverything () {
        requestAllPrograms();
        for (int i = Bank.PROG_EXTA; i < Bank.NUMBER_OF_PROG_BANKS; ++i) { // Hardware needs special invitation for EXT banks
            requestProgramBank(i);
        }
        requestAllCombis();
        requestGlobal();
    }
    
    public void requestProgramBank (int bank) {
        SysexMessage msg = TritonRequest.requestBankProgams(midiChannel, bank);
        _midiIO.sendMsg(msg);
    }
    
    public void requestSingleProgram (int bank, int offset) {
        SysexMessage msg = TritonRequest.requestSingleProgram(midiChannel, bank, offset);
        _midiIO.sendMsg(msg);
    }
    
    public void requestAllPrograms () {
        _midiIO.sendMsg (TritonRequest.requestAllPrograms(midiChannel));
    }
    
    public void requestAllCombis () {
        _midiIO.sendMsg(TritonRequest.requestAllCombis(midiChannel));
    }
    
    public void requestGlobal () {
        _midiIO.sendMsg(TritonRequest.requestGlobalData(midiChannel));
    }
    
    public void shutdown () {
        _midiIO.close();
    }
    
    protected Triton _triton = new Triton();  // Should I instantiate the model here???  Feels wrong.
    protected View _view;
    protected TritonMidiIO _midiIO = new TritonMidiIO (_triton);
    
    private int midiChannel = 0;
}
