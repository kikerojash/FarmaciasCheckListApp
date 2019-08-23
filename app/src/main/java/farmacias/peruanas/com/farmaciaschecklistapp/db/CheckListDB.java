package farmacias.peruanas.com.farmaciaschecklistapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import farmacias.peruanas.com.farmaciaschecklistapp.dao.CheckListDao;
import farmacias.peruanas.com.farmaciaschecklistapp.dao.InstanciaDao;
import farmacias.peruanas.com.farmaciaschecklistapp.dao.UsuarioDao;
import farmacias.peruanas.com.farmaciaschecklistapp.model.Checklist;
import farmacias.peruanas.com.farmaciaschecklistapp.model.Instancia;
import farmacias.peruanas.com.farmaciaschecklistapp.model.Usuario;

@Database(entities = {Usuario.class, Checklist.class, Instancia.class}, version = 1)
public abstract class CheckListDB extends RoomDatabase {

    private static volatile CheckListDB INSTANCE;

    abstract public UsuarioDao usuarioDao();

    abstract public CheckListDao checkListDao();

    abstract public InstanciaDao instanciaDao();

    public static CheckListDB getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (CheckListDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CheckListDB.class, "checklist.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
