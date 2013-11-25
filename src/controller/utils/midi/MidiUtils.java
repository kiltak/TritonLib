package controller.utils.midi;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.SysexMessage;

/** Utility methods for MIDI examples.
*/
public class MidiUtils
{
	/**
	 * 
	 * @return
	 */
    public static String[] getOutputDeviceNames () {
    	String retVal[] = new String[10];
    	
    	for (int i = 0; i < 10; ++i) {
    		retVal[i] = null;
    	}

        MidiDevice.Info[] devInfo = MidiSystem.getMidiDeviceInfo();
        if (devInfo.length == 0) {
        	
        }
        else {
        	MidiDevice dev;
        	for (int i = 0; (i < devInfo.length) && (i < 10); ++i) {
                try {
					dev = MidiSystem.getMidiDevice(devInfo[i]);
				}
                catch (MidiUnavailableException e) {
					e.printStackTrace();
					return null;
				}
        		
                if (dev.getMaxReceivers() != 0) {
                	retVal[i] = devInfo[i].getName();
        		}
        	}
        }
    	
    	return retVal;
    }
    
    /**
     * 
     * @return
     */
    public static String[] getInputDeviceNames () {
    	String retVal[] = new String[10];
    	
    	for (int i = 0; i < 10; ++i) {
    		retVal[i] = null;
    	}

        MidiDevice.Info[] devInfo = MidiSystem.getMidiDeviceInfo();
        if (devInfo.length == 0) {
        	
        }
        else {
        	MidiDevice dev;
        	for (int i = 0; (i < devInfo.length) && (i < 10); ++i) {
                try {
					dev = MidiSystem.getMidiDevice(devInfo[i]);
				}
                catch (MidiUnavailableException e) {
					e.printStackTrace();
					return null;
				}
        		
                if (dev.getMaxTransmitters() != 0) {
                	retVal[i] = devInfo[i].getName();
        		}
        	}
        }
    	
    	return retVal;
    }

    /**
     * 
     * @param index
     * @return
     */
    public static MidiDevice.Info getMidiDeviceInfo(int index)
    {
        MidiDevice.Info[] devs = MidiSystem.getMidiDeviceInfo();
        if ((index < 0) || (index >= devs.length)) {
            return null;
        }
        return devs[index];
    }
    
    /**
     * 
     * @param message
     */
    public static void printMsg (SysexMessage message) {
        byte[] data = message.getData();
        printBytes (data, data.length);
    }

    /**
     * Print out the bytes from an array
     * 
     * @param data
     *            The array to print out.
     * @param length
     *            The length of the array.
     */
    public static void printBytes(byte[] data, int length) {
        for (int i = 0; i < length; ++i) {
            System.out.print(String.format("%02X ", data[i]));
        }
        System.out.println();
    }
}

