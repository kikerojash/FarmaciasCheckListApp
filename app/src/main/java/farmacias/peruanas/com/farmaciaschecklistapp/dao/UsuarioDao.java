package farmacias.peruanas.com.farmaciaschecklistapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import farmacias.peruanas.com.farmaciaschecklistapp.model.Usuario;
import io.reactivex.Completable;

@Dao
public interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(Usuario user);
}
