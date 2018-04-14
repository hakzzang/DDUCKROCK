package hbs.com.dduckrock

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var resultString: String ="" //String 자료형
    private var target_1 :Int = 0 // Integer 자료형
    private var target_2 = 0 // 자동으로 추론하게 해줌
    private var currencys = listOf("BTC", "ETH", "DASH", "LTC", "ETC", "XRP", "BCH", "XMR", "ZEC", "QTUM", "BTG", "EOS", "ICX", "VEN", "TRX")
    private var url = "https://api.bithumb.com/public/orderbook/BTC"
    private var connectBithumb: ConnectBithumb? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        connectBithumb = ConnectBithumb()

        resultBtn.setOnClickListener {
            view -> resultTV.text = (targetET1.text.toString().toInt()+targetET2.text.toString().toInt()).toString()
        }

        connectBtn.setOnClickListener {
            view ->
            Log.d("preUrl",url)
            connectBithumb!!.execute(url)
        }

    }
}