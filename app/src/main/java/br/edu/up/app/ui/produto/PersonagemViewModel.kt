package br.edu.up.app.ui.produto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.up.app.data.Personagem
import br.edu.up.app.data.PersonagemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonagemViewModel
    @Inject constructor(val repository: PersonagemRepository) : ViewModel() {

    var personagem: Personagem = Personagem()

    private var _personagens = MutableStateFlow(listOf<Personagem>())
    val personagens: Flow<List<Personagem>> = _personagens

    init {
        viewModelScope.launch {
            repository.personagens.collect{personagens ->
                _personagens.value = personagens
            }
        }
    }

    fun novo(){
        this.personagem = Personagem()
    }

    fun editar(personagem: Personagem){
        this.personagem = personagem
    }

    fun salvar() = viewModelScope.launch {
        repository.salvar(personagem)
    }

    fun excluir(personagem: Personagem) = viewModelScope.launch {
        repository.excluir(personagem)
    }
}