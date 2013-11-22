package model.sound.program.oscillator;

class AmplifierEg {
    public int unpack (byte data[], int offset) {
        _startLevel = data[offset++];
        _attackTime = data[offset++];
        _attackLevel = data[offset++];
        _decayTime = data[offset++];
        _breakPointLevel = data[offset++];
        _slopeTime = data[offset++];
        _sustainLevel = data[offset++];
        _releaseTime = data[offset++];
        _amSourceTime1 = data[offset++];
        _intByAmTime1 = data[offset++];
        _amSourceTime2 = data[offset++];
        _intByAmTime2 = data[offset++];
        _amSourceLevel = data[offset++];
        _intByAmLevel = data[offset++];
        
        _attackAmTime1 = data[offset] & 0x03;
        _decayAmTime1 = (data[offset] & 0x0C) >> 2;
        _sloopeAmTime1 = (data[offset] & 0x30) >> 4;
        _releaseAmTime1 = (data[offset] & 0xC0) >> 6;
        ++offset;
        
        _attackAmTime2 = data[offset];
        _decayAmTime2 = (data[offset] & 0x0C) >> 2;
        _sloopeAmTime2 = (data[offset] & 0x30) >> 4;
        _releaseAmTime2 = (data[offset] & 0xC0) >> 6;
        ++offset;
        
        _startAmLevel = data[offset];
        _attackAmLevel = (data[offset] & 0x0C) >> 2;
        _breakAmLevel = (data[offset] & 0x30) >> 4;
        ++offset;
        
        _reserved = data[offset++];

        return offset;
    }
    
    public int pack (byte data[], int offset) {
        data[offset++] = (byte)_startLevel;
        data[offset++] = (byte)_attackTime;
        data[offset++] = (byte)_attackLevel;
        data[offset++] = (byte)_decayTime;
        data[offset++] = (byte)_breakPointLevel;
        data[offset++] = (byte)_slopeTime;
        data[offset++] = (byte)_sustainLevel;
        data[offset++] = (byte)_releaseTime;
        data[offset++] = (byte)_amSourceTime1;
        data[offset++] = (byte)_intByAmTime1;
        data[offset++] = (byte)_amSourceTime2;
        data[offset++] = (byte)_intByAmTime2;
        data[offset++] = (byte)_amSourceLevel;
        data[offset++] = (byte)_intByAmLevel;
        
        data[offset]  = (byte)_attackAmTime1;
        data[offset] |= (byte)(_decayAmTime1 << 2);
        data[offset] |= (byte)(_sloopeAmTime1 << 4);
        data[offset] |= (byte)(_releaseAmTime1 << 6);
        ++offset;
        
        data[offset]  = (byte)_attackAmTime2;
        data[offset] |= (byte)(_decayAmTime2 << 2);
        data[offset] |= (byte)(_sloopeAmTime2 << 4);
        data[offset] |= (byte)(_releaseAmTime2 << 6);
        ++offset;
        
        data[offset]  = (byte)_startAmLevel;
        data[offset] |= (byte)(_attackAmLevel << 2);
        data[offset] |= (byte)(_breakAmLevel << 4);
        ++offset;
        
        data[offset++] = (byte)_reserved;

        return offset;
    }
    
    private int _startLevel;      // byte  0
    private int _attackTime;      // byte  1
    private int _attackLevel;     // byte  2
    private int _decayTime;       // byte  3
    private int _breakPointLevel; // byte  4
    private int _slopeTime;       // byte  5
    private int _sustainLevel;    // byte  6
    private int _releaseTime;     // byte  7
    private int _amSourceTime1;   // byte  8
    private int _intByAmTime1;    // byte  9
    private int _amSourceTime2;   // byte 10
    private int _intByAmTime2;    // byte 11
    private int _amSourceLevel;   // byte 12
    private int _intByAmLevel;    // byte 13
    private int _attackAmTime1;   // byte 14, bits 0-1
    private int _decayAmTime1;    // byte 14, bits 2-3
    private int _sloopeAmTime1;   // byte 14, bits 4-5
    private int _releaseAmTime1;  // byte 14, bits 6-7
    private int _attackAmTime2;   // byte 15, bits 0-1
    private int _decayAmTime2;    // byte 15, bits 2-3
    private int _sloopeAmTime2;   // byte 15, bits 4-5
    private int _releaseAmTime2;  // byte 15, bits 6-7
    private int _startAmLevel;    // byte 16, bits 0-1
    private int _attackAmLevel;   // byte 16, bits 2-3
    private int _breakAmLevel;    // byte 16, bits 4-5
    private int _reserved;        // byte 17
    
    public static int BYTE_SIZE = 18;
}