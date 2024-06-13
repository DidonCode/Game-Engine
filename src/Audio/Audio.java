package Audio;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

import Tools.Logs;

public class Audio {

	int buffer;
	int sourceId;
	
	public static Audio loadAudioFile(String path) {
		int buffer = AL10.alGenBuffers();
		
		URL file = null;
		try {
			file = new File(path).toURI().toURL();
		} catch (MalformedURLException e) {
			new Logs("Error on audio path", e, false);
			return null;
		}
		WaveData waveFile = WaveData.create(file);
		AL10.alBufferData(buffer, waveFile.format, waveFile.data, waveFile.samplerate);
		waveFile.dispose();
		
		return new Audio(buffer);
	}
	
	private Audio(int buffer) {
		this.init();
		this.buffer = buffer;
	}
	
	private void init() {
		AL10.alListener3f(AL10.AL_POSITION, 0, 0, 0);
		AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
		
		sourceId = AL10.alGenSources();
		AL10.alSource3f(sourceId, AL10.AL_POSITION, 0, 0, 0);
	}
	
	public void clear() {
		AL10.alSourceStop(sourceId);
	}
	
	public void setLooping(boolean loop) {
		AL10.alSourcei(sourceId, AL10.AL_LOOPING, loop ? AL10.AL_TRUE : AL10.AL_FALSE);
	}
	
	public void play() {
		AL10.alSourcei(sourceId, AL10.AL_BUFFER, buffer);
		continuePlaying();
	}
	
	public void pause() {
		AL10.alSourcePause(sourceId);
	}
	
	public void stop() {
		AL10.alSourceStop(sourceId);
	}
	
	public void setVolume(float volume) {
		AL10.alSourcef(sourceId, AL10.AL_GAIN, volume);
	}
	
	public void setPitch(float pitch) {
		AL10.alSourcef(sourceId, AL10.AL_PITCH, pitch);
	}
	
	public void continuePlaying() {
		AL10.alSourcePlay(sourceId);
	}

	public int getBuffer() {
		return buffer;
	}
	
}
