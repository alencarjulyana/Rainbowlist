package com.example.rainbowlist

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*


class CorForm : AppCompatActivity() {

    private lateinit var sbRed: SeekBar
    private lateinit var sbGreen: SeekBar
    private lateinit var sbBlue: SeekBar
    private lateinit var tvRed: TextView
    private lateinit var tvGreen: TextView
    private lateinit var tvBlue: TextView
    private lateinit var etCor: EditText
    private lateinit var llColor: LinearLayout
    private lateinit var tvColor: TextView
    private lateinit var btnEnviar: Button
    private lateinit var btnCancelar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cor_form)

        this.sbRed = findViewById(R.id.sbRed)
        this.sbGreen = findViewById(R.id.sbGreen)
        this.sbBlue = findViewById(R.id.sbBlue)
        this.tvRed = findViewById(R.id.tvRed)
        this.tvGreen = findViewById(R.id.tvGreen)
        this.tvBlue = findViewById(R.id.tvBlue)
        this.etCor = findViewById(R.id.etCor)
        this.llColor = findViewById(R.id.llColor)
        this.tvColor = findViewById(R.id.tvColor)
        this.btnEnviar = findViewById(R.id.btnEnviar)
        this.btnCancelar = findViewById(R.id.btnCancelar)

        this.llColor.setBackgroundColor(this.createColor())
        this.tvColor.setTextColor(this.invertColor())
        this.tvColor.text = this.hexColor(this.createColor())

        this.sbRed.setOnSeekBarChangeListener(OnChangeColor())
        this.sbGreen.setOnSeekBarChangeListener(OnChangeColor())
        this.sbBlue.setOnSeekBarChangeListener(OnChangeColor())

        this.btnEnviar.setOnClickListener({salvar()})
        this.btnCancelar.setOnClickListener({cancelar()})
    }

    fun createColor(): Int{
        val red = this@CorForm.sbRed.progress
        val green = this@CorForm.sbGreen.progress
        val blue = this@CorForm.sbBlue.progress
        return Color.rgb(red, green, blue)
    }

    fun invertColor(): Int{
        val red = this@CorForm.sbRed.progress
        val green = this@CorForm.sbGreen.progress
        val blue = this@CorForm.sbBlue.progress
        return Color.rgb(255 - red, 255 - green, 255 - blue)
    }

    fun hexColor(color: Int): String {
        return Integer.toHexString(color)
    }

    fun salvar(){
        val red = this@CorForm.sbRed.progress
        val green = this@CorForm.sbGreen.progress
        val blue = this@CorForm.sbBlue.progress
        val name = this@CorForm.etCor.text.toString()

        val color = Cor(red, green, blue, name)


        val intent = Intent().apply {
            putExtra("COR", color)
        }
       setResult(RESULT_OK, intent)

        finish()
    }

    fun cancelar(){
        finish()
    }

    inner class OnChangeColor: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            val red = this@CorForm.sbRed.progress
            val green = this@CorForm.sbGreen.progress
            val blue = this@CorForm.sbBlue.progress

            this@CorForm.tvRed.text = red.toString()
            this@CorForm.tvGreen.text = green.toString()
            this@CorForm.tvBlue.text = blue.toString()

            this@CorForm.llColor.setBackgroundColor(this@CorForm.createColor())

            this@CorForm.tvColor.setTextColor(this@CorForm.invertColor())
            this@CorForm.tvColor.text = this@CorForm.hexColor(this@CorForm.createColor())


        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
//            TODO("Not yet implemented")
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
//            TODO("Not yet implemented")
        }
    }
}