package model.sound.program.oscillator;

class Header {
    public int unpack (byte data[], int offset) {
        _hiStartOffset = (data[offset] & 0x80) >> 7;
        _hiReverse = (data[offset] & 0x40) >> 6;
        _hiSampleNoMSB = data[offset] & 0x3F;
        ++offset;
        
        _hiSampleNoLSB = data[offset++];
        _hiBank = data[offset++];
        _hiLevel = data[offset++];
        
        _lowStartOffset = (data[offset] & 0x80) >> 7;
        _lowReverse = (data[offset] & 0x40) >> 6;
        _lowSampleNoMSB = data[offset] & 0x3F;
        ++offset;
        
        _lowSampleNoLSB = data[offset++];
        _lowBank = data[offset++];
        _lowLevel = data[offset++];
        _delayStart = data[offset++];
        _velMSampleSw = data[offset++];
        _velZoneBottom = data[offset++];
        _velZoneTop = data[offset++];
        
        return offset;
    }
    
    public int pack (byte data[], int offset) {
        data[offset]  = (byte)(_hiStartOffset << 7);
        data[offset] |= (byte)(_hiReverse << 6);
        data[offset] |= (byte)_hiSampleNoMSB;
        ++offset;
        
        data[offset++] = (byte)_hiSampleNoLSB;
        data[offset++] = (byte)_hiBank;
        data[offset++] = (byte)_hiLevel;
        
        data[offset]  = (byte)(_lowStartOffset << 7);
        data[offset] |= (byte)(_lowReverse << 6);
        data[offset] |= (byte)_lowSampleNoMSB;
        ++offset;
        
        data[offset++] = (byte)_lowSampleNoLSB;
        data[offset++] = (byte)_lowBank;
        data[offset++] = (byte)_lowLevel;
        data[offset++] = (byte)_delayStart;
        data[offset++] = (byte)_velMSampleSw;
        data[offset++] = (byte) _velZoneBottom;
        data[offset++] = (byte)_velZoneTop;
        
        return offset;
    }
    
    private int _hiStartOffset;   // byte  0, bit  7
    private int _hiReverse;       // byte  0, bit  6 
    private int _hiSampleNoMSB;   // byte  0, bits 0-5
    private int _hiSampleNoLSB;   // byte  1
    private int _hiBank;          // byte  2
    private int _hiLevel;         // byte  3
    private int _lowStartOffset;  // byte  4, bit  7
    private int _lowReverse;      // byte  4, bit  6
    private int _lowSampleNoMSB;  // byte  4, bit  0-5
    private int _lowSampleNoLSB;  // byte  5
    private int _lowBank;         // byte  6
    private int _lowLevel;        // byte  7
    private int _delayStart;      // byte  8
    private int _velMSampleSw;    // byte  9
    private int _velZoneBottom;   // byte 10
    private int _velZoneTop;      // byte 11
    
    public static int BYTE_SIZE = 12;
}