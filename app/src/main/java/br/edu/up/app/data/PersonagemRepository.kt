package br.edu.up.app.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


interface PersonagemRepository{

    val personagens: Flow<List<Personagem>>
    suspend fun salvar(personagem: Personagem)
    suspend fun excluir(personagem: Personagem)
    suspend fun excluirTodos()


}