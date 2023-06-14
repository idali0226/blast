package se.nrm.blast.db.creator;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException; 
import javax.annotation.PostConstruct; 
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Startup;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;  
import se.nrm.blast.db.creator.util.HelpClass;

/**
 * A Singleton EJB is used to pre-fetch data from database and create blast database 
 * at recurring intervals 
 * 
 * @author idali
 */
//@Startup    // initialize ejb at deployment time
@Singleton
@LocalBean
public class SingletonBlastDBCreator {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
     
    private static boolean isLocal;
        
    @Inject
    private FastaFileCreator creator;
    
    @Inject
    private DNASessionBean bean;
     
    public SingletonBlastDBCreator() {
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getLocalHost();
             
            isLocal = HelpClass.isLocal(inetAddress.getHostName());  
        } catch (UnknownHostException ex) {
            logger.error(ex.getMessage());
        }  
    }

    public SingletonBlastDBCreator(DNASessionBeanImpl bean, FastaFileCreator creator) { 
        this.bean = bean;
        this.creator = creator;
    }

    /**
     * Start ejb every time deploy into glassfish or glassfish start
     */
//    @PostConstruct
    void init() {
//        fastaFileDirectory = HelpClass.getFastaFileDirectory(); 
        createBlastDBOnStartup();
//        createNRMFastaFile(); 
    }
    
    
    
    
    /**
     * This method scheduled to run every Sat. at 00:00 to call createFastaFile() method to create 
     * a NRM.fa file and saves it into file system on the server
     */
    @Schedule(dayOfWeek = "Sat", hour = "0")    
    public void createNRMFastaFile() {

        logger.info("createNRMFastaFile");
 
        createFastaFile(); 
    }
    
     
    /**
     * This method scheduled to run every Mon. at 00:00 to create blast database
     */
//    @Schedule(dayOfWeek = "*", hour = "0")   // Scheduled everyday at midnight
//    @Schedule(hour="*", minute="*", second="*/30")                // Scheduled every 30 seconds
//    @Schedule(minute = "*", hour = "*", persistent = false)         // Scheduled every minute
    @Schedule(dayOfWeek = "Mon", hour = "0")
    public void createBlastDB() {

        logger.info("createBlastDB");

        createBOLDBlastDb();
        createGenbanBlastDb();
        createNrmBlastDb();
    }
    
    /**
     * This method called in init() method, when application deployed into 
     * glassfish or glassfish restart.
     */
    private void createBlastDBOnStartup() {
        
        createFastaFile();               // first create nrm fasta file with sequences in dina_nrm
        
        createBOLDBlastDb();
        createGenbanBlastDb();
        createNrmBlastDb();
    }
    
    /**
     * This method create a NRM.fa file with dina-nrm sequences
     */
    private void createFastaFile() {
        logger.info("createNRMFastaFile");
        
        String sequence = bean.getDNASequenceList();
        creator.createFastaFile(sequence, HelpClass.getFastaFilePath("nrm", isLocal)); 
    }
    
    private void createNrmBlastDb() { 
        logger.info("createNrmBlastDb"); 
         
        create(HelpClass.getNrmDbName());
    }
    
    private void createBOLDBlastDb() {
        
        logger.info("createBOLDBlastDb");
         
        create(HelpClass.getBoldDbName());
    }
    
    private void createGenbanBlastDb() {  
        create(HelpClass.getGenbankDbName());
    }
 
    private void create(String dbName) {

        logger.info("create:  {}",  dbName);
 
        try {
            String command = HelpClass.buildCreateDbCommand(isLocal, dbName);
            Runtime.getRuntime().exec(command);                   // create db 
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }  
}             
