/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suryacut.model;

/**
 *
 * @author CorneliusDavid
 */
public class TempatKeramas {
    private static TempatKeramas instance;
    
    private TempatKeramas(){
        
    }
    
    public static TempatKeramas getInstance(){
        if(instance==null){
            instance=new TempatKeramas();
        }
        return instance;
    }
}
