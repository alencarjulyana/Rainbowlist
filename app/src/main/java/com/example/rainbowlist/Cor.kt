package com.example.rainbowlist;

import android.graphics.Color

class Cor (private var red: Int, private var green: Int, private var blue: Int, private var nameHex: String): java.io.Serializable {
        override fun toString(): String{
                return "${this.nameHex}"
        }

        fun getName(): String{
                return this.nameHex
        }

        fun getRed(): Int{
                return this.red
        }

        fun getGreen(): Int{
                return this.green
        }

        fun getBlue(): Int{
                return this.blue
        }


        fun editColor(red: Int, green: Int, blue: Int){
                this.red = red
                this.green = green
                this.blue = blue
                val color = Color.rgb(red, green, blue)
                this.nameHex = Integer.toHexString(color)

        }

        fun getRgb(): Int{
                return Color.rgb(this.red, this.green, this.blue)
        }


}