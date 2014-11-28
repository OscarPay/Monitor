/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Oscar
 */
public class PoolManager extends Thread {

    private ArrayList<ConexionBD> conexiones;
    private AdminMonitor monitor;
    
    public PoolManager() {
        try {
            this.conexiones = new ArrayList();
            monitor = new AdminMonitor();
            info =monitor.inicializarConexiones();
            init(info);
            
            monitor.checkFile();
        } catch (IOException ex) {
            Logger.getLogger(PoolManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void init(DatosModificados informacion){
        actualizarPool(informacion.getTamPool());
    }
    
    
    
    private DatosModificados info = new DatosModificados();
    
    public void run() {
        while(true){
            try {
                Thread.sleep(5000);
                if(monitor.hayModificacion()){
                    monitor.setModificacion(false);
                    System.out.println("antes de los cambios:");
                    System.out.println(conexiones);
                    info = monitor.getInformacion();
                    actualizar(info);
                    setChangesOnConexion(info);
                    System.out.println("despuees de los cambios");
                    System.out.println(conexiones);
                }
                
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            
        }
    }
    
    public void actualizar(DatosModificados nuevo){
        actualizarPool(nuevo.getTamPool());
    }
    
    private void setChangesOnConexion(DatosModificados nuevo){
        for(ConexionBD conexion : conexiones){
            conexion.setHOST(nuevo.getIp());
            conexion.setNombreBD(nuevo.getNombreBD());
            conexion.setPORT(nuevo.getPuerto());
            conexion.setPassword(nuevo.getPassword());
            conexion.setUsuario(nuevo.getUsuario());
        }
    }

    private void actualizarPool(int cantidadPool) {
        int cantidadActual = conexiones.size();

        if (cantidadPool < cantidadActual) {
            //se reduce
            ArrayList<ConexionBD> conexionesNuevas = new ArrayList();
            for (ConexionBD conexion : conexiones) {
                //!!!!!!!!Verificar
                if (!conexion.IsActivo()) {
                    conexionesNuevas.add(conexion);
                    if (conexionesNuevas.size() == cantidadPool) {
                        break;
                    }
                }
            }
            conexiones = conexionesNuevas;

            if(conexiones.size() < cantidadPool) {
                crearNuevasConexiones(cantidadPool);
            }

        } else if (cantidadPool > cantidadActual) {
            crearNuevasConexiones(cantidadPool);
        } else {
            return;
        }
    }

    private void crearNuevasConexiones(int cantidad) {
        while (conexiones.size() < cantidad) {
            //!!!!!!!!!!!
            
            ConexionBD nueva = new ConexionBD(info.getIp(), info.getPuerto(),info.getNombreBD(),info.getUsuario(),info.getPassword());
            conexiones.add(nueva);
        }
    }
    
    public ConexionBD brindarConexion(){
        for(ConexionBD conexion : conexiones){
            if(!conexion.IsActivo()){
                return conexion;
            }
        }
        
        return null;
    }
    

}
