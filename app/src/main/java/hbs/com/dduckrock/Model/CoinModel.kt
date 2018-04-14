package hbs.com.dduckrock.Model

/**
 * Created by ooongi on 2018-04-14.
 */

class CoinModel{
    var coinName:String = ""
    var coinMount:Double =0.0

    constructor(){

    }

    constructor(coinName: String, coinMount: Double) {
        this.coinName = coinName
        this.coinMount = coinMount
    }
}