package triton.model.sound.combination;

public class ArpeggiatorHeader {
    public int unpack (byte data[], int offset) {
        _tempo   = data[offset++];
        _switch  =  data[offset] & 0x01;
        _arpRunA = (data[offset] & 0x02) >> 2;
        _arpRunB = (data[offset] & 0x04) >> 3;
        ++offset;
        
        return offset;
    }
    
    public int pack (byte data[], int offset) {
        data[offset++] = (byte)_tempo;
        data[offset] = (byte) _switch;
        data[offset] = (byte)(_arpRunA << 2);
        data[offset] = (byte)(_arpRunB << 3);
        ++offset;
        
        return offset;
    }
    
    public String toString () {
        String retString = "";
        
        retString += "Tempo " + _tempo + "\n";
        retString += "Switch " + _switch + "\n";
        retString += "ArpRunA" + _arpRunA + "\n";
        retString += "ArpRunB" + _arpRunB + "\n";
        
        return retString;
    }
    
    private int _tempo;   // byte 0         valid values 40-240
    private int _switch;  // byte 1, bit 0
    private int _arpRunA; // byte 1, bit 1
    private int _arpRunB; // byte 1, bit 2
    
    public static int BYTE_SIZE = 2;
}
