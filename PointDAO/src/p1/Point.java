/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package p1;

/**
 *
 * @author Bhushan
 */
public class Point {
    
    private int id;
    private int x;
    private int y;
    
    
    public Point(){}
    
    public Point(int id){
    
        this.id=id;
    }
    
    public Point(int id, int x, int y){
    
        this.id=id;
        this.x=x;
        this.y=y;
    } 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
}