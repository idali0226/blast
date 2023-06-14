package se.nrm.blast.db.creator.util;

/**
 *
 * @author idali
 */
public class HelpClass {
    
    private static final String NRM_DB_NAME = "nrm";                                        // blast database name (nrm)
    private static final String BOLD_DB_NAME = "bold";                                      // blast database name (bold)
    private static final String GENBANK_DB_NAME = "genbank";                                // blast database name (genbank)
  
    private static final String GENBANK_FASTA_FILE = "LATEST.fas";
    private static final String GENBANK_SWE_FASTA_FILE = "LATEST.SWE.fas";
    private static final String NRM_FASTA_FILE = "NRM.fas";
     
    private static final String FASTA_FILE_LOCAL_DIRECTORY ="/usr/local/blast/data/"; 
//    private static final String FASTA_FILE_REMOTE_DIRECTORY = "/home/dnakey/data/"; 
    private static final String FASTA_FILE_REMOTE_DIRECTORY = "/home/admin/wildfly-8.1.0-0/dnakey/data/"; 
       
    private static final String BLAST_BASE_PATH_LOCAL = "/usr/local/blast/";
//    private static final String BLAST_BASE_PATH_REMOTE = "/usr/local/";
    private static final String BLAST_BASE_PATH_REMOTE = "/home/admin/wildfly-8.1.0-0/dnakey/";
      
    private static final String MAKE_BLAST_DB = "bin/makeblastdb";
    private static final String BLAST_LOCAL_DB = "/usr/local/blast/db/";
//    private static final String BLAST_REMOTE_DB = "/home/dnakey/db/";
    private static final String BLAST_REMOTE_DB = "/home/admin/wildfly-8.1.0-0/dnakey/db/";
    
    public static String getNrmDbName() {
        return NRM_DB_NAME;
    }

    public static String getBoldDbName() {
        return BOLD_DB_NAME;
    }

    public static String getGenbankDbName() {
        return GENBANK_DB_NAME;
    }

    public static String getFastaFile(String fasta) {
        return fasta.equals("nrm") ? NRM_FASTA_FILE : fasta.equals("bold") ? 
                                            GENBANK_SWE_FASTA_FILE : GENBANK_FASTA_FILE;
    }
  
    public static String getFastaFileDirectory(boolean isLocal) {
        return isLocal ? FASTA_FILE_LOCAL_DIRECTORY : FASTA_FILE_REMOTE_DIRECTORY;
    } 
     
    public static String getFastaFilePath(String fasta, boolean isLocal) {
        return getFastaFileDirectory(isLocal) + getFastaFile(fasta);
    }
     
    public static String getBlastCreateDBPath(boolean isLocal) {
        return isLocal ? BLAST_BASE_PATH_LOCAL + MAKE_BLAST_DB : BLAST_BASE_PATH_REMOTE + MAKE_BLAST_DB;
    } 
     
    public static String getBlastDbPath(boolean isLocal) {
        return isLocal ? BLAST_LOCAL_DB : BLAST_REMOTE_DB;
    }     
    
    public static boolean isLocal(String hostname) {   
        return hostname.toLowerCase().contains("ida") ; 
    }

    public static String buildCreateDbCommand(boolean isLocal, String dbName) {
        StringBuilder sb = new StringBuilder();

        sb.append(getBlastCreateDBPath(isLocal));
        sb.append(" -in ");
        sb.append(getFastaFilePath(dbName, isLocal));
        sb.append(" -out ");
        sb.append(getBlastDbPath(isLocal));
        sb.append(dbName);
        sb.append(" -dbtype nucl");
        
        return sb.toString().trim();
    }
}
