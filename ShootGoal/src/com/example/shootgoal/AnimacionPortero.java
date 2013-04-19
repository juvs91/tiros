package com.example.shootgoal;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import com.example.shootgoal.modelos.Cuadro;

/**
 * Clase Animacion
 *
 * @author Jugando con JAVA
 * @version 1.0 31/08/2011
 */
public class AnimacionPortero {
    public List<Cuadro> cuadros;            //Lista de objetos Cuadro para los cuadros de la animación
    public int indice;                      //Índice del cuadro actual
    public double tiempo;                    //Tiempo transcurrido
    public double duracion;                  //Duración de la animación

    /**
     * Método constructor de la clase Animacion
     * Crea una animación vacía
     */
    public AnimacionPortero() {
        //Crea el arreglo de cuadros
        cuadros = new ArrayList<Cuadro>();
        //Inicializa la duración de la animación en 0
        duracion = 0;
        //Inicializa la animación
        iniciar();
    }

    /**
     * Método sumaCuadro
     * Añade un cuadro (imagen) a la animación con
     * la duración indicada (tiempo que se muestra el cuadro)
     *
     * @param imagen es la imagen del cuadro
     * @param duracion es el tiempo que se muestra el cuadro
     */
    public synchronized void sumaCuadro(Bitmap imagen, double duracion) {
        //Agrega la duración del cuadro a la duración de la animación
        this.duracion += duracion;
        //Crea un nuevo cuadro y lo agrega a la animación
        cuadros.add(new Cuadro(imagen, this.duracion));
    }

    /**
     * Metodo iniciar
     * Inicializa la animación en el primer cuadro con
     * tiempo 0
     */
    public synchronized void iniciar(){
        tiempo = 0;
        indice = 0;
    }

    /**
     * Método anima
     * Actualiza la animación en base al tiempo transcurrido desde
     * la útlima actualización
     *
     * @param tiempo es el tiempo transcurrido
     */
    /*public synchronized void anima(float tiempo) {
        //Si la animación no esta vacía
        if (cuadros.size() > 1) {
            //Guarda el tiempo transcurrido
            this.tiempo += tiempo;

            //Si el tiempo transcurrido es mayor a la duración de la animación
            if (this.tiempo >= duracion) {
                //Reinicia la animación
                this.tiempo = this.tiempo % duracion;
                indice = 0;
            }

            //Cambia el índice del cuadro de acuerdo al tiempo transcurrido
            while (this.tiempo > cuadros.get(indice).tiempo) {
                indice ++;
            }
        }
    }*/
    
    public synchronized boolean pararEnLaIzquierda(double tiempo){
    	if(cuadros.size() > 1){
    		this.tiempo += tiempo;
    		
    		if (this.tiempo >= duracion) {
                //Reinicia la animación
                this.tiempo = this.tiempo % duracion;
                indice = 1;
                return false;
            }
    		
    		while (this.tiempo > cuadros.get(indice).tiempo){
    			indice ++;
    		}
    		
    		
    	}
    	return true;
    }

    /**
     * Metodo getCuadro
     * Retorna la imagen del cuadro actual
     *
     * @return Rgresa un Bitmap con la imagen del caudro actual
     */
    public synchronized Bitmap getCuadro(){
        //Si la animación esta vacía
        if (cuadros.size() == 0)
            //Regresa nulo
            return null;
        else
            //Regresa la imagen del cuadro actual
            return cuadros.get(indice).imagen;
    }
}
