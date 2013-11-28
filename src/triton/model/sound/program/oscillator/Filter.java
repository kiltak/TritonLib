package triton.model.sound.program.oscillator;

class Filter {
    public int unpack (byte data[], int offset) {
        _frequency = data[offset++];
        _kbdTrackIntensity = data[offset++];
        _amSourceMOD1 = data[offset++];
        _intByAmMOD1 = data[offset++];
        _amSourceMOD2 = data[offset++];
        _intByAmMOD2 = data[offset++];
        _egIntensity = data[offset++];
        _egVelocity = data[offset++];
        _intByLFO1 = data[offset++];
        _intByLFO2 = data[offset++];
        _lfo1byJs = data[offset++];
        _lfo2byJs = data[offset++];
        _intByAmEg = data[offset++];
        _intByAmLFO1 = data[offset++];
        _intByAmLFO2 = data[offset++];
        
        return offset;
    }
    
    public int pack (byte data[], int offset) {
        data[offset++] = (byte)_frequency;
        data[offset++] = (byte)_kbdTrackIntensity;
        data[offset++] = (byte)_amSourceMOD1;
        data[offset++] = (byte)_intByAmMOD1;
        data[offset++] = (byte)_amSourceMOD2;
        data[offset++] = (byte)_intByAmMOD2;
        data[offset++] = (byte)_egIntensity;
        data[offset++] = (byte)_egVelocity;
        data[offset++] = (byte)_intByLFO1;
        data[offset++] = (byte)_intByLFO2;
        data[offset++] = (byte)_lfo1byJs;
        data[offset++] = (byte)_lfo2byJs;
        data[offset++] = (byte)_intByAmEg;
        data[offset++] = (byte)_intByAmLFO1;
        data[offset++] = (byte)_intByAmLFO2;
        
        return offset;
    }
    
    private int _frequency;         // byte  0
    private int _kbdTrackIntensity; // byte  1
    private int _amSourceMOD1;      // byte  2
    private int _intByAmMOD1;       // byte  3
    private int _amSourceMOD2;      // byte  4
    private int _intByAmMOD2;       // byte  5
    private int _egIntensity;       // byte  6
    private int _egVelocity;        // byte  7
    private int _intByLFO1;         // byte  8
    private int _intByLFO2;         // byte  9
    private int _lfo1byJs;          // byte 10
    private int _lfo2byJs;          // byte 11
    private int _intByAmEg;         // byte 12
    private int _intByAmLFO1;       // byte 13
    private int _intByAmLFO2;       // byte 14
    
    public static int BYTE_SIZE = 15;
}