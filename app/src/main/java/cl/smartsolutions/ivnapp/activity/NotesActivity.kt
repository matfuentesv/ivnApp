package cl.smartsolutions.ivnapp.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cl.smartsolutions.ivnapp.R
import cl.smartsolutions.ivnapp.adapter.NotesAdapter
import cl.smartsolutions.ivnapp.model.Note
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import java.util.*

class NotesActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var recyclerViewNotes: RecyclerView
    private lateinit var fabAddNote: FloatingActionButton
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var vibrator: Vibrator
    private val notesList = mutableListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        // Configurar el Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Configurar el DrawerLayout y NavigationView
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Configurar el Text-to-Speech y Vibrator
        textToSpeech = TextToSpeech(this, this)
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // Configurar el RecyclerView
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes)
        recyclerViewNotes.layoutManager = LinearLayoutManager(this)
        recyclerViewNotes.adapter = NotesAdapter(notesList) { note ->
            readNoteContent(note)
        }

        // Configurar el FloatingActionButton
        fabAddNote = findViewById(R.id.fabAddNote)
        fabAddNote.setOnClickListener {
            // Acción al hacer clic en el botón para agregar una nueva nota
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivityForResult(intent, 1)
        }

        // Cargar notas de ejemplo
        loadNotes()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            data?.let {
                val note = it.getSerializableExtra("note") as Note
                notesList.add(note)
                recyclerViewNotes.adapter?.notifyDataSetChanged()
                provideFeedback() // Proporcionar retroalimentación cuando se agrega una nueva nota
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.language = Locale("es", "ES")
            textToSpeech.setPitch(0.9f)
            textToSpeech.setSpeechRate(0.9f)

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
        notesList.add(Note("Llamada de emergencia", "Por favor, llama al 133, hay una emergencia."))
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

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }
}
