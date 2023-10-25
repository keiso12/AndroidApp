package br.edu.up.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personagens")
data class Personagem(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var nome: String,
    var descricao: String = String(),
    var vida: Int,
    var forca: Int,
    var defesa: Int,

) {
    constructor() : this(0,"","",0,0,0)
}