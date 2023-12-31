package br.edu.up.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Personagem::class], version = 3, exportSchema = true)
abstract class BancoSQLite : RoomDatabase() {

    abstract fun personagemDAO(): PersonagemDAO


    companion object{


        @Volatile
        private var INSTANCIA: BancoSQLite? = null

        fun get(context: Context): BancoSQLite {
            if (INSTANCIA == null) {
                INSTANCIA = Room.databaseBuilder(
                    context.applicationContext,
                    BancoSQLite::class.java,
                    "meu_banco.db"
                ).fallbackToDestructiveMigration().build()
            }
            return INSTANCIA as BancoSQLite
        }
    }

}