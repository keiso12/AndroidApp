package br.edu.up.app

import android.app.Application
import android.content.Context
import br.edu.up.app.data.BancoSQLite
import br.edu.up.app.data.PersonagemDAO
import br.edu.up.app.data.PersonagemRepository
import br.edu.up.app.data.PersonagemRepositoryFirebase
import br.edu.up.app.data.PersonagemRepositorySqlite
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@HiltAndroidApp
@InstallIn(SingletonComponent::class)
class AppCardapio : Application() {

    @Provides
    fun providePersonagemRef(): CollectionReference{
        return Firebase.firestore.collection("personagens")
    }
    @Provides
    fun providePersonagemRepositoryFirebase(personagemRef: CollectionReference):PersonagemRepository{
        return PersonagemRepositoryFirebase(personagemRef)
    }
    @Provides
    fun providePersonagemRepositorySqlite(personagemDAO:PersonagemDAO):PersonagemRepositorySqlite{
        return PersonagemRepositorySqlite(personagemDAO)
    }
    @Provides
    fun providePersonagemDAO(bancoSQLite: BancoSQLite): PersonagemDAO {
        return bancoSQLite.personagemDAO()
    }

    @Provides
    @Singleton
    fun provideBanco(@ApplicationContext ctx: Context): BancoSQLite {
        return BancoSQLite.get(ctx)
    }
}