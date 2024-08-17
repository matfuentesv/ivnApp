package cl.smartsolutions.ivnapp.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.speech.tts.TextToSpeech
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

    private val notesList = mutableListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notes)

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
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.language = Locale.getDefault()
        } else {
            Toast.makeText(this, "Error al inicializar Text to Speech", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadNotes() {
        // Aquí se pueden cargar notas de prueba o reales
        notesList.add(Note("Note 1", "This is the content of note 1"))
        notesList.add(Note("Note 2", "This is the content of note 2"))
        notesList.add(Note("Note 3", "This is the content of note 3"))
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
