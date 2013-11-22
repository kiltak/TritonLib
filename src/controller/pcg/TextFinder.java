package controller.pcg;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TextFinder {
    public boolean readFile (String filename) {
        try {
            Path path = Paths.get(filename);
            _data = Files.readAllBytes(path);
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
        // Unpack the data into the sub structures
        for (int i = 0; i < _data.length; ++i) {
            if ((0x20 <= _data[i]) && (_data[i] <= 0x7E)) {
                if (!_writting) {
                    _buffer = "";
                    _stringStart = i;
                }
                _writting = true;
                _buffer += (char)_data[i];
            }
            else if (_writting) {
                _writting = false;
                String strippedString = _buffer.replaceAll("\\s+", "");
                if (strippedString.length() >= 4) {
                    System.out.println (String.format("%5d: ", _stringStart) + _buffer);
                }
            }
        }
        
        return true;
    }
    
    public static void main(String args[]) {
        String filename = "./docs/PCG/EH_SNDZ/EH_SNDZ.pcg";
        filename = "./docs/PCG/TC_LEADS/LEADS.pcg";
//        String filename = ".\\docs\\PCG\\EH_SNDZ\\EH_SNDZ.pcg";
        
        TextFinder tf = new TextFinder();
        tf.readFile(filename);
        
        System.out.println ("Finished reading " + filename);
    }
    
    private byte _data[];
    private boolean _writting = false;
    private String _buffer;
    private int _stringStart;
}