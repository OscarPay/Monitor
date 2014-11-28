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
public class ConexionBD{
    private String HOST;
    private int PORT;
    private boolean isActivo = false;
    private String nombreBD, usuario,password;
    
    
    public ConexionBD(String host, int port, String BD, String usuario, String password){
        this.HOST = host;
        this.PORT = port;
        this.nombreBD = BD;
        this.password = password;
        this.usuario = usuario;
    }
    
    public void conectar(){
        
    }
    
    public void desconectar(){
        
    }

    /**
     * @return the HOST
     */
    public String getHOST() {
        return HOST;
    }

    /**
     * @param HOST the HOST to set
     */
    public void setHOST(String HOST) {
        this.HOST = HOST;
    }

    /**
     * @return the PORT
     */
    public int getPORT() {
        return PORT;
    }

    /**
     * @param PORT the PORT to set
     */
    public void setPORT(int PORT) {
        this.PORT = PORT;
    }

    /**
     * @return the isActivo
     */
    public boolean IsActivo() {
        return isActivo;
    }

    /**
     * @param isActivo the isActivo to set
     */
    public void setIsActivo(boolean isActivo) {
        this.isActivo = isActivo;
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

    @Override
    public String toString() {
        return "ConexionBD{" + "HOST=" + HOST + ", PORT=" + PORT + ", isActivo=" + isActivo + ", nombreBD=" + nombreBD + ", usuario=" + usuario + ", password=" + password + '}';
    }
    
}
