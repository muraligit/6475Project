package gatech.cs6300.project1;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AverageSentenceLengthTest {

    private AverageSentenceLength asl;
    private String fileDir;

    @Before
    public void setUp() throws Exception {
        asl = new AverageSentenceLength();
        fileDir = new String("test" + File.separator + "inputfiles"
                + File.separator);
    }

    @After
    public void tearDown() throws Exception {
        asl = null;
        fileDir = null;
    }

    @Test
    public void testComputeAverageSentenceLength1() {
        asl.setFile(new File(fileDir + "multi.txt"));
        assertEquals(6, asl.computeAverageSentenceLength(), 0);
    }

    @Test
    public void testComputeAverageSentenceLength2() {
        asl.setFile(new File(fileDir + "file.txt"));
        asl.setSentenceDelimiters("|%");
        assertEquals(1, asl.computeAverageSentenceLength(), 0);
    }

    @Test
    public void testComputeAverageSentenceLength3() {
        asl.setFile(new File(fileDir + "essay.txt"));
        assertEquals(10, asl.computeAverageSentenceLength(), 0);
    }

    @Test
    public void testComputeAverageSentenceLength4() {
        asl.setFile(new File(fileDir + "essay.txt"));
        asl.setMinWordLength(5);
        assertEquals(4, asl.computeAverageSentenceLength(), 0);
    }
    
    @Test
    public void testComputeAverageSentenceLength5() {
        asl.setFile(new File(fileDir + "numbers.txt"));
        asl.setSentenceDelimiters("/|");
        asl.setMinWordLength(1);
        assertEquals(5, asl.computeAverageSentenceLength(), 0);
    }

}
