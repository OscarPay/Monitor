package monitor;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vanessa
 */
public class AdminMonitor {

    private Timer timer;
    private boolean modificacion = false;
    private DatosBD informacion;

    public AdminMonitor() {
        this.timer = new Timer();
        informacion = new DatosBD();
    }

    public DatosBD getInformacion() {
        return informacion;
    }

    /**
     * Metodo que checa si el archivo configuracionBD.txt,
     *
     * @throws IOException
     */
    public void checkFile() throws IOException {

        File configuracion = new File("configuracionBD.txt");

        System.out.println("Empezó la verificación");

        TimerTask task = new MonitorDeArchivoDeConfiguracion(configuracion) {

            @Override
            protected void onChange(File file) {
                System.out.println("File " + file.getName() + " has changed!");
                Date lastModified = new Date(file.lastModified());
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                System.out.println(format.format(lastModified));

                try {
                    obtenerCambios(file);
                    modificacion = true;
                } catch (IOException ex) {
                    Logger.getLogger(AdminMonitor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        // repeat the check 5 second
        timer.schedule(task, new Date(), 5000);
    }

    public void stopCheck() {
        System.out.println("Se detuvo la verificación");
        timer.cancel();
    }

    public boolean hayModificacion() {
        return modificacion;
    }

    /**
     * Metodo que lee el archivo de configuracion y setea los campos con su
     * respectivo dato.
     *
     * @return
     */
    public DatosBD cargarConfiguracion() {
        File archivo = new File("configuracionBD.txt");
        try {
            obtenerCambios(archivo);
        } catch (IOException ex) {
            Logger.getLogger(AdminMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        return informacion;
    }

    public void setModificacion(boolean modificacion) {
        this.modificacion = modificacion;
    }

    /**
     * Metodo que actualiza cambios en la variable informacion.
     *
     * @param file
     * @throws IOException
     */
    public void obtenerCambios(File file) throws IOException {
        FileReader cambios;
        String[] configuracion;
        String linea, ip, name, password, user;
        int port, pool;
        try {
            cambios = new FileReader(file);
            BufferedReader br = new BufferedReader(cambios);
            //La primera línea corresponde al tamaño del pool
            pool = Integer.parseInt(br.readLine());
          
            while ((linea = br.readLine()) != null) {
                configuracion = linea.split(" ");
                name = configuracion[0];
                ip = configuracion[1];
                port = Integer.parseInt(configuracion[2]);
                user = configuracion[3];
                password = configuracion[4];

                if(pool <= 0) pool = 100;
                informacion = crearCambio(pool, name, ip, port, user, password);                
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AdminMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private DatosBD crearCambio(int tamPool, String nombreBD,
            String ip, int puerto, String usuario, String password) {
        
        return new DatosBD(tamPool, nombreBD, ip, puerto, usuario, password);
    }
}
