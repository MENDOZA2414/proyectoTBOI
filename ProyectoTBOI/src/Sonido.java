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

    public Sonido(String ruta) {
        iniciarMusica(ruta);
    }

    private void iniciarMusica(String ruta) {
        try {
            // Crear objeto Clip para reproducir el audio
            clip = AudioSystem.getClip();

            clip.open(AudioSystem.getAudioInputStream(new File("music/" + ruta + ".wav").getAbsoluteFile()));

            // ajustar volumen audio
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume = 0.2f; // volumen
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);

            // Reproducir el audio
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException l) {
            l.printStackTrace();
        }
    }

    public void cambiarRuta(String nuevaRuta) {
        clip.stop(); // Detener la reproducción actual

        // Cerrar el clip y cargar el nuevo archivo de sonido
        clip.close();
        iniciarMusica(nuevaRuta);
    }

    public Clip getClip() {
        return clip;
    }
}
