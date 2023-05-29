import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sonido {
	private Clip clip;
	private float volume;
	
	public Sonido() {
		iniciarMusica();
	}
	
	private void iniciarMusica() {
        try {
            // Crear objeto Clip para reproducir el audio
            clip = AudioSystem.getClip();

            clip.open(AudioSystem.getAudioInputStream(new File("music/MainSong.wav").getAbsoluteFile()));

            //ajustar volumen audio
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume = 0.06f; // volumen
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
            
            // Reproducir el audio
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException l) {
            l.printStackTrace();
        }
    }

	public Clip getClip() {
		return clip;
	}
    
}
