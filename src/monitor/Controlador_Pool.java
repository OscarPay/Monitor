/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitor;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Oscar
 */
public class Controlador_Pool {
    private PoolManager manager = new PoolManager();
    
    
    public void iniciarPool(){
        manager.start();
    }
    
    public DatosBD pedirConexion(){
        return manager.brindarConexion();
    }
    
    @Override
    public void finalize(){
        try {
            super.finalize();
            
        } catch (Throwable ex) {
            Logger.getLogger(Controlador_Pool.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
 
}
