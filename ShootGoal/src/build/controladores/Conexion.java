package build.controladores;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Crea una conexion con los respectivos web services que se tengan o dispongan
 * @author CoockiesNCode
 *
 */
public class Conexion {
	//link donde se encuentra el php con las acciones a realizar
	private static String amigosUrl = "amigos.php";//"http://code.systheam.com/webServices/amigos.php";
	private static String tiroUrl = "tiro.php";
	private static String paroUrl = "paro.php";
	private static String loginUrl = "login.php";
	private static String signUpUrl = "signUp.php";
	private static String buscarUrl= "buscar.php";
	private static String juego= "juego.php";
	private static String juegoUpdateUrl= "juegoUpdate.php";
	private static String juegoNuevoUrl= "nuevoJuego.php";


	//tad para la accion a realiza

	private static final String BASE_URL = "http://goalshootout.comoj.com/";
	private static AsyncHttpClient client = new AsyncHttpClient();
	/**
	 * Genera la lista de amigos
	 * @param id
	 * @param responseHandler
	 */
	public static void amigos (int id, AsyncHttpResponseHandler responseHandler) {
			RequestParams params = new RequestParams();
			String resource = amigosUrl;
			params.put("id", id+"");
			Conexion.post(resource, params, responseHandler);				
	}
	/**
	 * Genera un tiro en la ase de datos
	 * @param jugador1
	 * @param jugador2
	 * @param status
	 * @param posTiro
	 * @param aceptado
	 * @param posParo
	 * @param responseHandler
	 */
	public static void tiro(int jugador1,int jugador2,int status,int posTiro,boolean aceptado,int posParo, AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		String resource = tiroUrl;
		params.put("jugador1", jugador1+"");
		params.put("jugador2", jugador2+"");
		params.put("status", status+"");
		params.put("posTiro", posTiro+"");
		params.put("aceptado", aceptado+"");
		params.put("posParo", posParo+"");
		Conexion.post(resource, params, responseHandler);	
	}
	/**
	 * Actualiza la informacion cuando se jugo como portero
	 * @param jugador1
	 * @param jugador2
	 * @param status
	 * @param posTiro
	 * @param aceptado
	 * @param posParo
	 * @param responseHandler
	 */
	public static void paro(int jugador1,int jugador2,int status,int posTiro,boolean aceptado,int posParo, AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		String resource = paroUrl;
		params.put("juagor1", jugador1+"");
		params.put("juagor2", jugador2+"");
		params.put("status", status+"");
		params.put("posTiro", posTiro+"");
		params.put("aceptado", aceptado+"");
		params.put("posParo", posParo+"");
		Conexion.post(resource, params, responseHandler);	
	}
	/**
	 * Conexion cuando se inicia el juego
	 * @param usuario
	 * @param password
	 * @param responseHandler
	 */
	public static void login(String usuario,String password, AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		String resource = loginUrl;
		params.put("usuario", usuario+"");
		params.put("password", password+"");
		Conexion.post(resource, params, responseHandler);	
	}
	/**
	 * HAce el signup de un nuevo usuario
	 * @param usuario
	 * @param password
	 * @param mail
	 * @param responseHandler
	 */
	public static void signUp(String usuario,String password,String mail, AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		String resource = signUpUrl;
		params.put("usuario", usuario+"");
		params.put("password", password+"");
		params.put("mail", mail+"");
		Conexion.post(resource, params, responseHandler);	
	}
	/**
	 * Busca un mail
	 * @param mail
	 * @param responseHandler
	 */
	public static void buscar(String mail, AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		String resource = buscarUrl;
		params.put("mail", mail+"");
		Conexion.post(resource, params, responseHandler);	
	}
	/**
	 * Trae un nuevo juego con los id 
	 * @param idJugador1
	 * @param idJugador2
	 * @param responseHandler
	 */
	public static void juego(int idJugador1,int idJugador2, AsyncHttpResponseHandler responseHandler){
		
		RequestParams params = new RequestParams();
		String resource = juego;
		params.put("idJugador1", idJugador1+"");
		params.put("idJugador2", idJugador2+"");
		Conexion.post(resource, params, responseHandler);	
	}
	/**
	 * HAce una actualizacion a algun juego que ya exista
	 * @param idJugador1
	 * @param idJugador2
	 * @param estado
	 * @param posTiro
	 * @param posParo
	 * @param puntajeJugador1
	 * @param puntajeJugador2
	 * @param responseHandler
	 */
	public static void juegoUpdate(int idJugador1,int idJugador2,int estado,int posTiro,int posParo,int puntajeJugador1,int puntajeJugador2, AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		String resource = juegoUpdateUrl;
		if(estado>2){
			params.put("idJugadorUno", idJugador2+"");
			params.put("idJugadorDos", idJugador1+"");
		} else {
			params.put("idJugadorUno", idJugador1+"");
			params.put("idJugadorDos", idJugador2+"");
		}
		params.put("estado", estado+"");
		params.put("posTiro", posTiro+"");
		params.put("posParo", posParo+"");
		params.put("puntajeJugadorUno", puntajeJugador1+"");
		params.put("puntajeJugadorDos", puntajeJugador2+"");
		
		Conexion.post(resource, params, responseHandler);	
	}
	
	/**
	 * Genera un nuevo juego
	 * @param idJugador1
	 * @param idJugador2
	 * @param responseHandler
	 */
	public static void nuevoJuego(int idJugador1,int idJugador2, AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		String resource = juegoNuevoUrl;
		params.put("jugador1", idJugador1+"");
		params.put("jugador2", idJugador2+"");

		Conexion.post(resource, params, responseHandler);	
	}
	
	/**
	 * Obtiene la informacion deseada
	 * @param url
	 * @param params
	 * @param responseHandler
	 */
	public static void get(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
				String fullUrl = getAbsoluteUrl(url);
				client.get(fullUrl, params, responseHandler);		
		}

		public static void post(String url, RequestParams params,
				AsyncHttpResponseHandler responseHandler) {
				client.post(getAbsoluteUrl(url), params, responseHandler);
			
		}

		public static void get(String url, AsyncHttpResponseHandler responseHandler) {
			RequestParams params = new RequestParams();
			Conexion.get(url, params, responseHandler);
		}

		public static void post(String url, AsyncHttpResponseHandler responseHandler) {
			RequestParams params = new RequestParams();
			Conexion.post(url, params, responseHandler);
		}
		
		private static String getAbsoluteUrl(String relativeUrl) {
 			return BASE_URL + relativeUrl;
		}
}