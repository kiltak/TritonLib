package triton.model.sound;

import triton.model.sound.combination.Combination;
import triton.model.sound.program.Program;

public class Bank {
    public Bank(int type, int bankNo) {
        switch (type) {
            case PROG:
                _bankType = Bank.PROG;
                _sounds = new Program[128];
                for (int i = 0; i < _sounds.length; ++i) {
                    _sounds[i] = new Program();
                }
                break;
            case COMBI:
                _bankType = Bank.COMBI;
                _sounds = new Combination[128];
                for (int i = 0; i < _sounds.length; ++i) {
                    _sounds[i] = new Combination();
                }
                break;
        }
        
        _bankNo = bankNo;
    }
    
    /**
     * Pack the bank into a byte array.
     * 
     * @param data
     * @param offset
     * @return
     */
    public int pack(byte data[], int offset) {
        for (int i = 0; i < _sounds.length; ++i) {
            offset = _sounds[i].pack(data, offset);
        }
        
        return offset;
    }
    
    public byte[] pack() {
        byte[] retArr;
        if (_sounds[0] instanceof Program) {
            retArr = new byte[Program.BYTE_SIZE * 128];
        }
        else {
            retArr = new byte[Combination.BYTE_SIZE * 128];
        }
        
        pack (retArr, 0);
        return retArr;
    }
    
    public void set (Sound s, int offset) {
        _sounds[offset] = s;
    }
    
    public Sound get (int offset) {
        return _sounds[offset];
    }
    
    public String toString (String prefix) {
        String retString = "";
        
        for (int i = 0; i < _sounds.length; ++i) {
            if (_sounds[i].toString() != null) {
                retString += String.format("%s-%03d: ", prefix, i) + _sounds[i].toString() + "\n";
            }
        }
        
        return retString;
    }
    
    public String toString () {
        String retString = "";
        
        for (Sound s : _sounds) {
            retString += s.toString() + "\n";
        }
        
        return retString;
    }
    
    /**
     * Return an array of the sound names.
     * @return
     */
    public String[] getNames () {
        String arr[] = new String[128];
        
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = _sounds[i].getName();
        }
        
        return arr;
    }
    
    /**
     * Get the string name of the bank.  Easy for humans to read.
     * @return
     */
    public String getName () {
        return BANK_NAMES[_bankType][_bankNo];
    }
    
    /**
     * Get the array of bank names (Prog or Combi).
     * @return
     */
    public String[] getBankNames () {
        return BANK_NAMES[_bankType];
    }
    
    public Sound[] _sounds;
    private final int _bankNo;
    private int _bankType;
    
    public final static String[][] BANK_NAMES = {
        // Programs
        {"IntA", "IntB", "IntC", "IntD", "IntE", "IntF", "ExtA", "ExtB", "ExtC", "ExtD", "ExtE", "ExtF", "ExtG", "ExtH"},
        // Combinations
        {"IntA", "IntB", "IntC", "IntD", "IntE", "ExtA", "ExtB", "ExtC", "ExtD", "ExtE", "ExtF", "ExtG", "ExtH"}
    };;
    
    public final static int PROG  = 0;
    public final static int COMBI = 1;
    
    public final static int PROG_INTA = 0;
    public final static int PROG_INTB = 1;
    public final static int PROG_INTC = 2;
    public final static int PROG_INTD = 3;
    public final static int PROG_INTE = 4;
    public final static int PROG_INTF = 5;
    public final static int PROG_EXTA = 6;
    public final static int PROG_EXTB = 7;
    public final static int PROG_EXTC = 8;
    public final static int PROG_EXTD = 9;
    public final static int PROG_EXTE = 10;
    public final static int PROG_EXTF = 11;
    public final static int PROG_EXTG = 12;
    public final static int PROG_EXTH = 13;
    public final static int NUMBER_OF_PROG_BANKS = 14;
    
    public final static int COMBI_INTA = 0;
    public final static int COMBI_INTB = 1;
    public final static int COMBI_INTC = 2;
    public final static int COMBI_INTD = 3;
    public final static int COMBI_INTE = 4;
    public final static int COMBI_EXTA = 5;
    public final static int COMBI_EXTB = 6;
    public final static int COMBI_EXTC = 7;
    public final static int COMBI_EXTD = 8;
    public final static int COMBI_EXTE = 9;
    public final static int COMBI_EXTF = 10;
    public final static int COMBI_EXTG = 11;
    public final static int COMBI_EXTH = 12;
    public final static int NUMBER_OF_COMBI_BANKS = 13;
    
}