package apitest;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class json_import {

  private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
    InputStream is = new URL(url).openStream();
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      JSONObject json = new JSONObject(jsonText);
      return json;
    } finally {
      is.close();
    }
  }

  public static void main(String[] args) throws IOException, JSONException {
	int id=0;
	String nombre="sergyonyx";
	String webs="https://euw.api.pvp.net/api/lol/euw";
	String Api="?api_key=fcb02306-537c-497a-8704-b804c1e73d1e";
    JSONObject summ = readJsonFromUrl(webs+"/v1.4/summoner/by-name/"+nombre+Api);
    //System.out.println(summ.toString());
    JSONObject yo=(JSONObject) summ.get(nombre);
    summ=null;
    id=(int) yo.get("id");
    yo=null;
    JSONObject suma=readJsonFromUrl(webs+"/v1.3/stats/by-summoner/"+id+"/summary"+Api);
    JSONArray suma2;
    suma2=suma.getJSONArray("playerStatSummaries");
    for (int i=0;i<suma2.length();++i){
    	JSONObject o=suma2.getJSONObject(i);
    	System.out.print("Tipo: "+o.get("playerStatSummaryType"));
    	System.out.print(", Victorias: "+o.get("wins"));
    	JSONObject o2=o.getJSONObject("aggregatedStats");
    	Iterator it=o2.keys();
    	if (it.hasNext()){
    		while (it.hasNext()){
    			String s=(String) it.next();
    			System.out.print(" "+s+ " "+ o2.get(s));
    		}
    	}
    	//System.out.print(o2.toString());
    	System.out.println("");
    }
  }
}