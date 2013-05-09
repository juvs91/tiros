package build.modelos;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Clase Cuadro
 * Maneja la imagen y la duraci??n de un cuadro de animaci??n
 */
public class Cuadro {
   public Bitmap imagen;           //Imagen del cuadro
   public double tiempo;            //Duraci??n del cuadro
   Rect rectangulo;

   /**
    * M??todo constructos de la clase Cuadro
    * Crea un cuadro vac??o
    */
   public Cuadro() {
       this.imagen = null;
       this.tiempo = 0;
       this.rectangulo = null;
   }

   /**
    * M??todo constructor de la clase Cuadro
    * Crea un cuadro de animaci??n con la imagen y el tiempo
    * recibidos como par??metros
    *
    * @param imagen es la imagen del cuadro
    * @param tiempo es la duraci??n del cuadro
    */
   public Cuadro(Bitmap imagen, double tiempo) {
       this.imagen = imagen;
       this.tiempo = tiempo;
       rectangulo = new Rect(0,0,imagen.getWidth(), imagen.getHeight());
   }
}
