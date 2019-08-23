package farmacias.peruanas.com.farmaciaschecklistapp;

import android.content.Context;

import farmacias.peruanas.com.farmaciaschecklistapp.api.RetrofitInstance;
import farmacias.peruanas.com.farmaciaschecklistapp.base.AppExecutors;
import farmacias.peruanas.com.farmaciaschecklistapp.db.CheckListDB;
import farmacias.peruanas.com.farmaciaschecklistapp.login.LoginFactory;
import farmacias.peruanas.com.farmaciaschecklistapp.login.LoginRepository;
import farmacias.peruanas.com.farmaciaschecklistapp.login.service.ChecklistService;
import farmacias.peruanas.com.farmaciaschecklistapp.login.service.LoginService;
import farmacias.peruanas.com.farmaciaschecklistapp.login.service.MaestrosService;

//import farmacias.peruanas.com.farmaciaschecklistapp.login.service.RespuestaService;
import farmacias.peruanas.com.farmaciaschecklistapp.main.preguntas.PreguntasFactory;
import farmacias.peruanas.com.farmaciaschecklistapp.main.preguntas.PreguntasRepository;
import farmacias.peruanas.com.farmaciaschecklistapp.main.preguntas.service.RespuestaService;
import farmacias.peruanas.com.farmaciaschecklistapp.utils.AppConstants;

public class Injection {


    public static LoginFactory provideViewModelFactory(Context context) {
        return new LoginFactory(new LoginRepository(CheckListDB.getInstance(context),
                RetrofitInstance.createService(LoginService.class, AppConstants.BASE_URL, context),
                RetrofitInstance.createService(ChecklistService.class, AppConstants.BASE_URL, context),
                RetrofitInstance.createService(MaestrosService.class, AppConstants.BASE_URL, context)
               // RetrofitInstance.createService(RespuestaService.class, AppConstants.BASE_URL, context)
                , new AppExecutors()));
    }


   public static PreguntasFactory providePreguntasViewModelFactory(Context context) {
        return new PreguntasFactory(new PreguntasRepository(RetrofitInstance
                .createService(RespuestaService.class,
                        AppConstants.BASE_URL, context),
                CheckListDB.getInstance(context),
                new AppExecutors()));
    }
   /* fun provideLoginViewModelFactory(application: Application): LoginFactory {
        return LoginFactory(
                LoginRepository.getInstance(
                        DermaDb.getDatabase(application),
                        ServiceFactory.getService()
                )
                , application
        )
    }*/

   /* public static UserDataSource provideUserDataSource(Context context) {
        UsersDatabase database = UsersDatabase.getInstance(context);
        return new LocalUserDataSource(database.userDao());
    }*/


    /*

          UsersDatabase database = UsersDatabase.getInstance(context);
        return new LocalUserDataSource(database.userDao());


    * */
}
