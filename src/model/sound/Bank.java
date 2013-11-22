package model.sound;

import model.sound.combination.Combination;
import model.sound.program.Program;

public class Bank {
    public Bank(int type, int bankNo) {
        switch (type) {
            case PROG:
                _sounds = new Program[128];
                for (int i = 0; i < _sounds.length; ++i) {
                    _sounds[i] = new Program();
                }
                break;
            case COMBI:
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
        return BANK_NAMES[_bankNo];
    }
    
    public Sound[] _sounds;
    private final int _bankNo;
    
    public final static int PROG  = 0;
    public final static int COMBI = 1;
    
    public static int NUMBER_OF_BANKS = 0;
    public final static int INTA = NUMBER_OF_BANKS++;
    public final static int INTB = NUMBER_OF_BANKS++;
    public final static int INTC = NUMBER_OF_BANKS++;
    public final static int INTD = NUMBER_OF_BANKS++;
    public final static int INTE = NUMBER_OF_BANKS++;
    public final static int INTF = NUMBER_OF_BANKS++;
    public final static int EXTA = NUMBER_OF_BANKS++;
    public final static int EXTB = NUMBER_OF_BANKS++;
    public final static int EXTC = NUMBER_OF_BANKS++;
    public final static int EXTD = NUMBER_OF_BANKS++;
    public final static int EXTE = NUMBER_OF_BANKS++;
    public final static int EXTF = NUMBER_OF_BANKS++;
    public final static int EXTG = NUMBER_OF_BANKS++;
    public final static int EXTH = NUMBER_OF_BANKS++;
    
    public final static String[] BANK_NAMES = {"IntA", "IntB", "IntC", "IntD", "IntE", "IntF",
                                        "ExtA", "ExtB", "ExtC", "ExtD", "ExtE", "ExtF", "ExtG", "ExtH"};
}