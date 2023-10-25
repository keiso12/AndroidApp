package br.edu.up.app.ui.produto

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import br.edu.up.app.data.Personagem
//import br.edu.up.app.ui.produto.databinding.FragmentItemProdutoBinding
import br.edu.up.app.databinding.FragmentItemProdutoBinding

class PersonagensAdapter(
    private val personagens: List<Personagem>,
    val viewModel: PersonagemViewModel
    ) :
    RecyclerView.Adapter<PersonagensAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemProdutoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemPersonagem = personagens[position]

        holder.txtNome.text = itemPersonagem.nome
        holder.txtForca.text = itemPersonagem.forca.toString()
        holder.txtVida.text = itemPersonagem.vida.toString()
        holder.txtDefesa.text = itemPersonagem.defesa.toString()

        //clique para editar item da lista
        holder.itemView.setOnClickListener { view ->
            viewModel.editar(itemPersonagem)
            val action = PersonagensFragmentDirections.actionNavHomeToProdutoFragment()
            view.findNavController().navigate(action)
        }

        //clique para excluir item da lista
        holder.itemView.setOnLongClickListener { view ->
            AlertDialog.Builder(view.context)
                .setMessage("ATENÇÃO: Tem certeza que deseja excluir?")
                .setPositiveButton("Confirmar") { dialog, id ->
                    viewModel.excluir(itemPersonagem)
                }
                .setNegativeButton("CANCELAR") { dialog, id ->
                    //ignorar
                }
                .create()
                .show()
            true
        }
    }

    override fun getItemCount(): Int = personagens.size

    inner class ViewHolder(binding: FragmentItemProdutoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val txtNome: TextView = binding.txtNome
        val txtVida: TextView = binding.txtVida
        val txtForca: TextView = binding.txtForca
        val txtDefesa: TextView = binding.txtDefesa

    }

}