package se.nrm.blast.db.creator;
    
import java.util.List; 
import javax.ejb.EJB; 
import javax.ejb.Stateless;  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import se.nrm.dina.data.jpa.DinaDao; 

/**
 *
 * An EJB to fetch dna data from database and constructe dna sequence into a string with fasta files format
 * 
 * @author idali
 */
@Stateless 
public class DNASessionBeanImpl implements DNASessionBean {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
      
    @EJB
    private DinaDao dao;

    public DNASessionBeanImpl() {
    }

    public DNASessionBeanImpl(DinaDao dao) {
        this.dao = dao;
    }
    
    /**
     * This method retrieves dna sequences from database, with dna sequences to 
     * create a NRM.fa file for creating blast database. 
     * 
     * @return String contains dna sequences
     */
    @Override
    public String getDNASequenceList() {
        
        logger.info("getDNASequenceList");
            
        List<Object[]> list = dao.getSearchResultsByJPQL(buildJPQL()); 
        StringBuilder sb = new StringBuilder();
        list.stream()
                .forEach(objs -> {
                    sb.append(">gi||gb|");
                    if(isNotNull(objs[1])) {
                        sb.append(objs[1]);
                    }
                    sb.append("|bold|");
                    if (isNotNull(objs[2])) {
                        sb.append(objs[2]);
                        if (isNotNull(objs[3])) {
                            sb.append(".");
                            sb.append(objs[3]);                              
                        }
                    }
                    sb.append("|gene|");
                    if (isNotNull(objs[3])) { 
                        sb.append(objs[3]);                              
                    }
                    
                    sb.append("|latlon|");
                    sb.append(latLnt(objs));

                    sb.append("|catnr|");
                    if (isNotNull(objs[4])) { 
                        sb.append(objs[4]);                              
                    }
                    sb.append(" ");
                    if (isNotNull(objs[7])) { 
                        sb.append(objs[7]);                              
                    }
                    sb.append("\n");
                    sb.append(objs[0].toString().replaceAll("[0-9 \t\n\r]", ""));
                    sb.append("\n"); 
                }); 
        return sb.toString().trim(); 
    }
    
    private  boolean isNotNull(Object object) {
        return object != null;
    }
    
    private String buildJPQL() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT dna.geneSequence, dna.genbankAccessionNumber, dna.boldBarcodeId, dna.targetMarker, ");
        sb.append("co.catalogNumber, ");
        sb.append("loc.lat1text, loc.long1text, ");
        sb.append("tx.fullName ");  
        sb.append("FROM Dnasequence dna ");
        sb.append("JOIN dna.collectionObject co "); 
        sb.append("JOIN co.determinations det ");
        sb.append("JOIN det.preferredTaxon tx ");
        sb.append("JOIN co.collectingEvent ce ");
        sb.append("JOIN ce.locality loc ");
        sb.append("WHERE dna.collectionObject = co ");
        sb.append("AND det.collectionObject = co ");
        sb.append("AND det.preferredTaxon = tx ");
        sb.append("AND co.collectingEvent = ce ");
        sb.append("AND ce.locality = loc ");
        sb.append("AND det.isCurrent = true "); 
        return sb.toString();
    }
    
    private String latLnt(Object[] objs) { 
        String latlnt = objs[5] + " " + objs[6];
        latlnt = latlnt.replace(".", " "); 
        latlnt = latlnt.replace("\u00b0", "");

        latlnt = latlnt.replaceAll("(\\d+) (\\d+)", "$1\\.$2");  
        return latlnt.replace(" ", "_");
    } 
}
