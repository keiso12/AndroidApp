package br.edu.up.app.ui.comparar

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import br.edu.up.app.databinding.FragmentGalleryBinding
import br.edu.up.app.ui.produto.CompararViewModel
import br.edu.up.app.data.Personagem
import kotlinx.coroutines.flow.collect
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

        binding.btnComparar.setOnClickListener {

            val nome1 = binding.inputPrimeiroNome.text.toString()
            val nome2 = binding.inputSegundoNome.text.toString()

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
                        exibirResultadoComparacao(resultado)
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
        val resultado: String
        // Lógica de comparação com base em características (vida, força, defesa)
        val defesa1 = personagem1.defesa/10
        val defesa2 = personagem1.defesa/10

        personagem1.vida+=defesa1
        personagem2.vida+=defesa2

        val ataques1:Int
        val ataques2:Int
        ataques1 = personagem2.vida/personagem1.forca
        ataques2 = personagem1.vida/personagem2.forca
        if (ataques1 < ataques2) {
            resultado = "${personagem1.nome} GANHA!"
        } else if (ataques2 < ataques1) {
            resultado = "${personagem2.nome} GANHA!"
        } else {
            resultado = "Empate!"
        }
        return resultado
    }

    private fun exibirResultadoComparacao(resultado: String) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Resultado da Comparação")
        alertDialogBuilder.setMessage(resultado)
        alertDialogBuilder.setPositiveButton("Fechar") { dialog, _ -> dialog.dismiss() }
        alertDialogBuilder.create().show()
    }

}
