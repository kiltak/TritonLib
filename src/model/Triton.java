/**
 * Model for the librarian.  This contains everything that is being used.
 * 
 * - Banks of programs
 * - Banks of combinations
 * - Global data
 * - Arpeggios (future, because I don't care about them)
 * - Drum Kits (future, because I don't care about them)
 */

package model;

import java.util.ArrayList;

import controller.pcg.Pcg;
import model.sound.Bank;
import model.sound.Location;
import model.sound.combination.Combination;
import model.sound.program.Program;
import model.global.Global;

public class Triton {
	public Triton() {
		_progs = new Bank[Bank.NUMBER_OF_BANKS];
        _combis = new Bank[Bank.NUMBER_OF_BANKS];
	}
	
	/**
	 * Link all of the combinations to the programs.
	 */
	public void linkCombisToProgs () {
	    clearCombiProgLinks();
	    for (int bank = 0; bank < _combis.length; ++bank) {
	        if (_combis[bank] != null) {
    	        Bank b = _combis[bank];
    	        for (int offset = 0; offset < 128; ++offset) {
                    Location combiLoc = new Location (bank, offset);
                    Combination c = (Combination)b.get(offset);
    	            
                    ArrayList<Location> progsInCombi = c.getProgramLocations();
    	            for (Location progLoc : progsInCombi) {
    	                if (_progs[progLoc.getBank()] != null) {
                            ((Program)(_progs[progLoc.getBank()]._sounds[progLoc.getOffset()])).addCombi(combiLoc);
    	                }
    	                else {
    	                    System.out.println ("couldn't set link for " + c.getName() + ", " + progLoc);
    	                }
    	            }
    	        }
	        }
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
	    return (Program)_progs[bank].get(offset);
	}
	
	public void setSingleProg (Program p, int bank, int offset) {
		if (_progs[bank] == null) {
			_progs[bank] = new Bank(Bank.PROG, bank);
		}
	    _progs[bank].set(p, offset);
	}
    
    /*********************
     *  COMBINATIONS
     *********************/
    
    public Bank[] getAllCombis () {
        return _combis;
    }
    
    public void setAllCombis (Bank[] combis) {
        _combis = combis;
    }
    
    public Bank getCombiBank (int offset) {
        return _combis[offset];
    }
    
    public void setCombiBank (Bank bank, int offset) {
        _combis[offset] = bank;
    }
    
    public Combination getSingleCombi (int bank, int offset) {
        return (Combination)_combis[bank].get(offset);
    }
    
    public void setSingleCombi (Combination p, int bank, int offset) {
		if (_combis[bank] == null) {
			_combis[bank] = new Bank(Bank.COMBI, bank);
		}
        _combis[bank].set(p, offset);
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
	

	// Programs: 5 int banks, 7 ext banks, 128 programs per bank
	private Bank[] _progs;
	
	// Combinations: 5 int banks, 7 ext banks, 128 combis per bank
	private Bank[] _combis;
	
	private Global _global = new Global();
}