package br.edu.up.app.ui.comparar

import android.R
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import br.edu.up.app.databinding.FragmentGalleryBinding
import br.edu.up.app.ui.produto.CompararViewModel
import br.edu.up.app.data.Personagem
import br.edu.up.app.ui.produto.PersonagemViewModel
import kotlinx.coroutines.launch

class CompararFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val viewModel: CompararViewModel by activityViewModels()

        val adapter = ArrayAdapter<String>(requireContext(), R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerCima.adapter = adapter
        binding.spinnerBaixo.adapter = adapter

        // Coleta a Flow de personagens
        lifecycleScope.launch {
            viewModel.personagens.collect { listaDePersonagens ->
                // Mapeia os nomes dos personagens
                val nomesDosPersonagens = listaDePersonagens.map { it.nome }

                // Limpa o adaptador e adiciona os nomes dos personagens
                adapter.clear()
                adapter.addAll(nomesDosPersonagens)
            }
        }
        binding.btnComparar.setOnClickListener {
            val nome1 = binding.spinnerCima.selectedItem.toString()
            val nome2 = binding.spinnerBaixo.selectedItem.toString()
            if (nome1 == nome2){
                exibirErro("Não é possível comparar dois personagens iguais")
            }

            var personagem1: Personagem? = null
            var personagem2: Personagem? = null

            lifecycleScope.launch {

                viewModel.personagens.collect { listaDePersonagens ->
                    for (personagem in listaDePersonagens) {
                        if (nome1 == personagem.nome) {
                            personagem1 = personagem
                        } else if (nome2 == personagem.nome) {
                            personagem2 = personagem
                        }
                    }

                    if (personagem1 != null && personagem2 != null) {
                        val resultado = compararPersonagens(personagem1!!, personagem2!!)
                        exibirOpcaoSalvar(resultado, personagem1!!, personagem2!!)
                    }
                }
            }
        }


        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun compararPersonagens(personagem1: Personagem, personagem2: Personagem): String {
        val defesa1 = personagem1.defesa / 10
        val defesa2 = personagem2.defesa / 10

        val vida1 = personagem1.vida + defesa1
        val vida2 = personagem2.vida + defesa2

        val ataques1 = vida2 / personagem1.forca
        val ataques2 = vida1 / personagem2.forca

        return when {
            ataques1 < ataques2 -> "${personagem1.nome} GANHOU!"
            ataques2 < ataques1 -> "${personagem2.nome} GANHOU!"
            else -> "Empatou!"
        }
    }



    private fun exibirOpcaoSalvar(resultado: String, personagem1: Personagem, personagem2: Personagem) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Deseja salvar o resultado?")
        alertDialogBuilder.setMessage(resultado)
        alertDialogBuilder.setPositiveButton("Salvar") { dialog, _ ->
            when (resultado) {
                "${personagem1.nome} GANHOU!" -> {
                    personagem1.vitorias++
                    personagem2.derrotas++
                }
                "${personagem2.nome} GANHOU!" -> {
                    personagem2.vitorias++
                    personagem1.derrotas++
                }
            }

            val viewModel: PersonagemViewModel by activityViewModels()
            viewModel.salvar(personagem1)
            viewModel.salvar(personagem2)
            dialog.dismiss()
        }
        alertDialogBuilder.setNegativeButton("Não") { dialog, _ -> dialog.dismiss() }
        alertDialogBuilder.create().show()
    }


    private fun exibirErro(erro : String) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Não é possível comparar dois personagens iguais")
        alertDialogBuilder.setPositiveButton("Fechar") { dialog, _ -> dialog.dismiss() }
        alertDialogBuilder.create().show()
    }
}
