package hbs.com.dduckrock.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import hbs.com.dduckrock.MainActivity
import hbs.com.dduckrock.R
import kotlinx.android.synthetic.main.activity_first.*

@SuppressLint("Registered")
/**
 * Created by ooongi on 2018-04-11.
 */

class FirstActivity:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        buyWalletBtn.setOnClickListener {
            onClick(it)
        }
        sellWalletBtn.setOnClickListener {
            onClick(it)
        }
        calculatorBtn.setOnClickListener {
            onClick(it)
        }

        portfolioBtn.setOnClickListener{
            onClick(it)
        }
    }

    fun onClick(view:View)
    {
        when(view.id){
            R.id.buyWalletBtn -> startActivity(Intent(this, WalletActivity::class.java))
            R.id.sellWalletBtn-> startActivity(Intent(this, SellWalletActivity ::class.java))
            R.id.calculatorBtn -> startActivity(Intent(this, MainActivity::class.java))
            R.id.portfolioBtn -> startActivity(Intent(this, MainActivity::class.java))
        }
    }
}