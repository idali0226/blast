/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.nrm.dina.data.jpa;

import java.util.List; 
import java.util.Map;
import se.nrm.dina.data.jpa.vo.CommonVO;
import se.nrm.specify.datamodel.SpecifyBean; 

/**
 *
 * @author idali
 * @param <T>
 */
public interface DinaDao<T extends SpecifyBean> {
     
    /**
     * Saves a transient or persistent {@link BaseEntity} to the database. 
     * 
     * @param entity the entity to save.
     * @return a persistent copy of the entity.
     */
    public T create(T entity);
    
    
    /**
     * Merges a transient {@link BaseEntity} to the database. 
     * 
     * @param entity the entity to merge.
     * @return a persistent copy of the entity.
     */
    public T merge(T entity);
    
    /**
     * A generic method to update an entity by query.
     * @param jpql 
     * @return  
     */
    public boolean updateByJPQL(String jpql );
    
    /**
     * Deletes a {@link BaseEntity} from the database. If the delete
     * was successful, the entity's ID will be null.
     * 
     * @param entity the entity to delete. 
     */
    public void delete(T entity);
    
    /**
     * Finds a {@link BaseEntity} by its database ID.
     * @param id the database id of the entity we want.
     * @param clazz
     * 
     * @return the instance of the entity from the database with the given id.
     */
    public T findById(int id, Class<T> clazz);
    
    /**
     * Find an instance whose state may be lazily fetched
     * 
     * @param id the database id of the entity we want.
     * @param clazz
     * 
     * @return the instance of the entity from the database with the given id.
     */ 
    public T findByReference(int id, Class<T> clazz);
     
    /**
     * Finds all the instances of an entity in the database.
     * @param clazz
     * @return a <code>List</code> of all the entities in the database.
     */
    public List<T> findAll(Class<T> clazz);
     
    /**
     * Find an instance by given jpql
     * @param jpql
     * @return T
     */
    public T getEntityByJPQL(String jpql);

    
    /**
     * Method to get a total count of an entity in database
     * 
     * @param bean
     * @param jpql
     * @return int
     */
    public int getCountByJPQL(T bean, String jpql);
    
    /**
     * Find an instance by given namedQuery
     * @param namedQuery
     * @param conditions
     * @return T
     */
    public T getEntityByNamedQuery(String namedQuery, Map<String, Object> conditions);
 
    /**
     * Find all the instances of an entity in the database by JPQL
     * @param jpql
     * @return a <code>List</code> of all the instances of an entity in the database.
     */
    public List<T> getAllEntitiesByJPQL(String jpql);
      
    /**
     * Find list of values in a CommonVO object
     * @param jpql
     * @return List<CommonVO>
     */
    public List<CommonVO> getListByJPQL(String jpql);
    
    /**
     * Get last CollectionObject catalognumber (This method can be generic for get a String value defined in jpql)
     * @param jpql
     * @return 
     */
    public String getLastCatalogunumber(String jpql);
    
    /**
     * Get an array of values in an entity by given JPQL
     * @param jpql
     * @return Object[]
     */
    public Object[] getListOfDataByJPQL(String jpql);
    
    /**
     * Get a single value of an entity by jpql. 
     * @param jpql
     * @return 
     */
    public String getSingleValueByJPQL(String jpql);
    
    /**
     * Get an int value by JPQL
     * @param jpql
     * @return int
     */
    public int getSingleIdByJPQL(String jpql);
    
    /**
     * Find a list of array values from entities by jpql
     * @param jpql
     * @return 
     */
    public List<Object[]> getSearchResultsByJPQL(String jpql);
    
    /**
     * Find a list of String values by jpql
     * @param jpql
     * @return 
     */
    public List<String> getStringListByJPQL(String jpql);
    
    /**
     * Find a list of Integers (usually entity ids) by jpql
     * @param jpql
     * @return List
     */
    public List<Integer> getIntListByJPQL(String jpql);
    
    /**
     * Find a list of instances from an entity by named query
     * @param namedQuery
     * @param parameters
     * @return 
     */
    public List getAllEntitiesByNamedQuery(String namedQuery, Map<String, Object> parameters);
    
    /**
     * Find a list of array values by native query
     * @param query
     * @return List<Object[]>
     */
    public List<Object[]> getAllByNativeQuery(String query);
    
    /**
     * Find array values by a native query
     * @param query
     * @return Object[]
     */
    public Object[] getByNativeQuery(String query);
    
    
    public String getTaxonHighClassification(String fullName, int treeDefId);
    
    
    /**
     * This method is only used for inventory text version
     */
    public void deleteByJPQL();  
}
