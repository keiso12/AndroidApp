package br.edu.up.app.ui.produto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import br.edu.up.app.data.Personagem
import br.edu.up.app.databinding.FragmentProdutoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonagemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewModel : PersonagemViewModel by activityViewModels()
        val binding = FragmentProdutoBinding.inflate(layoutInflater)

        val personagem = viewModel.personagem
        binding.inputNome.setText(personagem.nome)
        binding.inputDescricao.setText(personagem.descricao)
        binding.inputVida.setText(personagem.vida.toString())
        binding.inputForca.setText(personagem.forca.toString())
        binding.inputDefesa.setText(personagem.defesa.toString())

        binding.btnSalvar.setOnClickListener {
            val personagemSalvar = Personagem(
                personagem.id,
                personagem.docId,
                binding.inputNome.text.toString(),
                binding.inputDescricao.text.toString(),
                binding.inputVida.text.toString().toInt(),
                binding.inputForca.text.toString().toInt(),
                binding.inputDefesa.text.toString().toInt(),
                0,0
            )
            viewModel.personagem = personagemSalvar
            viewModel.salvar(personagemSalvar)
            findNavController().popBackStack()
        }

        return binding.root
    }
}