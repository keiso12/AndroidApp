package br.edu.up.app.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PersonagemRepository
    @Inject constructor(val personagemDAO: PersonagemDAO) {

    val personagens: Flow<List<Personagem>> get() = personagemDAO.listar()
    suspend fun salvar(personagem: Personagem) {
        if (personagem.id == 0){
            personagemDAO.inserir(personagem)
        } else {
            personagemDAO.atualizar(personagem)
        }
    }
    suspend fun excluir(personagem: Personagem){
        personagemDAO.excluir(personagem)
    }

    suspend fun excluirTodos(){
        personagemDAO.excluirTodos()
    }

//    init {
//        CoroutineScope(Job()).launch {
//
//            produtoDAO.excluirTodos()
//            delay(15000)
//            val produtos = produtos()
//            for(p in produtos){
//                p.id = 0
//                produtoDAO.inserir(p)
//            }
//        }
//    }



}