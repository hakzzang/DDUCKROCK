package hbs.com.dduckrock

import android.os.AsyncTask
import android.util.Log
import android.widget.TextView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by ooongi on 2018-04-07.
 */
class ConnectBithumb : AsyncTask<String, String, String>(){
    var resultTV: TextView?= null;

    override fun onProgressUpdate(vararg values: String?) {
        super.onProgressUpdate(*values)

    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        Log.d("result",result)
        resultTV!!.text=result;
    }

    override fun doInBackground(vararg urls: String): String? {
        Log.d("url",urls[0].toString());
        val url: URL = URL(urls[0].toString());
        var resultStringBuilder:String ="";
        val connection = url
                .openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.doInput = true

        val `is` = connection.inputStream

        val br = BufferedReader(InputStreamReader(`is`))
        var readLine: String? = null

        do {
            readLine = br.readLine()
            if (readLine == null)
                break
            resultStringBuilder+=readLine;
        } while (readLine==null)

        br.close()
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