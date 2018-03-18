package com.example.sese.myrecipes2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper{

    private final String TABLE_FOOD_DETAIL = "food_details";
    private static String KEY_ID = "id";
    private static String TITLE = "title";
    private static String DESCRIPTION = "description";

    public DBHandler(Context context) {
        super(context, "food_list", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FOOD_LIST_TABLE = "CREATE TABLE " + TABLE_FOOD_DETAIL +"("
                + KEY_ID + " INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT,"
                + TITLE + " TEXT,"
                + DESCRIPTION + " TEXT " + ")";
        db.execSQL(CREATE_FOOD_LIST_TABLE);
        db.delete(TABLE_FOOD_DETAIL, null, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD_DETAIL);
        onCreate(db);
    }
    void addNewFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, food.getKey());
        values.put(TITLE, food.getTitle());
        values.put(DESCRIPTION, food.getDescription());
        db.insert(TABLE_FOOD_DETAIL, null, values);
        db.close();
    }
    public boolean updateFoodRecipe(int id, String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(KEY_ID, id);
        args.put(TITLE, title);
        args.put(DESCRIPTION, description);
        return db.update(TABLE_FOOD_DETAIL, args, KEY_ID + "=" + id, null) > 0;
    }

    public boolean deleteFood(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_FOOD_DETAIL, KEY_ID + "=" + id, null) > 0;
    }

    public List<Food> getAllFoodList() {
        List<Food> foodList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_FOOD_DETAIL;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Food food = new Food();
                food.setKey(Integer.parseInt(cursor.getString(0)));
                food.setTitle(cursor.getString(1));
                food.setDescription(cursor.getString(2));
                foodList.add(food);
            } while (cursor.moveToNext());
        }
        return foodList;
    }
}
