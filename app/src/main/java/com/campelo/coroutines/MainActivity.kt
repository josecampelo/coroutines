package com.campelo.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.campelo.coroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnExecutar.setOnClickListener {
            job = CoroutineScope(Dispatchers.IO).launch {
                withTimeout(7000L){
                    executar()
                }
            }
        }

        binding.btnEncerrar.setOnClickListener {
            job?.cancel()
            botaoTrue()
        }
    }

    private suspend fun executar(){
        repeat(10){i ->
            withContext(Dispatchers.Main){
                binding.btnExecutar.text = "Executando T:${Thread.currentThread().name} i:$i"
                binding.btnExecutar.isEnabled = false
            }

            delay(1000)
        }

        withContext(Dispatchers.Main){
            botaoTrue()
        }
    }

    private fun botaoTrue(){
        binding.btnExecutar.text = "Executar"
        binding.btnExecutar.isEnabled = true
    }
}