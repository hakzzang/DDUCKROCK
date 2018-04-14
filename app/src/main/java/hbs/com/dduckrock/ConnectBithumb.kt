package hbs.com.dduckrock

import android.os.AsyncTask
import android.util.Log
import android.widget.EditText
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by ooongi on 2018-04-07.
 */
class ConnectBithumb : AsyncTask<String, String, String>(){
    var resultET: EditText?= null;

    override fun onProgressUpdate(vararg values: String?) {
        super.onProgressUpdate(*values)

    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        if(result.equals("ERROR")){

        }else{
            val parentJson: JSONObject = JSONObject(result)
            val dataJSON: JSONObject = JSONObject(parentJson["data"].toString())
            val asksJSON: JSONArray = JSONArray(dataJSON["asks"].toString())
            Log.d("json_array",asksJSON[0].toString())
            val costJSON: JSONObject = JSONObject(asksJSON[0].toString())
            resultET!!.setText(costJSON["price"].toString())
        }


        cancel(true)
    }

    override fun doInBackground(vararg urls: String): String? {
        Log.d("url",urls[0].toString());
        val url: URL = URL(urls[0].toString());
        var resultStringBuilder: String = "";
        try {
            val connection = url
                    .openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.doInput = true
            connection.setRequestProperty("Connection", "Close");

            val `is` = connection.inputStream

            val br = BufferedReader(InputStreamReader(`is`))
            var readLine: String? = null

            do {
                readLine = br.readLine()
                if (readLine == null)
                    break
                resultStringBuilder += readLine;
            } while (readLine == null)
            br.close()
            `is`.close()
            connection.disconnect()
        }catch (e:Exception){
            resultStringBuilder="ERROR"
        }
        return resultStringBuilder
    }

    override fun onCancelled(result: String?) {
        super.onCancelled(result)
    }

    override fun onCancelled() {
        super.onCancelled()
    }

    override fun onPreExecute() {
        super.onPreExecute()
    }


}