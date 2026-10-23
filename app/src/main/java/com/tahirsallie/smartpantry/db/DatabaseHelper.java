package com.tahirsallie.smartpantry.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tahirsallie.smartpantry.model.PantryItem;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "smart_pantry.db";
    private static final int DB_VERSION = 1;

    // pantry_items
    public static final String T_PANTRY = "pantry_items";
    public static final String P_ID = "id";
    public static final String P_NAME = "name";
    public static final String P_QTY = "quantity";
    public static final String P_UNIT = "unit";
    public static final String P_EXPIRY = "expiry_date";

    // recipes
    public static final String T_RECIPES = "recipes";
    public static final String R_ID = "id";
    public static final String R_NAME = "name";
    public static final String R_STEPS = "steps";

    // recipe_ingredients
    public static final String T_RING = "recipe_ingredients";
    public static final String RI_ID = "id";
    public static final String RI_RECIPE_ID = "recipe_id";
    public static final String RI_NAME = "name";
    public static final String RI_QTY = "quantity";
    public static final String RI_UNIT = "unit";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + T_PANTRY + " ("
                + P_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + P_NAME + " TEXT NOT NULL, "
                + P_QTY + " REAL NOT NULL, "
                + P_UNIT + " TEXT NOT NULL, "
                + P_EXPIRY + " TEXT)");

        db.execSQL("CREATE TABLE " + T_RECIPES + " ("
                + R_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + R_NAME + " TEXT NOT NULL, "
                + R_STEPS + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + T_RING + " ("
                + RI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RI_RECIPE_ID + " INTEGER NOT NULL, "
                + RI_NAME + " TEXT NOT NULL, "
                + RI_QTY + " REAL NOT NULL, "
                + RI_UNIT + " TEXT NOT NULL, "
                + "FOREIGN KEY(" + RI_RECIPE_ID + ") REFERENCES "
                + T_RECIPES + "(" + R_ID + ") ON DELETE CASCADE)");
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);   // needed for ON DELETE CASCADE
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + T_RING);
        db.execSQL("DROP TABLE IF EXISTS " + T_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + T_PANTRY);
        onCreate(db);
    }

    // ---------- CREATE ----------
    public long addPantryItem(PantryItem item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(P_NAME, item.getName());
        v.put(P_QTY, item.getQuantity());
        v.put(P_UNIT, item.getUnit());
        v.put(P_EXPIRY, item.getExpiryDate());
        return db.insert(T_PANTRY, null, v);
    }

    // ---------- READ ----------
    public List<PantryItem> getAllPantryItems() {
        List<PantryItem> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        try (Cursor c = db.query(T_PANTRY, null, null, null, null, null,
                P_NAME + " COLLATE NOCASE ASC")) {
            while (c.moveToNext()) {
                list.add(cursorToItem(c));
            }
        }
        return list;
    }

    public PantryItem getPantryItem(long id) {
        SQLiteDatabase db = getReadableDatabase();
        try (Cursor c = db.query(T_PANTRY, null, P_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null)) {
            return c.moveToFirst() ? cursorToItem(c) : null;
        }
    }

    // ---------- UPDATE ----------
    public int updatePantryItem(PantryItem item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(P_NAME, item.getName());
        v.put(P_QTY, item.getQuantity());
        v.put(P_UNIT, item.getUnit());
        v.put(P_EXPIRY, item.getExpiryDate());
        return db.update(T_PANTRY, v, P_ID + "=?",
                new String[]{String.valueOf(item.getId())});
    }

    // ---------- DELETE ----------
    public int deletePantryItem(long id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(T_PANTRY, P_ID + "=?", new String[]{String.valueOf(id)});
    }

    private PantryItem cursorToItem(Cursor c) {
        return new PantryItem(
                c.getLong(c.getColumnIndexOrThrow(P_ID)),
                c.getString(c.getColumnIndexOrThrow(P_NAME)),
                c.getDouble(c.getColumnIndexOrThrow(P_QTY)),
                c.getString(c.getColumnIndexOrThrow(P_UNIT)),
                c.getString(c.getColumnIndexOrThrow(P_EXPIRY)));
    }
}
