package br.edu.up.app.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PersonagemRepositorySqlite
   @Inject constructor(val personagemDAO: PersonagemDAO):PersonagemRepository{

    override val personagens: Flow<List<Personagem>>
        get() = personagemDAO.listar()
    override  suspend fun salvar(personagem: Personagem){
        if(personagem.id == 0){
            personagemDAO.inserir(personagem)
        }else{
            personagemDAO.atualizar(personagem)
        }
    }

    override suspend fun excluir(personagem: Personagem) {
        personagemDAO.excluir(personagem)
    }

    override suspend fun excluirTodos() {
       personagemDAO.excluirTodos()
    }

}