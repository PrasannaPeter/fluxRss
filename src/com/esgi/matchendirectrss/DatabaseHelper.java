package com.esgi.matchendirectrss;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	String CREATA_TABLE = "CREATE TABLE ELEVE ("+
						"_ID integer Primary key autoincrement,"+
						"NAME_ELEVE varchar";

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATA_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
