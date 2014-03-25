package triton.model.sound.program.oscillator;

import triton.util.Limits;

class KeyboardTrack {
    public int unpack (byte data[], int offset) {
        _keyLow = data[offset++] & 0x7F;
        _rampLow = data[offset++];
        _keyHigh = data[offset++] & 0x7F;
        _rampHigh = data[offset++];
        
        validate();
        
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
    
    private boolean validate () {
        boolean retVal = true;
        
        try {
            Limits.checkLimits ("KeyboardTrack._keyLow", _keyLow, 0, 0x7F);
            Limits.checkLimits ("KeyboardTrack._rampLow", _rampLow, -99, 99);
            Limits.checkLimits ("KeyboardTrack._keyHigh", _keyHigh, 0, 0x7F);
            Limits.checkLimits ("KeyboardTrack._rampHigh", _rampHigh, -99, 99);
        }
        catch (IllegalArgumentException e) {
            System.out.println (e);
            retVal = false;
        }
        
        return retVal;
    }
    
    private int _keyLow;   // byte 0
    private int _rampLow;  // byte 1
    private int _keyHigh;  // byte 2
    private int _rampHigh; // byte 3
    
    public static int BYTE_SIZE = 4;
}