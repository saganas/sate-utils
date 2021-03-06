package edu.tfai.sate2.utils;

import edu.tfai.sate2.spectra.Spectra;
import edu.tfai.sate2.spectra.SpectraReader;
import org.junit.Test;

import java.nio.file.Path;

import static edu.tfai.sate2.utils.FileNameUtils.getPath;
import static edu.tfai.sate2.utils.NoiseUtil.addNoise;
import static edu.tfai.sate2.utils.NoiseUtil.noiseCheck;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

public class NoiseUtilUTest {
    private final Path file = getPath("o2_sun_synth.xxy");

    private SpectraReader spectraReader = new SpectraReader();

    @Test
    public void testNoiseLevel() throws Exception {
        Spectra spectra = spectraReader.loadSyntheticSpectra(file, "02");
        double noise = noiseCheck(spectra);
        assertThat(noise, closeTo(284, 1));
    }

    @Test
    public void testAddNoise() throws Exception {
        Spectra spectra = spectraReader.loadSyntheticSpectra(file, "02");
        Spectra noisySp = addNoise(spectra.copy(), 0.25);
//        assertNotEquals(spectra.getY(0),noisySp.getY(0),0.1);
        noisySp.normalize();
        double noise = noiseCheck(noisySp);
//        spectraReader.writeSpectra(get("./sp1.txt"), noisySp);
        assertThat(noise, lessThan(284.0));

    }
}