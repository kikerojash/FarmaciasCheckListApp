package farmacias.peruanas.com.farmaciaschecklistapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
import farmacias.peruanas.com.farmaciaschecklistapp.model.Instancia;

@Dao
public interface InstanciaDao {

    @Query("SELECT * FROM instancia")
    LiveData<List<Instancia>> findAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertOrder(Instancia instancia);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllOrders(List<Instancia> instanciaList);

    @Query("SELECT * FROM instancia")
    LiveData<List<Instancia>> obtenerListaInstanciaLive();
}
