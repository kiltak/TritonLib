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

//	public void send(MidiMessage message, long lTimeStamp) {
//		String strMessage = null;
//		if (message instanceof SysexMessage) {
//			strMessage = _midiParser.decodeMessage((SysexMessage) message, this);
//		}
////		else {
////			strMessage = "unknown message type " + message.getClass();
////		}
//		String strTimeStamp = null;
//
//		if (lTimeStamp == -1L) {
//			strTimeStamp = "timestamp [unknown]: ";
//		} else {
//			strTimeStamp = "timestamp " + lTimeStamp + " us: ";
//		}
//
//		if (strMessage != null) {
//			System.out.println(strTimeStamp + strMessage);
//		}
//	}

//	public void requestMode(int channel) {
//		sendMsg ((TritonRequest.buildRequest(TritonRequest.MODE_REQUEST, channel)));
//	}
	
//	/*****************************
//	 *          PROGRAM DATA
//	 *****************************/
//	
//	public void requestCurrentProgramParameterDump (int channel) {
//		sendMsg (TritonRequest.requestCurrentProgramParameterDump (channel));
//	}
//	
//	public void requestAllPrograms (int channel) {
//		requestProgramParameterDump (channel, 0, 0, 0);
//	}
//	
//	public void requestBankProgams (int channel, int bank) {
//		requestProgramParameterDump (channel, 1, bank, 0);
//	}
//	
//	public void requestSingleProgram (int channel, int bank, int prog) {
//		requestProgramParameterDump (channel, 2, bank, prog);
//	}
//	
//	public void requestProgramParameterDump (int channel, int kind, int bank, int prog) {
//		sendMsg (TritonRequest.requestProgramParameterDump (channel, kind, bank, prog));
//	}
//	
//	/*****************************
//	 *          COMBI DATA
//	 *****************************/
//	
//	public void requestCurrentCombiParameterDump (int channel) {
//		byte data[] = new byte[1];
//		data[0] = 0x00; // reserved
//		sendMsg ((TritonRequest.buildRequest(TritonRequest.CURRENT_COMBINATION_PARAMETER_DUMP_REQUEST, channel, data)));
//	}
//	
//	public void requestAllCombis (int channel) {
//		requestCombiParameterDump (channel, 0, 0, 0);
//	}
//	
//	public void requestBankCombis (int channel, int bank) {
//		requestCombiParameterDump (channel, 1, bank, 0);
//	}
//	
//	public void requestSingleCombi (int channel, int bank, int prog) {
//		requestCombiParameterDump (channel, 2, bank, prog);
//	}
//	
//	public void requestCombiParameterDump (int channel, int kind, int bank, int prog) {
//		byte data[] = new byte[3];
//		data[0] =  (byte) (((byte)kind << 4) & 0x30);  // 00kk 0bbb
//		data[0] += (byte) (((byte)bank) & 0x07);
//		data[1] = (byte) (0x7F & (byte)prog);
//		data[2] = 0x00; // reserved
//		sendMsg ((TritonRequest.buildRequest(TritonRequest.COMBINATION_PARAMETER_DUMP_REQUEST, channel, data)));
//	}
//	
//	////////////////////////////////////
//	
//	public void requestSequenceData (int channel) {
//		byte data[] = new byte[1];
//		data[0] = 0x00; // reserved
//		sendMsg ((TritonRequest.buildRequest(TritonRequest.SEQUENCE_DATA_DUMP_REQUEST, channel, data)));
//	}
//	
//	public void requestGlobalData (int channel) {
//		byte data[] = new byte[1];
//		data[0] = 0x00; // reserved
//		sendMsg ((TritonRequest.buildRequest(TritonRequest.GLOBAL_DATA_DUMP_REQUEST, channel, data)));
//	}
//	
//	/*****************************
//	 *          DK DATA
//	 *****************************/
//	
//	public void requestAllDrumkits (int channel) {
//		requestDrumkit (channel, 0, 0);
//	}
//	
//	public void requestDrumkit (int channel, int kind, int dumkit) {
//		byte data[] = new byte[3];
//		data[0] = (byte) (0x01 & (byte)kind);    // 0000 000k
//		data[1] = (byte) (0x7F & (byte)dumkit);  // 0ddd dddd
//		data[2] = 0x00; // reserved
//		
//		sendMsg ((TritonRequest.buildRequest(TritonRequest.DRUMKIT_DATA_DUMP_REQUEST, channel, data)));
//	}
//	
//	/*****************************
//	 *          Arp DATA
//	 *****************************/
//	
//	public void requestAllApreggios (int channel) {
//		requestArpeggio (channel, 0, 0);
//	}
//	
//	public void requestArpeggio (int channel, int kind, int arp) {
//		byte data[] = new byte[3];
//		data[0] = (byte) (0x01 & (byte)kind);        // 0000 000k
//		data[1] = (byte) (0x01 & ((byte)arp >> 7));  // 0ddd dddd
//		data[2] = (byte) (0x7F & (byte)arp);         // 0ddd dddd
//		
//		sendMsg ((TritonRequest.buildRequest(TritonRequest.DRUMKIT_DATA_DUMP_REQUEST, channel, data)));
//	}
//	
//	/////////////////////////////////
//	
//	public void requestAllData (int channel) {
//		byte data[] = new byte[1];
//		data[0] = 0x00; // reserved
//		sendMsg ((TritonRequest.buildRequest(TritonRequest.ALL_DATA_DUMP_REQUEST, channel, data)));
//	}
//	
//	public void requestProgramWrite (int channel, int bank, int prog) {
//		byte data[] = new byte[2];
//		data[0] = (byte) (0x07 & (byte)bank); // 0000 0bbb
//		data[1] = (byte) (0x7F & (byte)prog); // 0ppp pppp
//		
//		sendMsg ((TritonRequest.buildRequest(TritonRequest.PROGRAM_WRITE_REQUEST, channel, data)));
//	}
//	
//	public void requestCombiWrite (int channel, int bank, int combi) {
//		byte data[] = new byte[2];
//		data[0] = (byte) (0x07 & (byte)bank);  // 0000 0bbb
//		data[1] = (byte) (0x7F & (byte)combi); // 0ccc cccc
//		
//		sendMsg ((TritonRequest.buildRequest(TritonRequest.COMBINATION_WRITE_REQUEST, channel, data)));
//	}
//	
//	public void requestDeviceInformatin(int channel) {
//		sendMsg(TritonRequest.requestDeviceInformatin(channel));
//	}
	
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
	
//	private MidiParser _midiParser = new MidiParser();
}