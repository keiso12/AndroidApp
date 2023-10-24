package br.edu.up.app

import android.app.Application
import android.content.Context
import br.edu.up.app.data.BancoSQLite
import br.edu.up.app.data.PersonagemDAO
import br.edu.up.app.data.PersonagemRepository
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
    fun providePersonagemRepository(personagemDAO: PersonagemDAO) : PersonagemRepository {
        return PersonagemRepository(personagemDAO)
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