package com.example.shootgoal;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Clase Cuadro
 * Maneja la imagen y la duración de un cuadro de animación
 */
public class Cuadro {
   Bitmap imagen;           //Imagen del cuadro
   float tiempo;            //Duración del cuadro
   Rect rectangulo;

   /**
    * Método constructos de la clase Cuadro
    * Crea un cuadro vacío
    */
   public Cuadro() {
       this.imagen = null;
       this.tiempo = 0;
       this.rectangulo = null;
   }

   /**
    * Método constructor de la clase Cuadro
    * Crea un cuadro de animación con la imagen y el tiempo
    * recibidos como parámetros
    *
    * @param imagen es la imagen del cuadro
    * @param tiempo es la duración del cuadro
    */
   public Cuadro(Bitmap imagen, float tiempo) {
       this.imagen = imagen;
       this.tiempo = tiempo;
       rectangulo = new Rect(0,0,imagen.getWidth(), imagen.getHeight());
   }
}
