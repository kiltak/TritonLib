package staged;

import java.io.*;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.SysexMessage;

public class FileReader {
    public FileReader () {
        
    }
    
    public FileReader (String filename) {
        this.open (filename);
    }
    
    /**
     * Open up the reader.
     * 
     * @param filename
     * @throws FileNotFoundException
     */
    public boolean open (String filename) {
        close();
        FileInputStream fstream;
        
        try {
            fstream = new FileInputStream(filename);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        
        _file = new BufferedReader(new InputStreamReader(fstream));
        return true;
    }
    
    /**
     * Close out the reader (after checking to see if it's open.
     */
    public void close () {
        if (_file != null) {
            try {
                _file.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            _file = null;
        }
    }
    
    /**
     * Get the next message.  A line that says 'inside decodeMessage' is what stops this.
     * @return
     */
    public SysexMessage getNextMsg () {
        SysexMessage retMsg = new SysexMessage();
        byte data[] = new byte[10000];

        data[0] = (byte)0xF0;
        
        String s;
        int i = 1;
        int tmp; // Java is stupid with 'signed' bytes
        try {
            while ((s = _file.readLine()) != null) {
                if (s.equals("")) {
                    break;
                }
                if (s.contains("inside decodeMessage")) {
                    _file.readLine(); // get passed the next new line
                    break;
                }
                else if (!s.equalsIgnoreCase("")) {
                    for (String sub : s.split(" ")) {
                        tmp = Integer.parseInt(sub, 16);
                        data[i++] = (byte)tmp;
                    }
                }
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
        // Check to see if no data was added
        if (i == 1) {
            return null;
        }
        
        try {
            retMsg.setMessage(data, i);
        }
        catch (InvalidMidiDataException e) {
            e.printStackTrace();
            return null;
        }
        return retMsg;
    }
    
    /**
     * 
     * @param args
     */
    public static void main(String args[]) {
        FileReader _fr = new FileReader();
        SysexMessage msg;
        
        if (!_fr.open("src/staged/dump/everything")) {
            System.exit(1);
        }

        while ((msg = _fr.getNextMsg()) != null) {
            System.out.println (msg);
        }
        System.out.println ("finished");
        _fr.close();
    }
    
    private BufferedReader _file = null;
}