package se.nrm.blast.db.creator;

//import java.io.File;
//import java.io.FileNotFoundException; 
import java.io.IOException;
//import java.io.PrintWriter;  
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

/**
 * FastaFileCreator creates a .fasta file
 * 
 * @author idali
 */
@Stateless
public class FastaFileCreator {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
      
    public FastaFileCreator() {   
    } 
     
    /**
     * Create a fasta file
     * @param sequence
     * @param fastafile 
     */
    public void createFastaFile(String sequence, String fastafile) {
        logger.info("createFastaFile - sequence");
     
        try { 
            Files.write(Paths.get(fastafile), sequence.getBytes());  
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        } 
        
                    
//            File fastaFile = new File(fastafile);
//            try (PrintWriter out = new PrintWriter(fastaFile)) {
//                out.println(sequence);
//                out.flush();
//            } catch (FileNotFoundException ex) {
//                logger.error(ex.getMessage());
//            }
    } 
}
