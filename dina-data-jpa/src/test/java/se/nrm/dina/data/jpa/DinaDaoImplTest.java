/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.data.jpa;
 
import java.util.ArrayList; 
import java.util.List;  
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.junit.After; 
import org.junit.Before; 
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;  
import org.mockito.Mock; 
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;  
import org.mockito.runners.MockitoJUnitRunner;  
import se.nrm.specify.datamodel.Accession;
import se.nrm.specify.datamodel.SpecifyBean;

/**
 *
 * @author idali
 */ 
@RunWith(MockitoJUnitRunner.class)
public class DinaDaoImplTest {
    
    @Mock
    EntityManager entityManager;
    
    @Mock
    Query query;
    
    @Mock
    TypedQuery<String> tq;
    
    @Mock
    TypedQuery<Object[]> tqObj;
    
    @Mock
    TypedQuery<Integer> intTQ;
     
    private DinaDao dao;
    private List<Accession> accessions;
    private Accession accession1;
      
    public DinaDaoImplTest() {
        
    } 
    
    @Before
    public void setUp() {
        dao = new DinaDaoImpl(entityManager); 
        preparaTstData();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of findAll method, of class DinaDaoImpl.
     * @throws java.lang.Exception
     */
    @Test
    public void testFindAll() throws Exception {
        System.out.println("findAll");
        
        when(entityManager.createNamedQuery(Accession.class.getSimpleName() + ".findAll")).thenReturn(query);
        when(query.getResultList()).thenReturn(accessions);
        int expResult = 3;  
        
        List result = dao.findAll(Accession.class); 
        assertEquals(expResult, result.size()); 
    }

    /**
     * Test of findById method, of class DinaDaoImpl.
     * @throws java.lang.Exception
     */
    @Test
    public void testFindById() throws Exception {
        System.out.println("findById");
         
        when(entityManager.find(Accession.class, 20, LockModeType.OPTIMISTIC)).thenReturn(accession1);
        
        SpecifyBean result = dao.findById(20, Accession.class);
        assertSame(accession1, result); 
    }

    /**
     * Test of findByReference method, of class DinaDaoImpl.
     * @throws java.lang.Exception
     */
    @Test
    public void testFindByReference() throws Exception {
        System.out.println("findByReference");
   
        Accession expResult = accession1;
        
        when(entityManager.getReference(Accession.class, 20)).thenReturn(accession1);
        
        SpecifyBean acc = dao.findByReference(20, Accession.class); 
        assertEquals(expResult, acc);  
    }

    /**
     * Test of create method, of class DinaDaoImpl.
     * @throws java.lang.Exception
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        
        SpecifyBean bean = new Accession(50);
          
        int expResult = 50;  
        Accession result = (Accession)dao.create(bean);
        assertEquals(expResult, (int)result.getAccessionId());  
    }

    /**
     * Test of merge method, of class DinaDaoImpl.
     * @throws java.lang.Exception
     */
    @Test
    public void testMerge() throws Exception {
        System.out.println("merge");
        
        accession1.setAccessionNumber("acc00060");   
        String expResult = "acc00060";  
        
        when(entityManager.merge(accession1)).thenReturn(accession1);
        Accession result = (Accession)dao.merge(accession1);
        assertEquals(expResult, result.getAccessionNumber()); 
    }

    /**
     * Test of updateByJPQL method, of class DinaDaoImpl.
     * @throws java.lang.Exception
     */
    @Test
    public void testUpdateByJPQL() throws Exception {
        System.out.println("updateByJPQL");
         
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE Accession a SET a.assectionNumber = 'acc1235'");   
        sb.append("' WHERE a.accessionId = 20"); 
        
        when(entityManager.createQuery(sb.toString())).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);
          
        boolean result = dao.updateByJPQL(sb.toString()); 
        assertTrue(result); 
    }

    /**
     * Test of getCountByJPQL method, of class DinaDaoImpl.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetCountByJPQL() throws Exception {
        System.out.println("getCountByJPQL");
        
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT COUNT(c) ");
        sb.append("FROM Accession a "); 
        sb.append("WHERE a.accessionId > ");
        sb.append(1);  
        
        when(entityManager.createQuery(sb.toString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(5);
         
        int expResult = 5;
        int result = dao.getCountByJPQL(null, sb.toString()); 
        assertEquals(expResult, result); 
    }

    /**
     * Test of delete method, of class DinaDaoImpl.
     * @throws java.lang.Exception
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        
        dao.delete(accession1);
        
        verify(entityManager).remove(accession1); 
    }

    /**
     * Test of getLastCatalogunumber method, of class DinaDaoImpl.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetLastCatalogunumber() throws Exception {
        System.out.println("getLastCatalogunumber");

       
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT c.catalogNumber FROM Collectionobject AS c where c.collection.userGroupScopeId = ");
        sb.append(12345);
        sb.append(" ORDER BY c.collectionObjectId desc");
        
        when(entityManager.createQuery(sb.toString(), String.class)).thenReturn(tq);
        tq.setMaxResults(1); 
        when(tq.getSingleResult()).thenReturn("cat12345");
        
        String expResult = "cat12345";
        String result = dao.getLastCatalogunumber(sb.toString());
        assertEquals(expResult, result); 
    }

    /**
     * Test of getSearchResultsByJPQL method, of class DinaDaoImpl.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetSearchResultsByJPQL() throws Exception {
        System.out.println("getSearchResultsByJPQL");
        
        String jpql = "SELECT a.agentId, a.firstName, a.lastName, a.remarks FROM Agent AS a WHERE a.agentType = 150 "; 
        
        when(entityManager.createQuery(jpql, Object[].class)).thenReturn(tqObj);
        when(tqObj.getResultList()).thenReturn(new ArrayList<>());
          
        List result = dao.getSearchResultsByJPQL(jpql);
        assertNotNull(result); 
        assertEquals(0, result.size());
    }

    /**
     * Test of getStringListByJPQL method, of class DinaDaoImpl.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetStringListByJPQL() throws Exception {
        System.out.println("getStringListByJPQL");
        
        StringBuilder jpqlSB = new StringBuilder();
        jpqlSB.append("SELECT t.fullName ");
        jpqlSB.append("FROM Taxon AS t where t.fullName like '%test'"); 
        jpqlSB.append("%' AND t.definition.taxonTreeDefId = 11");  
        
        when(entityManager.createQuery(jpqlSB.toString(), String.class)).thenReturn(tq);
        when(tq.getResultList()).thenReturn(new ArrayList());
        
        List<String> result = dao.getStringListByJPQL(jpqlSB.toString()); 
        assertNotNull(result); 
        assertEquals(0, result.size());
    }

 

    /**
     * Test of getSingleValueByJPQL method, of class DinaDaoImpl.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetSingleValueByJPQL() throws Exception {
        System.out.println("getSingleValueByJPQL");

        String jpql = "SELECT a.accessionNumber FROM Accession a where a.accessionId = 1"; 
        when(entityManager.createQuery(jpql, String.class)).thenReturn(tq);
        when(tq.getSingleResult()).thenReturn("test"); 
        String expResult = "test";
        String result = dao.getSingleValueByJPQL(jpql);
        assertEquals(expResult, result); 
    }

    /**
     * Test of getSingleIdByJPQL method, of class DinaDaoImpl.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetSingleIdByJPQL() throws Exception {
        System.out.println("getSingleIdByJPQL");
        
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t.taxonId ");
        sb.append("FROM Taxon t "); 
        sb.append("WHERE t.fullName = 'test'"); 
        
        when(entityManager.createQuery(sb.toString(), Integer.class)).thenReturn(intTQ);  
        intTQ.setMaxResults(1);
        when(intTQ.getSingleResult()).thenReturn(20);
        
        int result = dao.getSingleIdByJPQL(sb.toString()); 
         
        int expResult = 20; 
        assertEquals(expResult, result); 
    }

    /**
     * Test of getListOfDataByJPQL method, of class DinaDaoImpl.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetListOfDataByJPQL() throws Exception {
        System.out.println("getListOfDataByJPQL");
          
        Object[] objs = new Object[3];
        objs[0] = 12;
        objs[1] = "test";
        objs[2] = "guid";
        
        String jpql = "SELECT t.taxonId, t.fullName, t.guid FROM Taxon AS t WHERE t.number1 = 12345"; 
        when(entityManager.createQuery(jpql, Object[].class)).thenReturn(tqObj);
        when(tqObj.getSingleResult()).thenReturn(objs);
         
        Object[] expResult = objs;
        Object[] result = dao.getListOfDataByJPQL(jpql);
        assertArrayEquals(expResult, result); 
    }
 

    /**
     * Test of getAllEntitiesByJPQL method, of class DinaDaoImpl.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetAllEntitiesByJPQL() throws Exception {
        System.out.println("getAllEntitiesByJPQL");
         
        String jpql = "SELECT a FROM Accession";
        
        when(entityManager.createQuery(jpql)).thenReturn(query);
        when(query.getResultList()).thenReturn(accessions);
         
        List expResult = accessions;
        List result = dao.getAllEntitiesByJPQL(jpql);
        assertEquals(expResult, result);  
    }

    /**
     * Test of getEntityByJPQL method, of class DinaDaoImpl.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetEntityByJPQL() throws Exception {
        System.out.println("getEntityByJPQL");
        String jpql = "SELECT a FROM Accession WHERE a.accessionId = 2"; 
        
        when(entityManager.createQuery(jpql)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(accession1);
         
        Object expResult = accession1;
        SpecifyBean result = dao.getEntityByJPQL(jpql);
        assertEquals(expResult, result); 
    }
  
    private void preparaTstData() {
        accessions = new ArrayList();
        Accession accession = new Accession(1);
        accessions.add(accession);
        accession = new Accession(2);
        accessions.add(accession);
        accession = new Accession(3);
        accessions.add(accession);
        
        accession1 = new Accession(20);
        accession1.setAccessionNumber("acc00020");
    }

}
