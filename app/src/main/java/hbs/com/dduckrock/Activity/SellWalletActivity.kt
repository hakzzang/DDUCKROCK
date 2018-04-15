package hbs.com.dduckrock.Activity

import android.content.Context
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Toast
import hbs.com.dduckrock.Adapter.CoinListAdapter
import hbs.com.dduckrock.ConnectBithumb
import hbs.com.dduckrock.DBControl.AddWalletDBAs
import hbs.com.dduckrock.Model.CoinModel
import hbs.com.dduckrock.R
import kotlinx.android.synthetic.main.activity_wallet.*
import java.text.DecimalFormat

/**
 * Created by ooongi on 2018-04-13.
 */

class SellWalletActivity: AppCompatActivity(), TextWatcher {
    private var currencys: ArrayList<String> = arrayListOf<String>("BTC", "ETH", "DASH", "LTC", "ETC", "XRP", "BCH", "XMR", "ZEC", "QTUM", "BTG", "EOS", "ICX", "VEN", "TRX")
    private var selectCoinArray: ArrayList<CoinModel> = arrayListOf<CoinModel>()
    private var myCoinArrays: ArrayList<CoinModel> = arrayListOf()
    private var bithumb_url = "https://api.bithumb.com/public/orderbook/"
    private var addWalletDBAs: AddWalletDBAs? = null
    private var itemPosition:Int = 0

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell_wallet)

        addWalletDBAs = AddWalletDBAs(this)

        selectMyCoinWallert()
        initMyCoinArrays()

        coinMountET.setOnFocusChangeListener { view, focusBool ->
            changeToSelectIcon(coinMountIV as ImageView, focusBool, R.drawable.select_coin, R.drawable.no_select_coin)
        }
        coinMountET.addTextChangedListener(this)
        coin_costET.setOnFocusChangeListener { view, focusBool ->
            changeToSelectIcon(coin_costIV as ImageView, focusBool, R.drawable.select_money, R.drawable.no_select_money)
        }
        registrationBtn.setOnClickListener { view ->
            addCoinWallet(coinName = coinListSpinner.selectedItem.toString(),coinMount = coinMountET.text.toString())
            finish()
        }
        coin_costET.addTextChangedListener(this)
        coinMountET.addTextChangedListener(coinMountTW)

        val layoutInflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val coinSpinnerAdapter: CoinListAdapter = CoinListAdapter(this, myCoinArrays, layoutInflater)
        coinListSpinner.adapter = coinSpinnerAdapter
        coinListSpinner.onItemSelectedListener = adapterClickListener
    }

    private val adapterClickListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
            val connectBithumb = ConnectBithumb()
            if(myCoinArrays[position]==null){
                coinMountET.setText("0")
            }else{
                coinMountET.setText(myCoinArrays[position].coinMount.toString())
            }

            connectBithumb.resultET = coin_costET
            connectBithumb.execute(bithumb_url + myCoinArrays[position].coinName)
            itemPosition = position
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun changeToSelectIcon(view: ImageView, focusBool: Boolean, select_imageMipmap: Int, no_select_imageMipmap: Int) {
        if (focusBool) {
            view.setImageDrawable(getDrawable(select_imageMipmap))
        } else {
            view.setImageDrawable(getDrawable(no_select_imageMipmap))
        }
    }

    private val coinMountTW = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val coinMount_double:Double = p0.toString().toDouble()
            if(coinMount_double>myCoinArrays[itemPosition].coinMount.toDouble()){
                Toast.makeText(this@SellWalletActivity,getString(R.string.over_sell_coin),Toast.LENGTH_SHORT).show()
                coinMountET.setText(myCoinArrays[itemPosition].coinMount.toString())
            }else if(myCoinArrays[itemPosition].coinMount.toDouble()<0){
                Toast.makeText(this@SellWalletActivity,getString(R.string.down_sell_coin),Toast.LENGTH_SHORT).show()
                coinMountET.setText("0")
            }
        }
    }

    override fun afterTextChanged(p0: Editable?) {

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (coin_costET.text.isNotEmpty() && coinMountET.text.isNotEmpty()) {
            val df = DecimalFormat("#,##0.00")
            resultTV.text = df.format((coin_costET.text.toString().toDouble() * coinMountET.text.toString().toDouble()))
        } else {
            resultTV.text = "0";
        }
    }

    private fun selectMyCoinWallert(){
        if (addWalletDBAs == null) {
            addWalletDBAs = AddWalletDBAs(this)
        }
        val coinCursor: Cursor = addWalletDBAs!!.allSelectCoinItems!!
        if(selectCoinArray==null){
            selectCoinArray = ArrayList()
        }else{
            selectCoinArray.clear()
        }
        if(coinCursor.moveToFirst()){
            do{
                selectCoinArray.add(CoinModel(coinCursor.getString(0),coinCursor.getString(1).toDouble()))
            }while(coinCursor.moveToNext())
        }
    }

    private fun initMyCoinArrays() {
        currencys.sort()
        for (coinName in currencys) {
            if(selectCoinArray.size==0){
                myCoinArrays.add(CoinModel(coinName, 0.0))
                continue
            }
            for (selectCoin in selectCoinArray) {
                if (coinName.toLowerCase().toCharArray()[0] <= selectCoin.coinName.toLowerCase().toCharArray()[0]) {
                    if (coinName.toLowerCase().equals(selectCoin.coinName.toLowerCase())) {
                        myCoinArrays.add(selectCoin)
                    }
                } else {
                    continue
                }
            }
        }
    }

    private fun addCoinWallet(coinName: String, coinMount: String) {
       if (addWalletDBAs == null) {
            addWalletDBAs = AddWalletDBAs(this)
        }

        val coinCursor: Cursor = addWalletDBAs!!.selectCoinItem(coinName)!!

        if (coinCursor.count == 0) {
            addWalletDBAs!!.insertCoin(coinName, coinMount)
        } else {
            coinCursor.moveToFirst()
            val alreadyHaveCoin = coinCursor.getString(1)
            val totalCoin = Integer.valueOf(alreadyHaveCoin)-Integer.valueOf(coinMount)
            addWalletDBAs!!.updateCoin(coinName, totalCoin.toString())
        }
    }
}


