package build.general;

import android.graphics.Bitmap;
import build.modelos.Cuadro;

import java.util.ArrayList;
import java.util.List;



/**
 * Clase Animacion
 *
 * @author Jugando con JAVA
 * @version 1.0 31/08/2011
 */
public class AnimacionPortero {
    public List<Cuadro> cuadros;            //Lista de objetos Cuadro para los cuadros de la animaci??n
    public int indice;                      //??ndice del cuadro actual
    public double tiempo;                    //Tiempo transcurrido
    public double duracion;                  //Duraci??n de la animaci??n

    /**
     * M??todo constructor de la clase Animacion
     * Crea una animaci??n vac??a
     */
    public AnimacionPortero() {
        //Crea el arreglo de cuadros
        cuadros = new ArrayList<Cuadro>();
        //Inicializa la duraci??n de la animaci??n en 0
        duracion = 0;
        //Inicializa la animaci??n
        iniciar();
    }

    /**
     * M??todo sumaCuadro
     * A??ade un cuadro (imagen) a la animaci??n con
     * la duraci??n indicada (tiempo que se muestra el cuadro)
     *
     * @param imagen es la imagen del cuadro
     * @param duracion es el tiempo que se muestra el cuadro
     */
    public synchronized void sumaCuadro(Bitmap imagen, double duracion) {
        //Agrega la duraci??n del cuadro a la duraci??n de la animaci??n
        this.duracion += duracion;
        //Crea un nuevo cuadro y lo agrega a la animaci??n
        cuadros.add(new Cuadro(imagen, this.duracion));
    }

    /**
     * Metodo iniciar
     * Inicializa la animaci??n en el primer cuadro con
     * tiempo 0
     */
    public synchronized void iniciar(){
        tiempo = 0;
        indice = 0;
    }

    /**
     * M??todo anima
     * Actualiza la animaci??n en base al tiempo transcurrido desde
     * la ??tlima actualizaci??n
     *
     * @param tiempo es el tiempo transcurrido
     */
    /*public synchronized void anima(float tiempo) {
        //Si la animaci??n no esta vac??a
        if (cuadros.size() > 1) {
            //Guarda el tiempo transcurrido
            this.tiempo += tiempo;

            //Si el tiempo transcurrido es mayor a la duraci??n de la animaci??n
            if (this.tiempo >= duracion) {
                //Reinicia la animaci??n
                this.tiempo = this.tiempo % duracion;
                indice = 0;
            }

            //Cambia el ??ndice del cuadro de acuerdo al tiempo transcurrido
            while (this.tiempo > cuadros.get(indice).tiempo) {
                indice ++;
            }
        }
    }*/
    
    public synchronized boolean pararEnElCentro(double tiempo){
    	if(cuadros.size() > 1){
    		this.tiempo += tiempo;
    		
    		if (this.tiempo >= 0.2*12) {
                //Reinicia la animaci??n
                this.tiempo = this.tiempo % 0.2*12;
                //indice = 1;
                return false;
            }
    		
    		//while (this.tiempo > cuadros.get(indice).tiempo){
    			//indice ++;
    		//}
    	}
    	return true;
    }
    
    public synchronized boolean pararEnLaIzquierda(double tiempo){
    	if(cuadros.size() > 1){
    		this.tiempo += tiempo;
    		
    		if (this.tiempo >= duracion-0.2*10) {
                //Reinicia la animaci??n
                this.tiempo = this.tiempo % (duracion-0.2*10);
                //indice = 1;
                return false;
            }
    		
    		while (this.tiempo > cuadros.get(indice).tiempo){
    			indice ++;
    		}
    		
    		
    	}
    	return true;
    }
    
    public synchronized boolean pararEnLaDerecha(double tiempo){
    	if(cuadros.size() > 1){
    		this.tiempo += tiempo;
    		
    		if (this.tiempo >= duracion) {
                //Reinicia la animaci??n
                this.tiempo = this.tiempo % duracion;
                //indice = 1;
                return false;
            }
    		
    		while (this.tiempo > cuadros.get(indice).tiempo){
    			if(indice == 1){
    				indice = 12;
    				this.tiempo += 0.2*10;
    			} else {
    				indice ++;
    			}
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
        //Si la animaci??n esta vac??a
        if (cuadros.size() == 0)
            //Regresa nulo
            return null;
        else
            //Regresa la imagen del cuadro actual
            return cuadros.get(indice).imagen;
    }
}
