package model.sound.program.oscillator;

public class Oscillator {
    public Oscillator () {
        int i;
        
        for (i = 0; i < 2; ++i) {
            _lfo[i] = new LFO();
            _filter[i] = new Filter();
        }
    }
    
    public int unpack (byte data[], int offset) {
        offset = _hdr.unpack(data, offset);
        
        for (int i = 0; i < 2; ++i) {
            offset = _lfo[i].unpack(data, offset);
        }
        
        offset = _pitch.unpack(data, offset);
        offset = _filterhdr.unpack(data, offset);
        
        for (int i = 0; i < 2; ++i) {
            offset = _filter[i].unpack(data, offset);
        }
        
        offset = _filterEg.unpack(data, offset);
        offset = _filterKeyboardTrack.unpack(data, offset);
        offset = _amplifier.unpack(data, offset);
        offset = _amplifierEg.unpack(data, offset);
        offset = _amplifierKeyboardTrack.unpack(data, offset);
        offset = _output.unpack(data, offset);
        
        return offset;
    }
    
    public int pack (byte data[], int offset) {
        offset = _hdr.pack(data, offset);
        
        for (int i = 0; i < 2; ++i) {
            offset = _lfo[i].pack(data, offset);
        }
        
        offset = _pitch.pack(data, offset);
        offset = _filterhdr.pack(data, offset);
        
        for (int i = 0; i < 2; ++i) {
            offset = _filter[i].pack(data, offset);
        }

        offset = _filterEg.pack(data, offset);
        offset = _filterKeyboardTrack.pack(data, offset);
        offset = _amplifier.pack(data, offset);
        offset = _amplifierEg.pack(data, offset);
        offset = _amplifierKeyboardTrack.pack(data, offset);
        offset = _output.pack(data, offset);
        
        return offset;
    }
    
    private Header _hdr = new Header();
    private LFO _lfo[] = new LFO[2];
    private Pitch _pitch = new Pitch();
    private FilterHdr _filterhdr = new FilterHdr();
    private Filter _filter[] = new Filter[2];
    private FilterEg _filterEg = new FilterEg();
    private KeyboardTrack _filterKeyboardTrack = new KeyboardTrack();
    private Amplifier _amplifier = new Amplifier();
    private AmplifierEg _amplifierEg = new AmplifierEg();
    private KeyboardTrack _amplifierKeyboardTrack = new KeyboardTrack();
    private Output _output = new Output();
}