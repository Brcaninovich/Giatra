package com.recoo.giatra

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.app.ActivityCompat
import be.tarsos.dsp.AudioProcessor
import be.tarsos.dsp.io.android.AudioDispatcherFactory
import be.tarsos.dsp.pitch.PitchDetectionHandler
import be.tarsos.dsp.pitch.PitchProcessor



var odabranaZica: Int = 0
var defaultZica: Int = 0
class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        actionBar?.hide()


        val switchBar = findViewById<SwitchCompat>(R.id.switch2)
            switchBar.isChecked = true


        //BUTONS
        val zica1 = findViewById<Button>(R.id.ZicaPrva)
        val zica2 = findViewById<Button>(R.id.ZicaDruga)
        val zica3 = findViewById<Button>(R.id.ZicaTreca)
        val zica4 = findViewById<Button>(R.id.ZicaCetvrta)
        val zica5 = findViewById<Button>(R.id.ZicaPeta)
        val zica6 = findViewById<Button>(R.id.ZicaSesta)

        zica1.setOnClickListener{
            odabranaZica = 1
            klikBtn(zica1, switchBar)
        }

        zica2.setOnClickListener{
            odabranaZica = 2
            klikBtn(zica2, switchBar)
        }

        zica3.setOnClickListener{
            odabranaZica = 3
            klikBtn(zica3, switchBar)
        }

        zica4.setOnClickListener{
            odabranaZica = 4
            klikBtn(zica4, switchBar)
        }

        zica5.setOnClickListener{
            odabranaZica = 5
            klikBtn(zica5, switchBar)
        }

        zica6.setOnClickListener{
            odabranaZica = 6
            klikBtn(zica6, switchBar)
        }

        //KRAJ BUTTONA

        val dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0)

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 111)
        }else {
            val pdh = PitchDetectionHandler { res, e ->
                val pitchInHz = res.pitch

                // REALTIME TUNER
                runOnUiThread { switchKonts(pitchInHz) }
            }

            val pitchProcessor: AudioProcessor =
                PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050f, 1024, pdh)
            dispatcher.addAudioProcessor(pitchProcessor)

            val audioThread = Thread(dispatcher, "Audio Thread")
            audioThread.start()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    public fun klikBtn(btn: Button, swt: SwitchCompat){
        if(swt.isChecked == true){
            swt.isChecked = false
        }
        val zica1 = findViewById<Button>(R.id.ZicaPrva)
        val zica2 = findViewById<Button>(R.id.ZicaDruga)
        val zica3 = findViewById<Button>(R.id.ZicaTreca)
        val zica4 = findViewById<Button>(R.id.ZicaCetvrta)
        val zica5 = findViewById<Button>(R.id.ZicaPeta)
        val zica6 = findViewById<Button>(R.id.ZicaSesta)
        vratiButone()

        btn.backgroundTintList = getColorStateList(android.R.color.holo_blue_bright)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    public fun vratiButone(){
        val zica1 = findViewById<Button>(R.id.ZicaPrva)
        val zica2 = findViewById<Button>(R.id.ZicaDruga)
        val zica3 = findViewById<Button>(R.id.ZicaTreca)
        val zica4 = findViewById<Button>(R.id.ZicaCetvrta)
        val zica5 = findViewById<Button>(R.id.ZicaPeta)
        val zica6 = findViewById<Button>(R.id.ZicaSesta)

        zica1.backgroundTintList = getColorStateList(android.R.color.white)
        zica2.backgroundTintList = getColorStateList(android.R.color.white)
        zica3.backgroundTintList = getColorStateList(android.R.color.white)
        zica4.backgroundTintList = getColorStateList(android.R.color.white)
        zica5.backgroundTintList = getColorStateList(android.R.color.white)
        zica6.backgroundTintList = getColorStateList(android.R.color.white)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    public fun switchKonts(pitchInHz: Float){
        val switchBar = findViewById<SwitchCompat>(R.id.switch2)
        if (switchBar.isChecked){
            processPitch(pitchInHz, switchBar)
        }else if(!switchBar.isChecked){
            processPitchManual(pitchInHz)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    public fun processPitchManual(pitchInHz: Float){

        val pitchText = findViewById<TextView>(R.id.frekvencija)

        val zica1 = findViewById<Button>(R.id.ZicaPrva)
        val zica2 = findViewById<Button>(R.id.ZicaDruga)
        val zica3 = findViewById<Button>(R.id.ZicaTreca)
        val zica4 = findViewById<Button>(R.id.ZicaCetvrta)
        val zica5 = findViewById<Button>(R.id.ZicaPeta)
        val zica6 = findViewById<Button>(R.id.ZicaSesta)

        var centar:Int = 0
        val noteText = findViewById<TextView>(R.id.nota)

        val progresMninus = findViewById<ProgressBar>(R.id.progressBarMinus)
        val progresPlus = findViewById<ProgressBar>(R.id.progressBarPlus)

        val stimerTxt = findViewById<TextView>(R.id.stimerText)

        progresMninus.progress = pitchInHz.toInt()
        progresPlus.progress = pitchInHz.toInt()

        if(defaultZica == 1){
            defaultZica = 0
            odabranaZica = 1
        }
        pitchText.setText("" + pitchInHz.toInt())
        when(odabranaZica){
            1 ->{
                    //E
                    vratiButone()
                    zica1.backgroundTintList = getColorStateList(android.R.color.holo_blue_bright)
                    centar = 82
                    progresMninus.min = 0
                    progresMninus.max = centar
                    progresPlus.min = centar
                    progresPlus.max = 100
                    if (pitchInHz > centar){
                        progresMninus.progress = progresMninus.min
                    }
                    noteText.setText("E");
                    if(pitchInHz.toInt() < centar){
                        stimerTxt.setText("Low")
                    }else if(pitchInHz.toInt() > centar){
                        stimerTxt.setText("High")
                    }
                    else{
                        stimerTxt.setText("Perfect")
                    }
            }
            2 ->{
                    //A
                    vratiButone()
                    zica2.backgroundTintList = getColorStateList(android.R.color.holo_blue_bright)
                    centar = 110
                    progresMninus.min = 0
                    progresMninus.max = centar
                    progresPlus.min = centar
                    progresPlus.max = 200
                    if (pitchInHz > centar){
                        progresMninus.progress = progresMninus.min
                    }
                    noteText.setText("A");
                    if(pitchInHz.toInt() < centar){
                        stimerTxt.setText("Low")
                    }else if(pitchInHz.toInt() > centar){
                        stimerTxt.setText("High")
                    }
                    else{
                        stimerTxt.setText("Perfect")
                    }
            }
            3 ->{
                    //D
                vratiButone()
                zica3.backgroundTintList = getColorStateList(android.R.color.holo_blue_bright)
                    centar = 146
                    progresMninus.min = 0
                    progresMninus.max = centar
                    progresPlus.min = centar
                    progresPlus.max = 200
                    if (pitchInHz > centar){
                        progresMninus.progress = progresMninus.min
                    }
                    noteText.setText("D");
                    if(pitchInHz.toInt() < centar){
                        stimerTxt.setText("Low")
                    }else if(pitchInHz.toInt() > centar){
                        stimerTxt.setText("High")
                    }
                    else{
                        stimerTxt.setText("Perfect")
                    }
            }
            4 ->{
                    //G
                vratiButone()
                zica4.backgroundTintList = getColorStateList(android.R.color.holo_blue_bright)
                    centar = 196
                    progresMninus.min = 0
                    progresMninus.max = centar
                    progresPlus.min = centar
                    progresPlus.max = 300
                    if (pitchInHz > centar){
                        progresMninus.progress = progresMninus.min
                    }
                    noteText.setText("G");
                    if(pitchInHz.toInt() < centar){
                        stimerTxt.setText("Low")
                    }else if(pitchInHz.toInt() > centar){
                        stimerTxt.setText("High")
                    }
                    else{
                        stimerTxt.setText("Perfect")
                    }
            }
            5 ->{
                    //B
                vratiButone()
                zica5.backgroundTintList = getColorStateList(android.R.color.holo_blue_bright)
                    centar = 246
                    progresMninus.min = 0
                    progresMninus.max = centar
                    progresPlus.min = centar
                    progresPlus.max = 300
                    if (pitchInHz > centar){
                        progresMninus.progress = progresMninus.min
                    }
                    noteText.setText("B");
                    if(pitchInHz.toInt() < centar){
                        stimerTxt.setText("Low")
                    }else if(pitchInHz.toInt() > centar){
                        stimerTxt.setText("High")
                    }
                    else{
                        stimerTxt.setText("Perfect")
                    }
            }
            6 ->{
                    //B
                vratiButone()
                zica6.backgroundTintList = getColorStateList(android.R.color.holo_blue_bright)
                    centar = 329
                    progresMninus.min = 0
                    progresMninus.max = centar
                    progresPlus.min = centar
                    progresPlus.max = 400
                    if (pitchInHz > centar){
                        progresMninus.progress = progresMninus.min
                    }
                    noteText.setText("B");
                    if(pitchInHz.toInt() < centar){
                        stimerTxt.setText("Low")
                    }else if(pitchInHz.toInt() > centar){
                        stimerTxt.setText("High")
                    }
                    else{
                        stimerTxt.setText("Perfect")
                    }
            }
            else ->{
                Toast.makeText(this, "Pogreska", Toast.LENGTH_SHORT)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    public fun processPitch(pitchInHz: Float, swt: SwitchCompat) {

        val textMolbe = findViewById<TextView>(R.id.Molba)
        val zica1 = findViewById<Button>(R.id.ZicaPrva)
        val zica2 = findViewById<Button>(R.id.ZicaDruga)
        val zica3 = findViewById<Button>(R.id.ZicaTreca)
        val zica4 = findViewById<Button>(R.id.ZicaCetvrta)
        val zica5 = findViewById<Button>(R.id.ZicaPeta)
        val zica6 = findViewById<Button>(R.id.ZicaSesta)



        val pitchText = findViewById<TextView>(R.id.frekvencija)
        val noteText = findViewById<TextView>(R.id.nota)
        val stimerTxt = findViewById<TextView>(R.id.stimerText)
        val progresMninus = findViewById<ProgressBar>(R.id.progressBarMinus)
        val progresPlus = findViewById<ProgressBar>(R.id.progressBarPlus)

        progresMninus.progress = pitchInHz.toInt()
        progresPlus.progress = pitchInHz.toInt()
        var centar: Int = 0
        pitchText.setText("" + pitchInHz.toInt())

        //VRACANJE BUTTONA NA DEFAULT BOJU
        if(swt.isChecked){
            defaultZica = 1
            vratiButone()
            textMolbe.setText("")
        }


        if(swt.isChecked == true){  //IF USLOV ZA SWIC
        if(pitchInHz >= 0 && pitchInHz < 100.00) {
            //A
            centar = 82
            /*metar.minValue = centar.toDouble() - 10
            metar.maxValue = centar.toDouble() + 10
            metar.value = pitchInHz.toDouble()*/



            progresMninus.min = 0
            progresMninus.max = centar
            progresPlus.min = centar
            progresPlus.max = 100
            if (pitchInHz > centar){
                progresMninus.progress = progresMninus.min
            }
            noteText.setText("E");
            if ( pitchInHz >= 82 && pitchInHz < 83){
                stimerTxt.setText("Ustimano")
            }else stimerTxt.setText("Nije Ustimano")
        }

        else if(pitchInHz >= 100 && pitchInHz < 130) {
            //B
            centar = 110

            progresMninus.min = 100
            progresMninus.max = centar
            progresPlus.min = centar
            progresPlus.max = 130
            if (pitchInHz > centar){
                progresMninus.progress = progresMninus.min
            }

            noteText.setText("A");
            if ( pitchInHz >= 109 && pitchInHz < 111){
                stimerTxt.setText("Ustimano")
            }else stimerTxt.setText("Nije Ustimano")
        }

        else if(pitchInHz >= 130 && pitchInHz < 180) {
            //C
            centar = 146

            progresMninus.min = 130
            progresMninus.max = centar
            progresPlus.min = centar
            progresPlus.max = 180
            if (pitchInHz > centar){
                progresMninus.progress = progresMninus.min
            }

            noteText.setText("D");
            if ( pitchInHz >= 145 && pitchInHz < 147){
                stimerTxt.setText("Ustimano")
            }else stimerTxt.setText("Nije Ustimano")
        }

        else if(pitchInHz >= 180 && pitchInHz < 220) {
            //D
            centar = 196
            progresMninus.min = 180
            progresMninus.max = centar
            progresPlus.min = centar
            progresPlus.max = 220
            if (pitchInHz > centar){
                progresMninus.progress = progresMninus.min
            }

            noteText.setText("G");
            if ( pitchInHz >= 195 && pitchInHz < 197){
                stimerTxt.setText("Ustimano")
            }else stimerTxt.setText("Nije Ustimano")
        }

        else if(pitchInHz >= 220 && pitchInHz <= 280) {
            //E
            centar = 246

            progresMninus.min = 220
            progresMninus.max = centar
            progresPlus.min = centar
            progresPlus.max = 280
            if (pitchInHz > centar){
                progresMninus.progress = progresMninus.min
            }

            noteText.setText("B");
            if ( pitchInHz >= 245 && pitchInHz < 247){
                stimerTxt.setText("Ustimano")
            }else stimerTxt.setText("Nije Ustimano")
        }

        else if(pitchInHz >= 280 && pitchInHz < 400) {
            //F
            centar = 329
            progresMninus.min = 280
            progresMninus.max = centar
            progresPlus.min = centar
            progresPlus.max = 400
            if (pitchInHz > centar){
                progresMninus.progress = progresMninus.min
            }

            noteText.setText("E");
            if ( pitchInHz >= 328 && pitchInHz < 330){
                stimerTxt.setText("Ustimano")
            }else stimerTxt.setText("Nije Ustimano")
        }
        }else{ //OD USLOVA SWICA GORE IZNAD

        }
    }
}