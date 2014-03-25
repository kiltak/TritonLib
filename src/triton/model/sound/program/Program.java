package triton.model.sound.program;

import java.util.HashSet;
import java.util.Set;

import triton.model.sound.Location;
import triton.model.sound.Sound;
import triton.model.sound.program.oscillator.*;


public class Program extends Sound {
    public Program () {
        for (int i = 0; i < _osc.length; ++i) {
            _osc[i] = new Oscillator();
        }
    }
    
    /**
     * 
     * @param data
     * @param offset
     * @return
     */
    public int unpack(byte data[], int offset) {
        byte[] oldArr = new byte[Program.BYTE_SIZE];
        System.arraycopy(data, offset, oldArr, 0, Program.BYTE_SIZE);
        offset = super.unpack(data, offset);
        
        System.out.println ("unpacking " + _name);
        
        // Unpack the other stuff
        offset = _arp.unpack(data, offset);

        offset = _common.unpack(data, offset);

        offset = _pitchEg.unpack(data, offset);

        for (int i = 0; i < _osc.length; ++i) {
            offset = _osc[i].unpack(data, offset);
        }
        
        for (int i = 0; i < _reserved.length; ++i) {
            _reserved[i] = data[offset++];
        }
        
//        byte[] cmp = pack();
//        for (int z = 0; z < cmp.length; ++z) {
//            if (cmp[z] != oldArr[z]) {
//                System.out.println (String.format("***** %d - new %02X, data %02X", z, cmp[z], oldArr[z]));
//            }
//        }
        
        return offset;
    }
    
    /**
     * Pack the data into the given array.  Not the most efficient way to do this...
     * 
     * @param data
     * @param offset
     * @return
     */
    public int pack (byte data[], int offset) {
        offset = super.pack(data, offset);

        offset = _arp.pack(data, offset);

        offset = _common.pack(data, offset);

        offset = _pitchEg.pack(data, offset);

        for (int i = 0; i < _osc.length; ++i) {
            offset = _osc[i].pack(data, offset);
        }
        
        for (int i = 0; i < _reserved.length; ++i) {
            data[offset++] = _reserved[i];
        }
        
        return offset;
    }
    
    public byte[] pack () {
        byte[] retArr = new byte[BYTE_SIZE];
        pack (retArr, 0);
        return retArr;
    }
    
    public void addCombi (Location l) {
        _combis.add (l);
    }
    
    public void clearCombis () {
        _combis.clear();
    }
    
    public Set<Location> getCombis () {
        return _combis;
    }
    
	public String toString () {
		return _name;
	}
	
	private Arpeggiator _arp = new Arpeggiator();
	private Common _common = new Common();
	private PitchEg _pitchEg = new PitchEg();
	private Oscillator[] _osc = new Oscillator[2];
	private byte[] _reserved = new byte[2];
	
	// The combinations the use this program
	private Set<Location> _combis = new HashSet<Location>();
	
	public static int BYTE_SIZE = 540;
}