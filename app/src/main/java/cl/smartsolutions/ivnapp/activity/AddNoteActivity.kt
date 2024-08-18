package cl.smartsolutions.ivnapp.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import cl.smartsolutions.ivnapp.R
import cl.smartsolutions.ivnapp.model.Note

class AddNoteActivity : AppCompatActivity() {

    private lateinit var etNoteTitle: EditText
    private lateinit var etNoteContent: EditText
    private lateinit var btnSaveNote: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        etNoteTitle = findViewById(R.id.etNoteTitle)
        etNoteContent = findViewById(R.id.etNoteContent)
        btnSaveNote = findViewById(R.id.btnSaveNote)

        btnSaveNote.setOnClickListener {
            val title = etNoteTitle.text.toString()
            val content = etNoteContent.text.toString()

            if (title.isNotEmpty() && content.isNotEmpty()) {
                val note = Note(title, content)
                val resultIntent = Intent()
                resultIntent.putExtra("note", note)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}
