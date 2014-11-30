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
    private PoolManager manager = new PoolManager();
    
    
    public void iniciarPool(){
        manager.start();
    }
    
    public ConexionBD pedirConexion(){
        return manager.brindarConexion();
    }
    
    public static void main(String[] args) {
        Controlador_Pool c = new Controlador_Pool();
        c.iniciarPool();
    }
}
