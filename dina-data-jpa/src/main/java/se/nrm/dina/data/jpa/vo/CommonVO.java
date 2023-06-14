/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.data.jpa.vo;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A support Entity for partially fetch values in entities
 * @author idali
 */
@XmlRootElement
public class CommonVO implements Serializable {

    @XmlElement
    private int id;
    
    @XmlElement
    private String name;

    @XmlElement
    private String guid;
     
    @XmlElement
    private List<String> list;
    
    public CommonVO() {
        
    }
    
    public CommonVO(int id) {
        this.id = id;
    }
    
    public CommonVO(String guid) {
        this.guid = guid;
    }

    public CommonVO(String name, String guid) {
        this.name = name;
        this.guid = guid;
    }
    
    public CommonVO(int id, String name, String guid) {
        this.id = id;
        this.name = name;
        this.guid = guid;
    }
    
    public CommonVO(String name, String guid, List<String> list) { 
        this.name = name;
        this.guid = guid;
        this.list = list;
    }
        
    public CommonVO(int id, String name, String guid, List<String> list) {
        this.id = id;
        this.name = name;
        this.guid = guid;
        this.list = list;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGuid() {
        return guid;
    }  

    public List<String> getList() {
        return list;
    }
    
    @Override
    public String toString() {
        return name + " -- " + guid;
    }
}
