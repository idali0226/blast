/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.blast.db.creator;
 
import java.util.ArrayList; 
import org.junit.After; 
import org.junit.Before; 
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;   
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when; 
import org.mockito.runners.MockitoJUnitRunner;
import se.nrm.dina.data.jpa.DinaDao;

/**
 *
 * @author idali
 */
@RunWith(MockitoJUnitRunner.class)
public class DNASessionBeanImplTest {
    
    @Mock
    private DinaDao mockDao;
       
    private DNASessionBean instance;
    
    public DNASessionBeanImplTest() {
    }
    
 
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getDNASequenceList method, of class DNASessionBeanImpl.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetDNASequenceList() throws Exception {
        System.out.println("getDNASequenceList");
        
        instance = new DNASessionBeanImpl(mockDao); 
         
        when(mockDao.getSearchResultsByJPQL(anyString())).thenReturn(new ArrayList<>());
         
        String result = instance.getDNASequenceList(); 
        assertNotNull(result);
        
        verify(mockDao).getSearchResultsByJPQL(anyString()); 
    } 
}
