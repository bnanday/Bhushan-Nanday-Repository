/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package p1;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author Bhushan
 */
public class PointDaoDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
    
        
        AbstractApplicationContext ac = new FileSystemXmlApplicationContext("C:\\Users\\Bhushan\\Documents\\NetBeansProjects\\PointDAO\\src\\p1\\one.xml");
        
        
        Point p1 = (Point)ac.getBean("pt");        
        PointDAO.insertPoint(p1);
        
        // The above operation demonstrates insert operation on the the class Point. Like wise we can perform
        //other operations like getPoint, delete point etc
        
        
    }
    
}
