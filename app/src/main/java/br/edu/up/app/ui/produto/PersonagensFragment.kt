package br.edu.up.app.ui.produto

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.edu.up.app.databinding.FragmentListaProdutosBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PersonagensFragment : Fragment() {

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //val banco = BancoSQLite.get(requireContext())
        //val repository = ProdutoRepository(banco.produtoDao())
        //val viewModel = ProdutoViewModel(repository)

        //Injeção automática de dependência
        val viewModel : PersonagemViewModel by activityViewModels()

        val binding = FragmentListaProdutosBinding.inflate(layoutInflater)
        val recyclerView = binding.root

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.personagens.collect{ personagens ->
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    recyclerView.adapter = PersonagensAdapter(personagens, viewModel)
                }
            }
        }
        return binding.root
    }
}