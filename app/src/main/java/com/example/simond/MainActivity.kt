package com.example.simond

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.example.simond.databinding.ActivityMainBinding
import java.util.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val posiblesBotones = ArrayList<LinearLayout>()
    private val botonesPulsados = ArrayList<LinearLayout>()
    private val pilaBotones = ArrayList<LinearLayout>()

    private var playerPatternIndex = 0
    private var perder = false
    private var handler = android.os.Handler()
    private var level = 1
    private var azOscuro = Color.parseColor("#13358E")
    private var amOscuro = Color.parseColor("#A89013")
    private var vOscuro = Color.parseColor("#28751A")
    private var rOscuro = Color.parseColor("#A11919")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Inicializamos las TextViews para mostrar el nivel y el resultado


        // Initialize ImageViews y establecemos los listeners utilizando View Binding
        val btnRed = binding.btnRed
        val btnGreen = binding.btnGreen
        val btnBlue = binding.btnBlue
        val btnYellow = binding.btnYellow

        posiblesBotones.add(btnRed)
        posiblesBotones.add(btnGreen)
        posiblesBotones.add(btnBlue)
        posiblesBotones.add(btnYellow)

        val btnIniciar = binding.btnIniciar
        val btnNueva = binding.btnNueva
        val btnComprobar = binding.btnComprobar

        btnIniciar.setOnClickListener {
            if (!perder) {
                startSimonSaysGame()
            }
        }

        btnComprobar.setOnClickListener{
            for ((i, boton) in pilaBotones.withIndex()){
                /*if(boton != botonesPulsados.get(i)){
                    perder = true
                }
            }
            if(!perder){
                level++
                generateSimonPattern()
                displaySimonPattern()
            }else{
                Toast.makeText(this, "HAS PERDIDO",Toast.LENGTH_SHORT)
                resetSimonSaysGame()*/
                if (perder) {
                            handlePlayerInput(boton)
                        }else{
                    Toast.makeText(this, "HAS PERDIDO",Toast.LENGTH_SHORT)
                    resetSimonSaysGame()
                        }
            }


        }

        btnNueva.setOnClickListener {
            resetSimonSaysGame()
            binding.btnIniciar.visibility = View.VISIBLE
        }

        for (i in posiblesBotones) {

            i.setOnTouchListener { view, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        when (i) {
                            btnRed -> {
                                i.setBackgroundColor(Color.RED)

                            }

                            btnBlue -> {
                                i.setBackgroundColor(Color.BLUE)

                            }

                            btnGreen -> {
                                i.setBackgroundColor(Color.GREEN)

                            }

                            btnYellow -> {

                                i.setBackgroundColor(Color.YELLOW)


                            }
                        }
                        botonesPulsados.add((i))
                        true // Devuelve 'true' para indicar que has manejado el evento
                    }
                    MotionEvent.ACTION_UP -> {
                        when (i) {
                            btnRed -> {
                                i.setBackgroundColor(rOscuro)
                            }

                            btnBlue -> {
                                i.setBackgroundColor(azOscuro)
                            }

                            btnGreen -> {
                                i.setBackgroundColor(vOscuro)
                            }

                            btnYellow -> {
                                i.setBackgroundColor(amOscuro)
                            }

                        }
                        //aquí va
                        true // Devuelve 'true' para indicar que has manejado el evento
                    }
                    else -> {

                        false // Para otros eventos táctiles, devuelve 'false'
                    }
                }

            }

           /*i.setOnClickListener {
                when (i) {
                    btnRed -> {


                        handler.postDelayed({
                            i.setBackgroundColor(Color.RED)

                        }, 50)
                        i.setBackgroundColor(rOscuro)
                    }

                    btnBlue -> {


                        handler.postDelayed({
                            i.setBackgroundColor(Color.BLUE)
                        }, 5)
                        i.setBackgroundColor(azOscuro)

                    }

                    btnGreen -> {


                            handler.postDelayed({
                                i.setBackgroundColor(Color.GREEN)
                            }, 5)
                        i.setBackgroundColor(vOscuro)


                    }

                    btnYellow -> {
                        handler.postDelayed({
                            i.setBackgroundColor(Color.YELLOW)

                        }, 5)
                        i.setBackgroundColor(amOscuro)
                    }
                }
                if (perder) {
                    handlePlayerInput(i)
                }
            }*/

        }
    }

    private fun startSimonSaysGame() {
        perder = true
        binding.btnIniciar.visibility = View.GONE
        generateSimonPattern()
        displaySimonPattern()
        binding.txtNivel.text = "Nivel $level"
    }

    private fun resetSimonSaysGame() {
        perder = false
        binding.btnNueva.visibility = View.VISIBLE
        pilaBotones.clear()
        playerPatternIndex = 0
        binding.txtResultado.setText("")// Limpiamos el resultado
        level = 1 // Reiniciamos el nivel
    }

    private fun generateSimonPattern() {
        val random = Random()
        val Index = random.nextInt(posiblesBotones.size)
        val panel = posiblesBotones[Index]
        pilaBotones.add(panel)
    }

    private fun displaySimonPattern() {
        for (i in pilaBotones.indices) {
            val panel = pilaBotones[i]
            val delayMillis = (i + 1) * 1000L
            handler.postDelayed({
                when (panel) {
                    binding.btnBlue -> {
                        panel.setBackgroundColor(azOscuro)
                        handler.postDelayed({
                            panel.setBackgroundColor(Color.BLUE)
                            handler.postDelayed({
                                panel.setBackgroundColor(azOscuro)
                            }, 500)
                        }, 500)
                    }

                    binding.btnGreen -> {
                        panel.setBackgroundColor(vOscuro)
                        handler.postDelayed({
                            panel.setBackgroundColor(Color.GREEN)
                            handler.postDelayed({
                                panel.setBackgroundColor(vOscuro)
                            }, 500)
                        }, 500)
                    }

                    binding.btnRed -> {
                        panel.setBackgroundColor(rOscuro)
                        handler.postDelayed({
                            panel.setBackgroundColor(Color.RED)
                            handler.postDelayed({
                                panel.setBackgroundColor(rOscuro)
                            }, 500)
                        }, 500)
                    }

                    binding.btnYellow -> {
                        panel.setBackgroundColor(amOscuro)

                        handler.postDelayed({
                            panel.setBackgroundColor(Color.YELLOW)
                            handler.postDelayed({
                                panel.setBackgroundColor(amOscuro)
                            }, 500)
                        }, 500)
                    }
                }
            }, delayMillis)
        }
    }

    private fun anadirSecuencia(){

    }
    private fun handlePlayerInput(boton: LinearLayout) {
        val expectedButton = pilaBotones[playerPatternIndex]

        if (boton == expectedButton) {
            playerPatternIndex++

            if (playerPatternIndex == pilaBotones.size) {
                playerPatternIndex = 0
                generateSimonPattern()
                level++ // Incrementamos el nivel
                binding.txtNivel.text = "$level" // Actualizamos el nivel en la vista
                if (level == 7) {
                    // Has ganado el juego
                    perder = false
                    binding.txtResultado.setText("Has ganado el juego enhorabuena")
                } else {
                    // Mostrar la secuencia nuevamente para el próximo nivel
                    displaySimonPattern()
                }
            }
        } else {
            // Has fallado
            binding.txtResultado.setText("Has fallado")
            resetSimonSaysGame()
        }
    }
}