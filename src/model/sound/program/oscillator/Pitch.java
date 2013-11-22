package model.sound.program.oscillator;

class Pitch {
    public int unpack (byte data[], int offset) {
        _octave = data[offset++];
        _transpose = data[offset++];
        _tuneMSB = data[offset++];
        _tuneLSB = data[offset++];
        _amSourcePitch = data[offset++];
        _intByAmPitch = data[offset++];
        _pitchSlope = data[offset++];
        _intByPitchEg = data[offset++];
        _amSourcePEG = data[offset++];
        _intByAmPEG = data[offset++];
        _intByLFO1 = data[offset++];
        _intByLFO2 = data[offset++];
        
        _portamento = data[offset];
        _portamentFingered = (data[offset] & 0x02) >> 1;
        ++offset;
        
        _portamentoTime = data[offset++];
        _pitchByJsPos = data[offset++];
        _pitchByJsNeg = data[offset++];
        _pitchByRibbon = data[offset++];
        _reserved = data[offset++];
        _lfo1IntByJS = data[offset++];
        _lfo2IntByJS = data[offset++];
        _amSourceLFO1 = data[offset++];
        _intByAmLFO1 = data[offset++];
        _amSourceLFO2 = data[offset++];
        _intByAmLFO2 = data[offset++];
        
        return offset;
    }
    
    public int pack (byte data[], int offset) {
        data[offset++] = (byte)_octave;
        data[offset++] = (byte)_transpose;
        data[offset++] = (byte)_tuneMSB;
        data[offset++] = (byte)_tuneLSB;
        data[offset++] = (byte)_amSourcePitch;
        data[offset++] = (byte)_intByAmPitch;
        data[offset++] = (byte)_pitchSlope;
        data[offset++] = (byte)_intByPitchEg;
        data[offset++] = (byte)_amSourcePEG;
        data[offset++] = (byte)_intByAmPEG;
        data[offset++] = (byte)_intByLFO1;
        data[offset++] = (byte)_intByLFO2;
        
        data[offset]  = (byte)_portamento;
        data[offset] |= (byte)(_portamentFingered & 0x02) << 1;
        ++offset;
        
        data[offset++] = (byte)_portamentoTime;
        data[offset++] = (byte)_pitchByJsPos;
        data[offset++] = (byte)_pitchByJsNeg;
        data[offset++] = (byte)_pitchByRibbon;
        data[offset++] = (byte)_reserved;
        data[offset++] = (byte)_lfo1IntByJS;
        data[offset++] = (byte)_lfo2IntByJS;
        data[offset++] = (byte)_amSourceLFO1;
        data[offset++] = (byte)_intByAmLFO1;
        data[offset++] = (byte)_amSourceLFO2;
        data[offset++] = (byte)_intByAmLFO2;
        
        return offset;
    }
    
    private int _octave;             // byte  0
    private int _transpose;          // byte  1
    private int _tuneMSB;            // byte  2
    private int _tuneLSB;            // byte  3
    private int _amSourcePitch;      // byte  4
    private int _intByAmPitch;       // byte  5
    private int _pitchSlope;         // byte  6
    private int _intByPitchEg;       // byte  7
    private int _amSourcePEG;        // byte  8
    private int _intByAmPEG;         // byte  9
    private int _intByLFO1;          // byte 10
    private int _intByLFO2;          // byte 11
    private int _portamento;         // byte 12, bit 0
    private int _portamentFingered;  // byte 12, bit 1
    private int _portamentoTime;     // byte 13
    private int _pitchByJsPos;       // byte 14
    private int _pitchByJsNeg;       // byte 15
    private int _pitchByRibbon;      // byte 16
    private int _reserved;           // byte 17
    private int _lfo1IntByJS;        // byte 18
    private int _lfo2IntByJS;        // byte 19
    private int _amSourceLFO1;       // byte 20
    private int _intByAmLFO1;        // byte 21
    private int _amSourceLFO2;       // byte 22
    private int _intByAmLFO2;        // byte 23
    
    public static int BYTE_SIZE = 24;
}