package hbs.com.dduckrock.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import hbs.com.dduckrock.Model.CoinModel
import hbs.com.dduckrock.R
import java.text.DecimalFormat


/**
 * Created by ooongi on 2018-04-14.
 */

class CoinListAdapter : BaseAdapter {

    lateinit var context: Context
    lateinit var myCoinArray: ArrayList<CoinModel>;
    lateinit var inflater: LayoutInflater;

    constructor(context: Context, myCoinArray: ArrayList<CoinModel>, inflater: LayoutInflater) {
        this.context = context
        this.myCoinArray = myCoinArray
        this.inflater = inflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView: View? = null
        var coinTV: TextView? = null
        var coinIV: ImageView? = null
        var coinMountTV: TextView? = null
        Log.d("item_pos",position.toString())
        convertView = inflater.inflate(R.layout.coin_list_spinner_normal, parent, false)

        if (myCoinArray != null) {
            //데이터세팅

            coinTV = convertView.findViewById(R.id.coinTV)
            coinIV = convertView.findViewById(R.id.coinIV)
            coinMountTV = convertView.findViewById(R.id.coinMountTV)

            coinTV.text = myCoinArray[position].coinName
            if (myCoinArray[position].coinMount > 0.0) {
                coinIV.setImageDrawable(context.resources.getDrawable(R.drawable.exist_icon))
                coinMountTV.text = DecimalFormat("#,##0.0").format(myCoinArray[position].coinMount)
            } else {
                coinIV.setImageDrawable(context.resources.getDrawable(R.drawable.no_exist_icon))
                coinMountTV.text="0.0"
            }
        }
        return convertView!!
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView: View? = null
        var coinTV: TextView? = null
        var coinIV: ImageView? = null
        var coinMountTV: TextView? = null
        Log.d("item_pos",position.toString())
        convertView = inflater.inflate(R.layout.coin_list_spinner_normal, parent, false)

        if (myCoinArray != null) {
            //데이터세팅

            coinTV = convertView.findViewById(R.id.coinTV)
            coinIV = convertView.findViewById(R.id.coinIV)
            coinMountTV = convertView.findViewById(R.id.coinMountTV)

            coinTV.text = myCoinArray[position].coinName
            if (myCoinArray[position].coinMount > 0.0) {
                coinIV.setImageDrawable(context.resources.getDrawable(R.drawable.exist_icon))
                coinMountTV.text = DecimalFormat("#,##0.0").format(myCoinArray[position].coinMount)
            } else {
                coinIV.setImageDrawable(context.resources.getDrawable(R.drawable.no_exist_icon))
                coinMountTV.text="0.0"
            }
        }
        return convertView!!
    }

    override fun getItem(position: Int): Any = myCoinArray.get(position).coinName

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = if (myCoinArray == null) 0 else myCoinArray.size
}