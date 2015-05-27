package blockoid.audio;
import sun.audio.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Audio {
	private static final AudioPlayer PLAYER = AudioPlayer.player;
	private static Map<String, AudioData> dataCache = new HashMap<String, AudioData>();
	
	private String file;
	private AudioData data;
	private AudioDataStream stream;
	
	public Audio(String file) {
		this(file, true);
	}
	
	public Audio(String file, boolean cache) {
		this.file = file;
		data = dataCache.get(file);
		if (data == null) {
			try {
				AudioStream stream = new AudioStream(new FileInputStream(file));
				data = stream.getData();
				stream.close();
			
				if (cache)
					dataCache.put(file, data);
			} catch (Exception e) {
				System.out.println("Cannot open audio file: " + file);
			}
		}
	}
	
	public void play(boolean loop) {
		stream = loop ? new ContinuousAudioDataStream(data) : new AudioDataStream(data);
		PLAYER.start(stream);
	}
	
	public void stop() {
		if (stream != null)
			PLAYER.stop(stream);
	}
	
	public void setVolume(int volume) {
		// TODO
	}
}
