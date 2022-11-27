package com.example.szymonapp005;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {
    SQLiteDatabase _db;
    Context _context;
    String _name;
    int _version;
    public DatabaseManager(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        _context = context;
        _name = name;
        _version = version;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tabela1 (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'title' TEXT, 'description' TEXT, 'color' TEXT, 'imagePath' TEXT)");
    }
    @SuppressLint("Range")
    public ArrayList<Note> getAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Note> notes = new ArrayList<>();
        Cursor result = db.rawQuery("SELECT * FROM tabela1" , null);
        while(result.moveToNext()){
            notes.add( new Note(
                result.getInt(result.getColumnIndex("_id")),
                result.getString(result.getColumnIndex("title")),
                result.getString(result.getColumnIndex("description")),
                result.getString(result.getColumnIndex("color")),
                result.getString(result.getColumnIndex("imagePath"))
            ));

        }
        db.close();
        return notes;
    }
    public boolean insert(String title, String description, String color, String imagePath){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("color", color);
        contentValues.put("imagePath", imagePath);

        db.insertOrThrow("tabela1", null, contentValues); // gdy insert się nie powiedzie, będzie błąd
        db.close();
        return true;
    }
    public int delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        int out = db.delete("tabela1", "_id = ?", new String[]{String.valueOf(id)});
        db.close();
        return out;
    }
    public int update(int id, String newTitle, String newDescription, String newColor){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", newTitle);
        contentValues.put("description", newDescription);
        contentValues.put("color", newColor);

        int out = db.update("tabela1", contentValues, "_id = ?", new String[]{String.valueOf(id)});
        db.close();
        return out;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS tabela1");
        onCreate(db);
    }
}
