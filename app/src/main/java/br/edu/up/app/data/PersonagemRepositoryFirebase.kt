package br.edu.up.app.data

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject



class PersonagemRepositoryFirebase
@Inject constructor(val personagensRef: CollectionReference): PersonagemRepository {

    private var _personagens = MutableStateFlow(listOf<Personagem>())
    override val personagens: Flow<List<Personagem>> get() = _personagens.asStateFlow()

    init {
        personagensRef.addSnapshotListener { snapshot, _ ->
            if (snapshot == null) {
                _personagens = MutableStateFlow(listOf())
            } else{
                var personagens = mutableListOf<Personagem>()
                snapshot.documents.forEach{doc ->
                    val personagem = doc.toObject<Personagem>()
                if(personagem != null){
                    personagem.docId = doc.id
                    personagens.add(personagem)
                }
            }
            _personagens.value = personagens
            }
        }
    }
    override suspend fun salvar(personagem: Personagem) {
        if(personagem.docId.isNullOrEmpty()){
            var doc = personagensRef.document()
            personagem.docId = doc.id
            doc.set(personagem)
        } else {
            personagensRef.document(personagem.docId).set(personagem)
        }
    }

    override suspend fun excluir(personagem: Personagem) {
        personagensRef.document(personagem.docId).delete()
    }

    override suspend fun excluirTodos() {
        _personagens.collect{ personagens ->
            personagens.forEach{ personagem ->
                personagensRef.document(personagem.docId).delete()
            }
        }
    }
}