package model.sound.program;

class PitchEg {
    public int unpack (byte data[], int offset) {
        _startLevel = data[offset++];
        _attackTime = data[offset++];
        _attackLevel = data[offset++];
        _decayTime = data[offset++];
        _releaseTime = data[offset++];
        _releaseLevel = data[offset++];
        _amSourceLevel1 = data[offset++];
        _intByAmLevel1 = data[offset++];
        _amSourceLevel2 = data[offset++];
        _intByAmLevel2 = data[offset++];
        _amSourceTime = data[offset++];
        _intByAmTime = data[offset++];
        
        _startAmLevel1 = data[offset] & 0x03;
        _attackAmLevel1  = (data[offset] & 0x0C) >> 2;
        _startAmLevel2 = (data[offset] & 0x30) >> 4;
        _attackAmLevel2 = (data[offset] & 0xC0) >> 6;
        ++offset;
        
        _attackAmTime = data[offset] & 0x03;
        _decayAmTime = (data[offset] & 0x0C) >> 2;
        ++offset;
        
        return offset;
    }
    
    public int pack (byte data[], int offset) {
        data[offset++] = (byte)_startLevel;
        data[offset++] = (byte)_attackTime;
        data[offset++] = (byte)_attackLevel;
        data[offset++] = (byte)_decayTime;
        data[offset++] = (byte)_releaseTime;
        data[offset++] = (byte)_releaseLevel;
        data[offset++] = (byte)_amSourceLevel1;
        data[offset++] = (byte)_intByAmLevel1;
        data[offset++] = (byte)_amSourceLevel2;
        data[offset++] = (byte)_intByAmLevel2;
        data[offset++] = (byte)_amSourceTime;
        data[offset++] = (byte)_intByAmTime;
        
        data[offset]  = (byte)_startAmLevel1;
        data[offset] |= (byte)(_attackAmLevel1 << 2);
        data[offset] |= (byte)(_startAmLevel2 << 4);
        data[offset] |= (byte)(_attackAmLevel2 << 6);
        ++offset;
        
        data[offset]  = (byte)_attackAmTime;
        data[offset] |= (byte)(_decayAmTime << 2);
        ++offset;
        
        return offset;
    }
    
    private int _startLevel;     // byte  0
    private int _attackTime;     // byte  1
    private int _attackLevel;    // byte  2
    private int _decayTime;      // byte  3
    private int _releaseTime;    // byte  4
    private int _releaseLevel;   // byte  5
    private int _amSourceLevel1; // byte  6
    private int _intByAmLevel1;  // byte  7
    private int _amSourceLevel2; // byte  8
    private int _intByAmLevel2;  // byte  9
    private int _amSourceTime;   // byte 10
    private int _intByAmTime;    // byte 11
    private int _startAmLevel1;  // byte 12, bits 0-1
    private int _attackAmLevel1; // byte 12, bits 2-3
    private int _startAmLevel2;  // byte 12, bits 4-5
    private int _attackAmLevel2; // byte 12, bits 6-7
    private int _attackAmTime;   // byte 13, bits 0-1
    private int _decayAmTime;    // byte 13, bits 2-3
    
    public static int BYTE_SIZE = 14;
}