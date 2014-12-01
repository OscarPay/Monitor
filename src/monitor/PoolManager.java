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
    
    /**
     * Constructor que se encarga de inicializar el pool con las conexiones segun el archivo,
     * la informacion con los datos del archivo, y thread que checa cada 5 segundos el archivo 
     * para confirmar si hay cambios
     */
    public PoolManager() {
        try {
            this.conexiones = new ArrayList();
            monitor = new AdminMonitor();
            info =monitor.cargarConfiguracion();
            init(info);
            
            monitor.checkFile();
        } catch (IOException ex) {
            Logger.getLogger(PoolManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void init(DatosBD informacion){
        actualizarPool(informacion.getTamPool());
    }
    
    
    
    private DatosBD info = new DatosBD();
    /**
     * Este es el que esta checando cada 5 segundos si el documento de configuracion
     * cambio
     */
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
    
    /**
     * 
     * @param nuevo Metodo que actualiza el pool
     */
    public void actualizar(DatosBD nuevo){
        actualizarPool(nuevo.getTamPool());
    }
    
    /**
     * Cuando detecta un cambio, setea los nuevos datos del archivo de configuracion
     * @param nuevo 
     */
    private void setChangesOnConexion(DatosBD nuevo){
        for(ConexionBD conexion : conexiones){
            conexion.setHOST(nuevo.getIp());
            conexion.setNombreBD(nuevo.getNombreBD());
            conexion.setPORT(nuevo.getPuerto());
            conexion.setPassword(nuevo.getPassword());
            conexion.setUsuario(nuevo.getUsuario());
        }
    }

    /**
     * Crea las demas conexiones que faltan
     * @param cantidadPool 
     */
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
        } 
    }

    /**
     * Crea las nuevas conexiones con los datos que tiene sobre la bd,
     * la cantidad nos dice cuantas conexiones seran
     * @param cantidad 
     */
    private void crearNuevasConexiones(int cantidad) {
        while (conexiones.size() < cantidad) {
            //!!!!!!!!!!!
            
            ConexionBD nueva = new ConexionBD(info.getIp(), info.getPuerto(),info.getNombreBD(),info.getUsuario(),info.getPassword());
            conexiones.add(nueva);
        }
    }
    
    /**
     * Devuelve una conexion que no esta activa.
     * @return 
     */
    public ConexionBD brindarConexion(){
        for(ConexionBD conexion : conexiones){
            if(!conexion.IsActivo()){
                return conexion;
            }
        }
        
        return null;
    }
    

}
