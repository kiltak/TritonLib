package model.sound.program.oscillator;

class Output {
    public int unpack (byte data[], int offset) {
        _reserved = data[offset++];
        _pan = data[offset++];
        _amSource = data[offset++];
        _intByAm = data[offset++];
        _send1 = data[offset++];
        _send2 = data[offset++];
        
        return offset;
    }
    
    public int pack (byte data[], int offset) {
        data[offset++] = _reserved;
        data[offset++] = (byte)_pan;
        data[offset++] = (byte)_amSource;
        data[offset++] = (byte)_intByAm;
        data[offset++] = (byte)_send1;
        data[offset++] = (byte)_send2;
        return offset;
    }
    
    public String toString () {
        String retString = "";

        retString += "Reserved  " + _reserved + "\n";
        retString += "Pan       " + _pan + "\n";
        retString += "AM Source " + _amSource + "\n";
        retString += "IntByAM   " + _intByAm + "\n";
        retString += "Send 1    " + _send1 + "\n";
        retString += "Send 2    " + _send2 + "\n";
        
        return retString;
    }
    
    private byte _reserved;  // byte 0
    private int _pan;        // byte 1
    private int _amSource;   // byte 2
    private int _intByAm;    // byte 3
    private int _send1;      // byte 4
    private int _send2;      // byte 5
    
    public static int BYTE_SIZE = 6;
}