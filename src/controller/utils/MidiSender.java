package controller.utils;

import java.util.ArrayDeque;
import java.util.Queue;

import javax.sound.midi.MidiMessage;

public class MidiSender implements Runnable {
	public MidiSender(TritonMidiIO m) {
		midiIO = m;
	}

	@Override
	public void run() {
		MidiMessage msg;
		while (true) {
			try {
				if (runFlag && !msgToSend.isEmpty()) {
					msg = msgToSend.remove();
					midiIO.reallySendMsg(msg);
					Thread.sleep(1000); // Give the hardware a little breathing
										// room
				}
				Thread.sleep(5000);
			}
			catch (InterruptedException e) {
				// Sometimes it's good to wake up and get to work!
			}
		}
	}

	public void addMsg(MidiMessage msg) {
		msgToSend.add(msg);
	}

	public void shouldRun(boolean runFlag) {
		this.runFlag = runFlag;
	}

	boolean runFlag = true;
	Queue<MidiMessage> msgToSend = new ArrayDeque<MidiMessage>();
	TritonMidiIO midiIO;
}
