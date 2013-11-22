package model.sound.program.oscillator;

class KeyboardTrack {
    public int unpack (byte data[], int offset) {
        _keyLow = data[offset++];
        _rampLow = data[offset++];
        _keyHigh = data[offset++];
        _rampHigh = data[offset++];
        
        return offset;
    }
    
    public int pack (byte data[], int offset) {
        data[offset++] = (byte)_keyLow;
        data[offset++] = (byte)_rampLow;
        data[offset++] = (byte)_keyHigh;
        data[offset++] = (byte)_rampHigh;
        return offset;
    }
    
    public String toString () {
        String retString = "";
        
        retString += "KeyLow " + _keyLow + "\n";
        retString += "RampLow " + _rampLow + "\n";
        retString += "KeyHigh " + _keyHigh + "\n";
        retString += "RampHigh " + _rampHigh + "\n";
        
        return retString;
    }
    
    private int _keyLow;   // byte 0
    private int _rampLow;  // byte 1
    private int _keyHigh;  // byte 2
    private int _rampHigh; // byte 3
    
    public static int BYTE_SIZE = 4;
}