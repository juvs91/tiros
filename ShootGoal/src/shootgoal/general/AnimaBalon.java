package shootgoal.general;

import java.util.ArrayList;
import java.util.List;

import shootgoal.modelos.Cuadro;

import shootgoal.modelos.Tirador;


import android.graphics.Bitmap;
import android.graphics.Point;


import android.graphics.Bitmap;

/**
 * Libreria para poder animar el balon
 *
 */

public class AnimaBalon {
	   public List<Cuadro> cuadros;            //Lista de objetos Cuadro para los cuadros de la animaci��n
	    public int indice;                      //��ndice del cuadro actual
	    public float tiempo;                    //Tiempo transcurrido
	    public float duracion;                  //Duraci��n de la animaci��n
	    public Tirador balon;
	    public int movimientosBalon;
	    public Point posicionInicial;

	    
	    public AnimaBalon(){
	    	 //Crea el arreglo de cuadros
	        cuadros = new ArrayList<Cuadro>();
	        //Inicializa la duraci��n de la animaci��n en 0
	        duracion = 0;
	        movimientosBalon = 1;
	        posicionInicial = new Point();

	        //Inicializa la animaci��n
	    }
	    
	    /**
	     * M��todo sumaCuadro
	     * A��ade un cuadro (imagen) a la animaci��n con
	     * la duraci��n indicada (tiempo que se muestra el cuadro)
	     *
	     * @param imagen es la imagen del cuadro
	     * @param duracion es el tiempo que se muestra el cuadro
	     */
	    public synchronized void sumaCuadro(Bitmap imagen, float duracion) {
	        //Agrega la duraci��n del cuadro a la duraci��n de la animaci��n
	        this.duracion += duracion;
	        //Crea un nuevo cuadro y lo agrega a la animaci��n
	        cuadros.add(new Cuadro(imagen, this.duracion));
	    }
	    

	    public synchronized boolean moverBalonAPosicion(Point posicionFinal, double tiempo){
	    	this.tiempo += tiempo;
	    	
	    	if(movimientosBalon == 1){
	    		posicionInicial.set(balon.posicion.x,balon.posicion.y);
	    	}
	    	
    		if (this.tiempo >= 0.2*14) {
                //Reinicia la animación
                this.tiempo = (float) (this.tiempo % (0.2*10));
                //indice = 1;
                //movimientosBalon = 1; 
                //balon.posicion.x = posicionInicial.x;
                //balon.posicion.y = posicionInicial.y;
                return false;
            }
    		
    		while (this.tiempo > movimientosBalon*0.2){
    			if(movimientosBalon>6 && movimientosBalon < 11){
    				balon.posicion.x += (posicionFinal.x-posicionInicial.x)/4;
    				balon.posicion.y += (posicionFinal.y-posicionInicial.y)/4;
    			} else if (movimientosBalon >= 11){
    				balon.posicion.x -= 7;
    				balon.posicion.y += 20;
    			}
    			movimientosBalon++;
    		}
    		return true;
	    }
	    
	    /**
	     * Metodo iniciar
	     * Inicializa la animaci��n en el primer cuadro con
	     * tiempo 0
	     */
	    public synchronized void iniciar(){
	        tiempo = 0;
	        indice = 0;
	    }
	    /*public synchronized void anima(float tiempo) {
        //Si la animaci��n no esta vac��a
        if (cuadros.size() > 1) {
            //Guarda el tiempo transcurrido
            this.tiempo += tiempo;

            //Si el tiempo transcurrido es mayor a la duraci��n de la animaci��n
            if (this.tiempo >= duracion) {
                //Reinicia la animaci��n
                this.tiempo = this.tiempo % duracion;
                indice = 0;
            }

            //Cambia el ��ndice del cuadro de acuerdo al tiempo transcurrido
            while (this.tiempo > cuadros.get(indice).tiempo) {
                indice ++;
            }
        }
    }*/
    
    public synchronized void lanzaBalon(float tiempo){

    }

    /**
     * Metodo getCuadro
     * Retorna la imagen del cuadro actual
     *
     * @return Rgresa un Bitmap con la imagen del caudro actual
     */
    public synchronized Bitmap getCuadro(){
        //Si la animaci��n esta vac��a
        if (cuadros.size() == 0)
            //Regresa nulo
            return null;
        else
            //Regresa la imagen del cuadro actual
            return cuadros.get(indice).imagen;
    }
	    
	    

}
