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

    private ArrayList<DatosBD> conexiones;
    private AdminMonitor monitor;
    private DatosBD informacionBD = new DatosBD();

    /**
     * Constructor que se encarga de inicializar el pool con las conexiones
     * segun el archivo, la informacion con los datos del archivo, y thread que
     * checa cada 5 segundos el archivo para confirmar si hay cambios
     */
    public PoolManager() {
        try {

            this.conexiones = new ArrayList<>();
            monitor = new AdminMonitor();

            informacionBD = monitor.cargarConfiguracion();
            iniciarPool(informacionBD);

            monitor.checkFile();

        } catch (IOException ex) {
            Logger.getLogger(PoolManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void iniciarPool(DatosBD informacion) {
        actualizarPool(informacion.getTamPool());
    }

    /**
     * Este es el que esta checando cada 5 segundos si el documento de
     * configuracion cambió.
     */
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
                if (monitor.hayModificacion()) {
                    monitor.setModificacion(false);
                    System.out.println("Antes de la modificación***");
                    System.out.println(informacionBD);
                    informacionBD = monitor.getInformacion();
                    actualizar(informacionBD);
                    System.out.println("Después de la modificación***");
                    System.out.println(informacionBD);
                    hacerCambiosEnConexion(informacionBD);
                }

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        }
    }

    /**
     * Metodo que actualiza el pool.
     *
     * @param nuevo, son los datos de la conexión.
     */
    public void actualizar(DatosBD nuevo) {
        actualizarPool(nuevo.getTamPool());
    }

    /**
     * Crea las demas conexiones que faltan
     *
     * @param cantidadPool
     */
    private void actualizarPool(int cantidadPool) {
        int cantidadActual = conexiones.size();

        if (cantidadPool < cantidadActual) {
            //se reduce
            ArrayList<DatosBD> conexionesNuevas = new ArrayList<>();
            for (DatosBD conexion : conexiones) {
                //!!!!!!!!Verificar
                if (!conexion.isIsActivo()) {
                    conexionesNuevas.add(conexion);
                    if (conexionesNuevas.size() == cantidadPool) {
                        break;
                    }
                }
            }
            conexiones = conexionesNuevas;

            if (conexiones.size() < cantidadPool) {
                crearNuevasConexiones(cantidadPool);
            }

        } else if (cantidadPool > cantidadActual) {
            crearNuevasConexiones(cantidadPool);
        }
    }

    /**
     * Cuando detecta un cambio, setea los nuevos datos del archivo de
     * configuracion
     *
     * @param nuevoConexion
     */
    private void hacerCambiosEnConexion(DatosBD nuevoConexion) {
        for (DatosBD conexion : conexiones) {
            conexion.setIp(nuevoConexion.getIp());
            conexion.setNombreBD(nuevoConexion.getNombreBD());
            conexion.setPuerto(nuevoConexion.getPuerto());
            conexion.setPassword(nuevoConexion.getPassword());
            conexion.setUsuario(nuevoConexion.getUsuario());
        }
    }

    /**
     * Crea las nuevas conexiones con los datos que tiene sobre la bd, la
     * cantidad nos dice cuantas conexiones seran
     *
     * @param cantidad
     */
    private void crearNuevasConexiones(int cantidad) {
        while (conexiones.size() < cantidad) {

            DatosBD nueva = new DatosBD(informacionBD.getTamPool(),
                    informacionBD.getNombreBD(), informacionBD.getIp(),
                    informacionBD.getPuerto(), informacionBD.getUsuario(),
                    informacionBD.getPassword());
            conexiones.add(nueva);
        }
    }

    /**
     * Devuelve una conexion que no esta activa.
     *
     * @return
     */
    public DatosBD brindarConexion() {
        /*for (DatosBD conexion : conexiones) {
            if (!conexion.isIsActivo()) {
                conexion.setIsActivo(true);
                return conexion;
            }
        }*/
        informacionBD.setIsActivo(true);
        return informacionBD;
    }

}
