package farmacias.peruanas.com.farmaciaschecklistapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import farmacias.peruanas.com.farmaciaschecklistapp.model.Checklist;
import farmacias.peruanas.com.farmaciaschecklistapp.model.Instancia;

@Dao
public interface CheckListDao {

    @Query("SELECT * FROM checklist")
    LiveData<List<Checklist>> findAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertOrder(Checklist checklist);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllOrders(List<Checklist> checklists);

    @Query("SELECT * FROM checklist")
    LiveData<List<Checklist>> obtenerListaCheckList();


}
