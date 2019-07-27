package com.example.ships;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "BattleField.db";
    private static final int DATABASE_VERSION=1;

    public DatabaseOpenHelper(Context context){
        super (context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    public boolean updateDatabase (int id, int numberOfMasts, int shipNumber, int isShip, int isHit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String idString = String.valueOf(id);
        String numberOfMastsString = String.valueOf(numberOfMasts);
        String shipNumberString = String.valueOf(shipNumber);
        String isShipString = String.valueOf(isShip);
        String isHitString = String.valueOf(isHit);


        contentValues.put("Primary_Key",idString);
        contentValues.put("Number_Of_Masts", numberOfMastsString);
        contentValues.put("Ship_Number",shipNumberString);
        contentValues.put("Is_Ship",isShipString);
        contentValues.put("Is_Hit",isHit);

        db.update("Battle_Field_Player_One_Random_Game", contentValues, "Primary_Key"+"="+idString, null);
        return true;
    }
}
