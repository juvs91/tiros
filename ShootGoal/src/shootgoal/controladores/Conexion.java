package shootgoal.controladores;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

public class Conexion {
	//link donde se encuentra el php con las acciones a realizar
	private static String amigosUrl = "amigos.php";//"http://code.systheam.com/webServices/amigos.php";
	private static String tiroUrl = "tiro.php";
	private static String loginUrl = "login.php";
	private static String signUpUrl = "signUp.php";
	//tad para la accion a realiza
	private static String amigosTag = "amigos";
	private static String juegoPendiente = "pendiente";
	private static final String BASE_URL = "http://goalshootout.comoj.com/";
	private static AsyncHttpClient client = new AsyncHttpClient();
	
	public static void amigos (int id, AsyncHttpResponseHandler responseHandler) {
			RequestParams params = new RequestParams();
			String resource = amigosUrl;
			params.put("id", id+"");
			Conexion.post(resource, params, responseHandler);				
	}
	public static void tiro(int idPortero,int idTirador, AsyncHttpResponseHandler responseHandler){
			RequestParams params = new RequestParams();
			String resource = tiroUrl;
			params.put("idPortero", idPortero+"");
			params.put("idTirador", idTirador+"");
			Conexion.post(resource, params, responseHandler);	
	}
	public static void login(String usuario,String password, AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		String resource = loginUrl;
		params.put("usuario", usuario+"");
		params.put("password", password+"");
		Conexion.post(resource, params, responseHandler);	
	}
	public static void signUp(String usuario,String password,String mail, AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		String resource = signUpUrl;
		params.put("usuario", usuario+"");
		params.put("password", password+"");
		params.put("mail", mail+"");
		Conexion.post(resource, params, responseHandler);	
	}

	
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