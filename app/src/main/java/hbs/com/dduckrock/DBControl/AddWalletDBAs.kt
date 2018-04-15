package hbs.com.dduckrock.DBControl

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log



/**
 * Created by ooongi on 2018-04-14.
 */

class AddWalletDBAs(context: Context) : SQLiteOpenHelper(context, DBFILE_CONTACT, null, DB_VERSION) {

    private val TABLE_NAME = "COIN_TABLE"
    private val mContext: Context? = null
    private var SQL_CREATE_TBL: String? = null

    var SQL_DROP_TBL = "DROP TABLE IF EXISTS \"$TABLE_NAME\""
    var SQL_SELECT = "SELECT * FROM \"$TABLE_NAME\""
    private var db: SQLiteDatabase? = null
    private var cursor: Cursor? = null

    val allSelectCoinItems: Cursor?
        get() {
            createSQL()
            db = writableDatabase
            SQL_SELECT = "SELECT * FROM \"$TABLE_NAME\""
            cursor = db!!.rawQuery(SQL_SELECT, null)

            return cursor
        }

    fun selectCoinItem(cursorName:String): Cursor? {
        createSQL()
        cursor = null
        db = writableDatabase
        SQL_SELECT = "SELECT * FROM \"$TABLE_NAME\" WHERE COIN_NAME='$cursorName'"
        cursor = db!!.rawQuery(SQL_SELECT, null)

        return cursor
    }

    override fun onCreate(db: SQLiteDatabase) {

    }

    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)

    }

    override fun onUpgrade(db: SQLiteDatabase, i: Int, i1: Int) {

    }

    fun createSQL() {
        db = writableDatabase
        setSQL_CREATE()
        db!!.execSQL(SQL_CREATE_TBL)
    }

    private fun setSQL_CREATE() {
        val sqlCreate = ("CREATE TABLE IF NOT EXISTS \"" + TABLE_NAME + "\" "
                + "(" +
                "COIN_NAME" + " TEXT PRIMARY KEY" + ", " +
                "COIN_MOUNT" + " TEXT" + " " +
                ")")
        Log.d("SQLCREATE: ", sqlCreate)
        this.SQL_CREATE_TBL = sqlCreate
    }

    private fun setChangeInputValues(coinName:String, coinMount:String) {
        db = writableDatabase

        val p = db!!.compileStatement("INSERT OR REPLACE INTO \"$TABLE_NAME\" VALUES(?,?);")
        p.bindString(1, coinName)
        p.bindString(2, coinMount)
        p.execute()
    }

    fun insertCoin(coinName:String, coinMount:String) {
        setChangeInputValues(coinName, coinMount)
    }

    fun updateCoin(coinName:String, coinMount:String){
        db = writableDatabase

        val values = ContentValues()
        values.put("COIN_NAME", coinName)
        values.put("COIN_MOUNT", coinMount)

        val id = db!!.update(TABLE_NAME, values, "COIN_NAME='$coinName'", null)

    }

    fun dropTable(tableName: String) {
        db = readableDatabase
        SQL_DROP_TBL = "DROP TABLE IF EXISTS \"$tableName\""
        Log.d("DROP", SQL_DROP_TBL)
        db!!.execSQL(SQL_DROP_TBL)
        db!!.close()
    }

    fun removeItems(colTitle: String, colDate: String) {
        db = writableDatabase
        val whereArs = arrayOf(colTitle, colDate)
        val count_index = db!!.delete(TABLE_NAME, "COL_TITLE = ? AND COL_DATE = ?", whereArs)
        Log.d("count", count_index.toString())
        /*SQLiteStatement p = db.compileStatement("DELETE FROM \""+TABLE_NAME+"\" WHERE COL_TITLE =? AND COL_DATE=?;");
        p.bindString(1,colTitle);
        p.bindString(2, colDate);

        p.executeUpdateDelete();*/
    }

    fun closeDB() {
        if (cursor != null)
            cursor!!.close()
        if (db != null)
            db!!.close()
    }

    companion object {
        val DB_VERSION = 1
        val DBFILE_CONTACT = "contact.db"
    }
}
