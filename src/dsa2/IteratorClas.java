/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsa2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author macbookpro
 */
public class IteratorClas {
    public static List<Integer> list = null;
     public static List<Integer> lists = null;
    public static void main(String[] args ){
        list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        
        IteratorClas i = new IteratorClas();
        Iterator <Integer> h =  i.adds(list);
        h = lists.iterator();
        while(h.hasNext()){
            
            System.out.println("hello"+h.next());
        }
        
        
        
    }

    public Iterator<Integer> adds(List x) {
      Iterator<Integer> li = list.iterator();
       while(li.hasNext())
       {
           System.out.println(li.next()+1);
       }
        return li;
    }
    
}
