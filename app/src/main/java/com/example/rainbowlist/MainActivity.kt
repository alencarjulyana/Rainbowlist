package com.example.rainbowlist

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    private lateinit var rvNames: RecyclerView
    private lateinit var fabAdd: FloatingActionButton
    private var cores = mutableListOf<Cor>()


    init {
        this.cores = mutableListOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.rvNames = findViewById(R.id.rvNames)
        this.fabAdd = findViewById(R.id.fabAdd)

        val adapter = MyAdapter(this.cores)
        adapter.onItemClick = OnItemClick()
        this.rvNames.adapter = adapter


        var formResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK){
                val color = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.data?.getSerializableExtra("COLOR", Cor::class.java)
                } else {
                    it.data?.getSerializableExtra("COLOR")
                } as Cor
                (this.rvNames.adapter as MyAdapter).add(color)

                Toast.makeText(this, "Cadastrada!", Toast.LENGTH_SHORT).show()
                (this.rvNames as MyAdapter).add(color)

            }
        }
        this.fabAdd.setOnClickListener({
            val intent = Intent()
            intent.action = "NOVACOR"
            formResult.launch(intent)
        })

    }

    inner class OnItemClick: OnItemClickRecyclerView {
        override fun onItemClick(position: Int) {
            val view =
                LayoutInflater.from(this@MainActivity).inflate(R.layout.activity_cor_form, null)
            val sbRed = view.findViewById<SeekBar>(R.id.sbRed)
            val sbGreen = view.findViewById<SeekBar>(R.id.sbGreen)
            val sbBlue = view.findViewById<SeekBar>(R.id.sbBlue)


            val colorSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    val redValue = sbRed.progress
                    val greenValue = sbGreen.progress
                    val blueValue = sbBlue.progress
                    val color = Color.rgb(redValue, greenValue, blueValue)

                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                    // Não é necessário fazer nada aqui
                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    // Não é necessário fazer nada aqui
                }
            }

            sbRed.setOnSeekBarChangeListener(colorSeekBarChangeListener)
            sbGreen.setOnSeekBarChangeListener(colorSeekBarChangeListener)
            sbBlue.setOnSeekBarChangeListener(colorSeekBarChangeListener)
        }

        inner class OnSwipe : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.DOWN or ItemTouchHelper.UP,
            ItemTouchHelper.START or ItemTouchHelper.END
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                (this@MainActivity.rvNames.adapter as MyAdapter).mov(
                    viewHolder.adapterPosition,
                    target.adapterPosition
                )
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               val position = viewHolder.adapterPosition

                    if (direction == ItemTouchHelper.END){
                        run {
                            AlertDialog.Builder(this@MainActivity)
                                .setTitle("Excluir item")
                                .setMessage("Tem certeza que deseja excluir esta cor?")
                                .setPositiveButton("Sim") { dialog, _ ->
                                    (this@MainActivity.rvNames.adapter as MyAdapter).del(position)
                                    dialog.dismiss()
                                }
                                .setNegativeButton("Não") { dialog, _ ->
                                    (this@MainActivity.rvNames.adapter as MyAdapter)
                                    dialog.dismiss()
                                }
                                .create()
                                .show()
                        }
                }

            }
        }

        }
    }
