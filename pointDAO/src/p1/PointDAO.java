/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package p1;

import java.util.ArrayList;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author Bhushan
 */
public class PointDAO {
    
    private static JdbcTemplate jt;
    private static ArrayList<Point> al;
    
    static{
    
        AbstractApplicationContext ac = new FileSystemXmlApplicationContext("C:\\Users\\Bhushan\\Documents\\NetBeansProjects\\PointDAO\\src\\p1\\one.xml");
        
        jt = (JdbcTemplate)ac.getBean("jdbcTemplate");
    }
    
    public static void insertPoint(Point p){
        
        int[] a = new int[3];
        a[0]=p.getId();
        a[1]=p.getX();
        a[2]=p.getY();
        jt.update("insert into point values(?,?,?)",a[0],a[1],a[2]);        
        
    }
    
    public static Point getPoint(Point p){
    
       SqlRowSet srs = jt.queryForRowSet("select * from point where id="+p.getId());
       int x = 0,y=0, id=0;
       Point p1 = null;
       while(srs.next()){
       
            x = srs.getInt("x");
            y = srs.getInt("y");
            p1 = new Point(p.getId(),x,y);
           
       }
       
       return p1;
    }
    
    public static ArrayList<Point> getPoints(){
        
        SqlRowSet srs = jt.queryForRowSet("select * from point");
       int x = 0,y = 0, id=0;
       while(srs.next()){
       
            id = srs.getInt("id");
            x = srs.getInt("x");
            y = srs.getInt("y");
            al.add(new Point(id,x,y));
           
       }
       
       return al;
    
    
    }
    
    public static void deletePoint(Point p){
        
        jt.execute("delete from point where id = "+p.getId());
    
        
    }
    
}
