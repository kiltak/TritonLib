package model.sound.program.oscillator;

class FilterEg {
    public int unpack (byte data[], int offset) {
        _startLevel = data[offset++];
        _attackTime = data[offset++];
        _attacklevel = data[offset++];
        _decayTime = data[offset++];
        _breakPointLevel = data[offset++];
        _slopeTime = data[offset++];
        _sustainLevel = data[offset++];
        _releaseTime = data[offset++];
        _releaseLevel = data[offset++];
        
        _releaseAmTime1 = (data[offset] & 0xC0) >> 6;
        _slopeAmTime1 = (data[offset] & 0x30) >> 4;
        _decaySmTime1 = (data[offset] & 0x0C) >> 2;
        _attackAmTime1 = data[offset] & 0x03;
        ++offset;
        
        _releaseAmTime2 = (data[offset] & 0xC0) >> 6;
        _slopeAmTime2 = (data[offset] & 0x30) >> 4;
        _decaySmTime2 = (data[offset] & 0x0C) >> 2;
        _attackAmTime2 = data[offset] & 0x03;
        ++offset;
        
        _breakAmLevel = (data[offset] & 0x30) >> 4;
        _attackAmLevel = (data[offset] & 0x0C) >> 2;
        _startAmLevel = data[offset] & 0x03;
        ++offset;
        
        _amSourceTime1 = data[offset++];
        _intByAmTime1 = data[offset++];
        _amSourceTime2 = data[offset++];
        _intByAmTime2 = data[offset++];
        _amSourceLevel = data[offset++];
        _intByAmLevel = data[offset++];
        
        return offset;
    }
    
    public int pack (byte data[], int offset) {
        data[offset++] = (byte)_startLevel;
        data[offset++] = (byte)_attackTime;
        data[offset++] = (byte)_attacklevel;
        data[offset++] = (byte)_decayTime;
        data[offset++] = (byte)_breakPointLevel;
        data[offset++] = (byte)_slopeTime;
        data[offset++] = (byte)_sustainLevel;
        data[offset++] = (byte)_releaseTime;
        data[offset++] = (byte)_releaseLevel;

        data[offset]  = (byte)(_releaseAmTime1 << 6);
        data[offset] |= (byte)(_slopeAmTime1 << 4);
        data[offset] |= (byte)(_decaySmTime1 << 2);
        data[offset] |= (byte)_attackAmTime1;
        ++offset;
        
        data[offset]  = (byte)(_releaseAmTime2 << 6);
        data[offset] |= (byte)(_slopeAmTime2 << 4);
        data[offset] |= (byte)(_decaySmTime2 << 2);
        data[offset] |= (byte)_attackAmTime2;
        ++offset;
        
        data[offset]  = (byte)(_breakAmLevel << 4);
        data[offset] |= (byte)(_attackAmLevel << 2);
        data[offset] |= (byte)_startAmLevel;
        ++offset;
        
        data[offset++] = (byte)_amSourceTime1;
        data[offset++] = (byte)_intByAmTime1;
        data[offset++] = (byte)_amSourceTime2;
        data[offset++] = (byte)_intByAmTime2;
        data[offset++] = (byte)_amSourceLevel;
        data[offset++] = (byte)_intByAmLevel;
        
        return offset;
    }
    
    private int _startLevel;      // byte 0
    private int _attackTime;      // byte 1
    private int _attacklevel;     // byte 2
    private int _decayTime;       // byte 3
    private int _breakPointLevel; // byte 4
    private int _slopeTime;       // byte 5
    private int _sustainLevel;    // byte 6
    private int _releaseTime;     // byte 7
    private int _releaseLevel;    // byte 8
    private int _releaseAmTime1;  // byte 9, bits 6-7
    private int _slopeAmTime1;    // byte 9, bits 4-5
    private int _decaySmTime1;    // byte 9, bits 2-3
    private int _attackAmTime1;   // byte 9, bits 0-1
    private int _releaseAmTime2;  // byte 10, bits 6-7
    private int _slopeAmTime2;    // byte 10, bits 4-5
    private int _decaySmTime2;    // byte 10, bits 2-3
    private int _attackAmTime2;   // byte 10, bits 0-1
    private int _breakAmLevel;    // byte 11, bits 4-5
    private int _attackAmLevel;   // byte 11, bits 2-3
    private int _startAmLevel;    // byte 11, bits 0-1
    private int _amSourceTime1;   // byte 12
    private int _intByAmTime1;    // byte 13
    private int _amSourceTime2;   // byte 14
    private int _intByAmTime2;    // byte 15
    private int _amSourceLevel;   // byte 16
    private int _intByAmLevel;    // byte 17
    
    public static int BYTE_SIZE = 18;
}