package hbs.com.dduckrock

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_first.*

@SuppressLint("Registered")
/**
 * Created by ooongi on 2018-04-11.
 */

class FirstActivity:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        walletBtn.setOnClickListener {
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
            R.id.walletBtn-> startActivity(Intent(this,MainActivity::class.java))
            R.id.calculatorBtn-> startActivity(Intent(this,MainActivity::class.java))
            R.id.portfolioBtn-> startActivity(Intent(this,MainActivity::class.java))
        }

    }
}