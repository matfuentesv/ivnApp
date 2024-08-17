package cl.smartsolutions.ivnapp.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.speech.tts.TextToSpeech
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cl.smartsolutions.ivnapp.R
import cl.smartsolutions.ivnapp.adapter.NotesAdapter
import cl.smartsolutions.ivnapp.model.Note
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class NotesActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var recyclerViewNotes: RecyclerView
    private lateinit var fabAddNote: FloatingActionButton
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var vibrator: Vibrator
    private lateinit var backButton: ImageView

    private val notesList = mutableListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notes)

        backButton = findViewById(R.id.backButton)
        // Inicialización de TextToSpeech y Vibrator
        textToSpeech = TextToSpeech(this, this)
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        recyclerViewNotes = findViewById(R.id.recyclerViewNotes)
        fabAddNote = findViewById(R.id.fabAddNote)

        recyclerViewNotes.layoutManager = LinearLayoutManager(this)
        recyclerViewNotes.adapter = NotesAdapter(notesList) { note ->
            readNoteContent(note)
        }

        fabAddNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
            provideFeedback()
        }

        // Carga de notas (en un proyecto real, estas podrían cargarse desde una base de datos o API)
        loadNotes()


        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // Configura el idioma español
            val result = textToSpeech.setLanguage(Locale("es", "ES"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "El idioma no es soportado", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Error al inicializar Text to Speech", Toast.LENGTH_SHORT).show()
        }
    }


    private fun loadNotes() {

        notesList.add(Note("Saludo", "Hola, ¿cómo estás?"))
        notesList.add(Note("Pedido de ayuda", "¿Podrías ayudarme, por favor?"))
        notesList.add(Note("Pregunta por dirección", "¿Dónde está el baño?"))
        notesList.add(Note("Pedido de información", "¿Puedes escribir lo que estás diciendo?"))
        notesList.add(Note("Explicación de sordera", "Soy sordo/a, no puedo escuchar. Por favor, lee mi mensaje."))
        notesList.add(Note("Pedido de bebida", "Me gustaría un vaso de agua, por favor."))
        notesList.add(Note("Gracias", "Muchas gracias por tu ayuda."))
        notesList.add(Note("Llamar la atención", "Disculpa, ¿puedes mirarme un momento?"))
        notesList.add(Note("Comunicación alternativa", "¿Podemos comunicarnos por escrito?"))
        notesList.add(Note("Pedido de comida", "Quisiera pedir una hamburguesa sin queso, por favor."))
        notesList.add(Note("Confirmación", "Sí, entiendo."))
        notesList.add(Note("Negación", "No, no necesito ayuda, gracias."))
        notesList.add(Note("Llamada de emergencia", "Por favor, llama al 911, hay una emergencia."))
        notesList.add(Note("Despedida", "Adiós, que tengas un buen día."))
        notesList.add(Note("Pregunta por tiempo", "¿Qué hora es?"))

        recyclerViewNotes.adapter?.notifyDataSetChanged()
    }


    private fun readNoteContent(note: Note) {
        textToSpeech.speak(note.content, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    private fun provideFeedback() {
        // Vibración para notificar al usuario
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(500)
        }

        // Mensaje opcional
        Toast.makeText(this, "Nueva nota agregada", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        // Libera recursos de TextToSpeech
        if (textToSpeech != null) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }


}
