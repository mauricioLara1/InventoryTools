package com.example.herramientas.data.db

//clases Android y Room
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//imports para una migracion
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

//DAO (interfaces que ejecutan las consultas SQL)
import com.example.herramientas.data.db.dao.HerramientaDao
import com.example.herramientas.data.db.dao.PrestamoDao

//entidades (tablas en la base de datos)
import com.example.herramientas.data.model.HerramientaEntity
import com.example.herramientas.data.model.PrestamoEntity

/**
 *AppDatabase.kt
 *esta es la clase que crea la db en Room
 *comunicando a room y ksp como se llama la db y las tablas y agregamos consultas
 */
//con @Database le decimos a Room que esta clase es una base de datos, o creamos una db
//appdatabase se se crea solo una vez, si no existe db la crea, si existe solo la abre
@Database(
    //agregamos las tablas a la bd
    entities = [HerramientaEntity::class, PrestamoEntity::class],

    //como appdatabase se crea solo una vez cuando se modifica hay que cambiar de version
    //cuando mover la version?
    //agregar columna nueva, eliminar columna
    //agregar una tabla, eliminar una tabla, cambiar nombre de una tabla
    //cuando no mover la version?
    //agregas o cambias consultas al dao
    //cambios no estructurales
    version = 1,


    //exportSchema se usa para guardar un historial de los cambios del esquema.
    //false no lo usa true si lo usa
    exportSchema = false
)


abstract class AppDatabase : RoomDatabase() {

    /**
     * las funciones abstractas se encargan del acceso a los daos porque son promesas
     */
    abstract fun herramientaDao(): HerramientaDao
    abstract fun prestamoDao(): PrestamoDao

    /**
     * se crea un singleton para que solo se pueda crear una instancia de la base de datos
     *esta se encarga de tener varias conexiones abiertas a la vez para evitar errores o perdida de rendimiento
     */
    companion object {

        //@Volatile garantiza que el valor de INSTANCE sea visible
        //para todos los hilos al mismo tiempo (seguridad en hilos).
        @Volatile private var INSTANCE: AppDatabase? = null

        /**
         * Ejemplo de migración:
         * MIGRATION_1_2 agrega una nueva columna 'descripcion' a la tabla herramienta.
         *
         * Esto evita que Room borre toda la base cuando se sube la versión.
         * Aquí simplemente se ejecuta un comando SQL para modificar la estructura.
         *   private val MIGRATION_1_2 = object : Migration(1, 2) {
         *       override fun migrate(database: SupportSQLiteDatabase) {
         *           // ALTER TABLE agrega una columna sin borrar datos existentes
         *           database.execSQL(
         *               "ALTER TABLE herramienta ADD COLUMN descripcion TEXT NOT NULL DEFAULT ''"
         *           )
         *       }
         *   }
         */

        /**
         *aqui nos encargamos de:
         * -Si la base de datos ya fue creada, simplemente la devuelve.
         * -Si no existe aún, la crea con Room.databaseBuilder().
         * @return nos devuelve una instancia de AppDatabase para usar, o solo retorna la db.
         */
        fun getInstance(context: Context): AppDatabase {
            //Si INSTANCE no es nula la devuelve; si es nula crea la db
            //este return siempre se ejecuta, si le toca esperar a crearlo espera si no, no espera
            return INSTANCE ?: synchronized(this) {

                //Room.databaseBuilder() construye la base de datos.
                // -context.applicationContext: se usa el contexto global de la app.
                // -AppDatabase::class.java: le decimos qué clase es la base de datos.
                // -"inventario_herramientas_db": nombre del archivo .db que se creará.
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "inventario_herramientas_db"
                ).build()

                //Guardamos la instancia creada en la variable INSTANCE
                INSTANCE = instance

                // Y la devolvemos para su uso
                instance
            }
        }
    }
}
