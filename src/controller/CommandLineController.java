package controller;

//import javax.sound.midi.SysexMessage;
//import staged.FileReader;
import view.View;
import triton.model.sound.Bank;
import triton.model.sound.combination.Combination;
import triton.model.sound.program.Program;

public class CommandLineController extends BasicController {
    public CommandLineController () {
//        FileReader fr;
//        SysexMessage msg;
//        
//        fr = new FileReader ("src/staged/dump/progDump");
//        while ((msg = fr.getNextMsg()) != null) {
//            _midiIO.send(msg, -1);
//        }
//        fr.close();
//        fr = new FileReader ("src/staged/dump/everything");
//        while ((msg = fr.getNextMsg()) != null) {
//            _midiIO.send(msg, -1);
//        }
//        fr.close();
    }
    
    /***************************
     * VIEW UPDATES
     ***************************/

    public void setView (View newView) {
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
}
