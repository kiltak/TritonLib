package controller.utils;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.SysexMessage;

import model.Triton;

public class TritonMidiIO extends MidiIO {
	
	private MidiParser _midiParser = new MidiParser();
	
	public TritonMidiIO (Triton triton) {
		super._inReceiver = this;
		_triton = triton;
		midiSender = new MidiSender(this);
		Thread t = new Thread(midiSender);
		t.start();
	}

	/**
	 * This is really reading from the MIDI device.  The message is getting sent to the computer.
	 */
	@Override
	public void send(MidiMessage message, long lTimeStamp) {
		if (message instanceof SysexMessage) {
//			midiSender.shouldRun(_midiParser.decodeMessage((SysexMessage) message, _triton));
			if (_midiParser.decodeMessage((SysexMessage) message, _triton)) {
				midiSender.shouldRun(true); // the message is to be continued...
			}
			else {
				midiSender.shouldRun(false); // the message is finished
			}
		}
	}
	
	public void sendMsg(MidiMessage msg) {
		midiSender.addMsg(msg);
	}
	
	public void reallySendMsg (MidiMessage msg) {
		super.sendMsg(msg);
	}
	
	// TODO: Take Triton out of TritonMidiIO.  Should return objects to add.
	Triton _triton;
	MidiSender midiSender;
}
