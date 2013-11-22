package model.sound.combination;

import java.util.ArrayList;

import model.sound.Sound;
import model.sound.Location;

public class Combination extends Sound {
    public Combination () {
        for (int i = 0; i < _arp.length; ++i) {
            _arp[i] = new Arpeggiator();
        }
        for (int i = 0; i < _timbre.length; ++i) {
            _timbre[i] = new Timbre();
        }
    }

    /**
     * 
     * @param data
     * @param offset
     * @return
     */
    public int unpack(byte data[], int offset) {
        offset = super.unpack(data, offset);
        
        offset = _arpHdr.unpack(data, offset);
        for (int i = 0; i < _arp.length; ++i) {
            offset = _arp[i].unpack(data, offset);
        }
        
        offset = _common.unpack(data, offset);
        
        for (int i = 0; i < _timbre.length; ++i) {
            offset = _timbre[i].unpack(data, offset);
        }
        
        return offset;
    }

    /**
     * 
     */
    public int pack(byte data[], int offset) {
        offset = super.pack(data, offset);
        
        offset = _arpHdr.pack(data, offset);
        for (int i = 0; i < _arp.length; ++i) {
            offset = _arp[i].pack(data, offset);
        }
        
        offset = _common.pack(data, offset);
        
        for (int i = 0; i < _timbre.length; ++i) {
            offset = _timbre[i].pack(data, offset);
        }
        
        return offset;
    }
    
    public byte[] pack () {
        byte[] retArr = new byte[BYTE_SIZE];
        pack (retArr, 0);
        return retArr;
    }
    
    /**
     * Get the location of the programs that are linked to this combination.
     * @return
     */
    public ArrayList<Location> getProgramLocations () {
        ArrayList<Location> arr = new ArrayList<Location>();
        
        for (Timbre t : _timbre) {
            arr.add(t.getProgLocation());
        }
        
        return arr;
    }
    
    /**
     * 
     */
	public String toString () {
		return _name;
	}
	
    private ArpeggiatorHeader _arpHdr = new ArpeggiatorHeader();
    private Arpeggiator _arp[] = new Arpeggiator[2];
    private Common _common = new Common();
    private Timbre _timbre[] = new Timbre[8];
	
	
	public static int BYTE_SIZE = 448;
}