/**
 * Abstract class for handling the midi IO.  This sets up the input/output stuff.
 */

package controller.utils;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

public abstract class MidiIO implements Receiver {
	public void initMidiOut(int index) {
		if (_outputDevice != null) {
			_outputDevice.close();
			_outputDevice = null;
		}

		MidiDevice.Info info = MidiUtils.getMidiDeviceInfo(index);
		System.out.println("Setting up output " + info);
		if (info != null) {
			try {
				_outputDevice = MidiSystem.getMidiDevice(info);
				_outputDevice.open();
			} catch (MidiUnavailableException e) {
				System.out.println(e);
			}
			if (_outputDevice == null) {
				System.out.println("wasn't able to retrieve MidiDevice");
				return;
			}
			try {
				_outPortReceiver = _outputDevice.getReceiver();

			} catch (MidiUnavailableException e) {
				System.out.println("initMidiOut wasn't able to connect the device's Receiver to the Transmitter:");
				System.out.println(e);
				_outputDevice.close();
				_outputDevice = null;
				_outPortReceiver = null;
			}
		}
	}

	public void initMidiIn(int index) {
		assert (_inReceiver != null);

		if (_inputDevice != null) {
			_inputDevice.close();
			_inputDevice = null;
		}

		MidiDevice.Info info = MidiUtils.getMidiDeviceInfo(index);
		if (info != null) {

			System.out.println("Setting up input " + info);
			try {
				_inputDevice = MidiSystem.getMidiDevice(info);
				_inputDevice.open();
			} catch (MidiUnavailableException e) {
				System.out.println(e);
			}
			if (_inputDevice == null) {
				System.out.println("wasn't able to retrieve MidiDevice");
				// shutdown();
			}
			try {
				_inTransmitter = _inputDevice.getTransmitter();
				_inTransmitter.setReceiver(_inReceiver);
			} catch (MidiUnavailableException e) {
				System.out.println("initMidiIn wasn't able to connect the device's Transmitter to the Receiver:");
				System.out.println(e);
				_inputDevice.close();
				_inputDevice = null;
			}
		}
	}

	public void close() {
		System.out.println("Closing MIDI connections");
		if (_inputDevice != null) {
			_inputDevice.close();
		}
		_inputDevice = null;

		if (_outputDevice != null) {
			_outputDevice.close();
		}
		_outputDevice = null;
	}

	/**
	 * Send a message to the keyboard
	 * 
	 * @param msg
	 */
	public void sendMsg(MidiMessage msg) {
		if (msg == null) {
			System.out.println("Can't send null msg in sendMsg");
			return;
		}
		if (_outPortReceiver == null) {
			System.out.println("Can't send msg in sendMgs: _outPortReceiver is null");
			return;
		}

		 //System.out.println ("Sending:");
		 //for (int i = 0; i < msg.getLength(); ++i) {
		 //System.out.print (String.format("%02X ", msg.getMessage()[i]));
		 //}
		 //System.out.println();

		_outPortReceiver.send(msg, -1);
	}

	/**
	 * The message from the keyboard to the computer. Not sure why they call
	 * this send since it's coming in.
	 */
	abstract public void send(MidiMessage arg0, long arg1);

	// MIDI in stuff
	private MidiDevice _inputDevice = null;
	protected Receiver _inReceiver = null;
	private Transmitter _inTransmitter = null;

	// MIDI out stuff
	private MidiDevice _outputDevice = null;
	private Receiver _outPortReceiver = null;
}
