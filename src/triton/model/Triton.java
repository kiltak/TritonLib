/**
 * Model for the librarian.  This contains everything that is being used.
 * 
 * - Banks of programs
 * - Banks of combinations
 * - Global data
 * - Arpeggios (future, because I don't care about them)
 * - Drum Kits (future, because I don't care about them)
 */

package triton.model;

import java.util.ArrayList;

import controller.pcg.Pcg;
import triton.model.sound.Bank;
import triton.model.sound.Location;
import triton.model.sound.combination.Combination;
import triton.model.sound.program.Program;
import triton.model.global.Global;

public class Triton {
    public Triton() {
        _progs = new Bank[Bank.NUMBER_OF_PROG_BANKS];
        _combis = new Bank[Bank.NUMBER_OF_COMBI_BANKS];
        
        // Go ahead and put in some empty banks.  I'm torn if this is a good idea or not.
        for (int i = 0; i < Bank.NUMBER_OF_PROG_BANKS; ++i) {
            _progs[i] = new Bank(Bank.PROG, i);
        }
        for (int i = 0; i < Bank.NUMBER_OF_COMBI_BANKS; ++i) {
            _combis[i] = new Bank(Bank.COMBI, i);
        }
    }
    
    /**
     * Link a single combination to the programs that it references.
     */
    private void linkCombiToProgs (int combiBank, int combiOffset) {
        Combination c = getSingleCombi(combiBank, combiOffset);
        if (c != null) {
//            System.out.println ("Linking combi " + c.getName());
            Location combiLoc = new Location (combiBank, combiOffset);
            
            ArrayList<Location> progsInCombi = c.getProgramLocations();
            for (Location progLoc : progsInCombi) {
                int bank = progLoc.getBank();
                int offset = progLoc.getOffset();
                if ((0 <= bank) && (bank <= Bank.NUMBER_OF_PROG_BANKS) && (0 <= offset) && (offset <= 127)) {
                    if (_progs[bank] != null) {
                        Program p = (Program)(_progs[bank]._sounds[offset]);
                        p.addCombi(combiLoc);
//                        System.out.println ("   " + p.getName());
                    }
//                    else {
//                        System.out.println ("   couldn't set link for " + c.getName() + ", " + progLoc);
//                    }
                }
//                else {
//                    System.out.println ("   link out of range.  bank " + bank + ", offset " + offset);
//                }
            }
        }
    }
    
    /**
     * Link a bank of combis to the programs they reference.
     * 
     * @param bank
     */
    private void linkCombiBankToProgs (int bank) {
        if (_combis[bank] != null) {
            for (int offset = 0; offset < 128; ++offset) {
                linkCombiToProgs (bank, offset);
            }
        }
    }
    
    /**
     * Link all of the combinations to the programs.
     */
    public void linkAllCombisToProgs () {
        clearCombiProgLinks();
        for (int bank = 0; bank < _combis.length; ++bank) {
            linkCombiBankToProgs (bank);
        }
    }
    
    /**
     * Clear out all of the program links to combinations.
     */
    private void clearCombiProgLinks () {
        for (Bank b : _progs) {
            if (b != null) {
                for (Program p : (Program[])b._sounds) {
                    p.clearCombis();
                }
            }
        }
    }
    
    /**
     * Transfer the sounds from the Pcg to the triton.
     * @param pcg
     */
    public void setFromPcg (Pcg pcg) {
        setAllProgs (pcg.getProgs());
        setAllCombis (pcg.getCombis());
        _global = pcg.getGlobal();
    }
    
    /**************
     *  PROGRAMS
     **************/
    
    public Bank[] getAllProgs () {
        return _progs;
    }
    
    public void setAllProgs (Bank[] progs) {
        _progs = progs;
    }
    
    public Bank getProgBank (int offset) {
        return _progs[offset];
    }
    
    public void setProgBank (Bank bank, int offset) {
        _progs[offset] = bank;
    }
    
    public Program getSingleProg (int bank, int offset) {
        if (_progs[bank] != null) {
            return (Program)_progs[bank].get(offset);
        }
        
        return null;
    }
    
    public void setSingleProg (Program p, int bank, int offset) {
        if (_progs[bank] == null) {
            _progs[bank] = new Bank(Bank.PROG, bank);
        }
        _progs[bank].set(p, offset);
    }
    
    public void renameProgram (int bank, int offset, String newName) {
    	if (_progs[bank] != null) {
    		_progs[bank].get(offset).setName(newName);
    	}
    }
    
    /*********************
     *  COMBINATIONS
     *********************/
    
    public Bank[] getAllCombis () {
        return _combis;
    }
    
    public void setAllCombis (Bank[] combis) {
        _combis = combis;
        linkAllCombisToProgs();
    }
    
    public Bank getCombiBank (int offset) {
        return _combis[offset];
    }
    
    public void setCombiBank (Bank bank, int offset) {
        _combis[offset] = bank;
        linkCombiBankToProgs (offset);
    }
    
    public Combination getSingleCombi (int bank, int offset) {
        if (_combis[bank] != null) {
            return (Combination)_combis[bank].get(offset);
        }
        
        return null;
    }
    
    public void setSingleCombi (Combination p, int bank, int offset) {
        if (_combis[bank] == null) {
            _combis[bank] = new Bank(Bank.COMBI, bank);
        }
        _combis[bank].set(p, offset);
        linkCombiToProgs (bank, offset);
    }
    
    public void renameCombination (int bank, int offset, String newName) {
    	if (_combis[bank] != null) {
    		_combis[bank].get(offset).setName(newName);
    	}
    }
    
    /**************
     *  GLOBAL
     **************/
    
    public Global getGlobal() {
        return _global;
    }
    
    public void setGlobal (Global global) {
        _global = global;
    }
    
    /**************
     *  
     **************/
    
    private Bank[] _progs;
    private Bank[] _combis;
    private Global _global = new Global();
}