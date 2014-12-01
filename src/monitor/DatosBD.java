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
public class DatosBD {

    private int tamPool;
    private String nombreBD;
    private String ip;
    private int puerto;
    private String usuario;
    private String password;

    public DatosBD() {}

    public DatosBD(int tamPool, String nombreBD, String ip, int puerto, String usuario, String password) {
        this.tamPool = tamPool;
        this.nombreBD = nombreBD;
        this.ip = ip;
        this.usuario = usuario;
        this.password = password;
        this.puerto = puerto;
    }

    /**
     * @return the tamPool
     */
    public int getTamPool() {
        return tamPool;
    }

    /**
     * @param tamPool the tamPool to set
     */
    public void setTamPool(int tamPool) {
        this.tamPool = tamPool;
    }

    /**
     * @return the nombreBD
     */
    public String getNombreBD() {
        return nombreBD;
    }

    /**
     * @param nombreBD the nombreBD to set
     */
    public void setNombreBD(String nombreBD) {
        this.nombreBD = nombreBD;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the puerto
     */
    public int getPuerto() {
        return puerto;
    }

    /**
     * @param puerto the puerto to set
     */
    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    @Override
    public String toString() {
        return "DatosModificados{" + "tamPool=" + tamPool + ", nombreBD=" + nombreBD + ", ip=" + ip + ", usuario=" + usuario + ", password=" + password + ", puerto=" + puerto + '}';
    }

}
