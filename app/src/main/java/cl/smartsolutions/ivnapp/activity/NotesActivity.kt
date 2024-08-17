package cl.smartsolutions.ivnapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cl.smartsolutions.ivnapp.R
import cl.smartsolutions.ivnapp.adapter.NotesAdapter
import cl.smartsolutions.ivnapp.model.Note
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotesActivity : AppCompatActivity() {

    private lateinit var recyclerViewNotes: RecyclerView
    private lateinit var fabAddNote: FloatingActionButton

    private val notesList = mutableListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notes)


        recyclerViewNotes = findViewById(R.id.recyclerViewNotes)
        fabAddNote = findViewById(R.id.fabAddNote)

        recyclerViewNotes.layoutManager = LinearLayoutManager(this)
        recyclerViewNotes.adapter = NotesAdapter(notesList)

        fabAddNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }

        // Carga de notas (en un proyecto real, estas podrían cargarse desde una base de datos o API)
        loadNotes()

    }

    private fun loadNotes() {
        // Aquí se pueden cargar notas de prueba o reales
        notesList.add(Note("Note 1", "This is the content of note 1"))
        notesList.add(Note("Note 2", "This is the content of note 2"))
        notesList.add(Note("Note 3", "This is the content of note 3"))
        recyclerViewNotes.adapter?.notifyDataSetChanged()
    }
}