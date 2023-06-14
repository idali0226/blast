/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.blast.db.creator;
 
import org.junit.After; 
import org.junit.Before; 
import org.junit.Test; 
import org.junit.runner.RunWith; 
import org.mockito.Mockito; 
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author idali
 */
@RunWith(MockitoJUnitRunner.class)
public class FastaFileCreatorTest {
      
    public FastaFileCreatorTest() {
    }
     
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createFastaFile method, of class FastaFileCreator.
     * @throws java.lang.Exception
     */
    @Test
    public void testCreateFastaFile() throws Exception {
        System.out.println("createFastaFile");
        String sequence = "jjjjjjj";
        String fastafile = "/path/to/file";
          
        FastaFileCreator testInstance = Mockito.mock(FastaFileCreator.class); 
        testInstance.createFastaFile(sequence, fastafile);
         
        Mockito.verify(testInstance).createFastaFile(sequence, fastafile); 
    } 
}
