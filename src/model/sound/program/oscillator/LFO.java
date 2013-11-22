package model.sound.program.oscillator;

class LFO {
    public int unpack (byte data[], int offset) {
        _waveform = (short)(data[offset] & 0x1F);
        _keySync = ((data[offset] & 0x80) != 0);
        offset++;
        
        _frequency = data[offset++];
        _offset = data[offset++];
        _delay = data[offset++];
        _fade = data[offset++];
        
        _midiTempoSync = (byte)((data[offset] & 0x80) >> 7);
        _syncBaseNote = (byte)((data[offset] & 0x70) >> 4);
        _times = (byte)(data[offset] & 0x0F);
        offset++;
        
        _amSourceTime1 = data[offset++];
        _intByAmTime1 = data[offset++];
        _amSourceTime2 = data[offset++];
        _intByAmTime2 = data[offset++];
        
        return offset;
    }
    
    private String getWaveformString() {
        switch (_waveform) {
            case 0:
                return "Triangle 0";
            case 1:
                return "Triangle 90";
            case 2:
                return "Triangle Random";
            case 3:
                return "Saw 0";
            case 4:
                return "Saw 180";
            case 5:
                return "Square";
            case 6:
                return "Sine";
            case 7:
                return "Guitar";
            case 8:
                return "Exponential Triangle";
            case 9:
                return "Exponential Saw Down";
            case 0xA:
                return "Exponential Saw Up";
            case 0xB:
                return "Step Triangle-4";
            case 0xC:
                return "Step Triangle-6";
            case 0xD:
                return "Step Saw-4";
            case 0xE:
                return "Step Saw-6";
            case 0xF:
                return "Random1 (S/H)";
            case 0x10:
                return "Random2 (S/H)";
            case 0x11:
                return "Random3 (S/H)";
            case 0x12:
                return "Random4 (S/H)";
            case 0x13:
                return "Random5 (S/H)";
            case 0x14:
                return "Random6 (S/H)";
        }
        return "";
    }
    
    public int pack (byte data[], int offset) {
        data[offset] = (byte)(_waveform & 0x1F);  // bits 0-4
        if (_keySync) {
            data[offset] |= 0x80;  // bit 7
        }
        offset++;
        
        data[offset++] = (byte)_frequency;
        data[offset++] = (byte)_offset;
        data[offset++] = (byte)_delay;
        data[offset++] = (byte)_fade;
        
        data[offset]  = (byte)((_midiTempoSync & 0x01) << 7);  // bit 7
        data[offset] |= (byte)((_syncBaseNote  & 0x07) << 4);  // bits 4-6
        data[offset] |= (byte) (_times         & 0x0F);        // bits 0-3
        offset++;
        
        data[offset++] = (byte)_amSourceTime1;
        data[offset++] = (byte)_intByAmTime1;
        data[offset++] = (byte)_amSourceTime2;
        data[offset++] = (byte)_intByAmTime2;
        
        return offset;
    }
    
    public String toString () {
        String retString = "";
        
        retString += "Waveform:           " + getWaveformString() + "\n";
        retString += "KeySync:            ";
        
        if (_keySync) {
            retString += "True" + "\n";
        }
        else {
            retString += "False" + "\n";
        }
        
        retString += "Freq:               " + _frequency + "\n";
        retString += "Offset:             " + _offset + "\n";
        retString += "Delay:              " + _delay + "\n";
        retString += "Fade:               " + _fade + "\n";
        retString += "MidiTempoSync:      " + _midiTempoSync + "\n";
        retString += "SyncBaseNote:       " + _syncBaseNote + "\n";
        retString += "Times:              " + _times + "\n";
        retString += "AM Source (Time 1): " + _amSourceTime1 + "\n";
        retString += "Int By AM (Time 1): " + _intByAmTime1 + "\n";
        retString += "AM Source (Time 2): " + _amSourceTime2 + "\n";
        retString += "Int By AM (Time 2): " + _intByAmTime2 + "\n";
        
        return retString;
    }
    
    private short _waveform;     // byte 0, bits 0-4
    private boolean _keySync;    // byte 0, bit 7
    private int _frequency;      // byte 1
    private int _offset;         // byte 2
    private int _delay;          // byte 3
    private int _fade;           // byte 4
    private byte _midiTempoSync; // byte 5, bit 7
    private byte _syncBaseNote;  // byte 5, bits 6-4
    private byte _times;         // byte 5, bits 0-3
    private int _amSourceTime1;  // byte 6
    private int _intByAmTime1;   // byte 7
    private int _amSourceTime2;  // byte 8
    private int _intByAmTime2;   // byte 8
    
    public static int BYTE_SIZE = 10;
}