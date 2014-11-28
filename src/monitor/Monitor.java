/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitor;

import java.io.*;
import java.util.*;

/**
 *
 * @author Vanessa
 */
public abstract class Monitor extends TimerTask{
      private long timeStamp;
      private File file;

      public Monitor( File file ) {
        this.file = file;
        this.timeStamp = file.lastModified();
      }

      public final void run() {
        long timeStamp = file.lastModified();

        if( this.timeStamp != timeStamp ) {
          this.timeStamp = timeStamp;
          onChange(file);
        }
      }

      protected abstract void onChange( File file );
}
