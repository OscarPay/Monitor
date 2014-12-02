/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitor;

/**
 *
 * @author Oscar
 */
public class Controlador_Pool {
    private PoolManager manager;

    public Controlador_Pool() {
        manager = new PoolManager();
    }
    
    
    public void iniciarPool(){
        manager.start();
    }
    
    public DatosBD pedirConexion(){
        return manager.brindarConexion();
    }
}
