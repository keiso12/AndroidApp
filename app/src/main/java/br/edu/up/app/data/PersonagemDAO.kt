package br.edu.up.app.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonagemDAO {

    @Query("select * from personagens")
    fun listar(): Flow<List<Personagem>>

    @Insert
    suspend fun inserir(personagem: Personagem)

    @Update
    suspend fun atualizar(personagem: Personagem)

    @Delete
    suspend fun excluir(personagem: Personagem)

    @Query("delete from personagens where id = :id")
    suspend fun excluir(id: Int)

    @Query("delete from personagens")
    suspend fun excluirTodos()
}