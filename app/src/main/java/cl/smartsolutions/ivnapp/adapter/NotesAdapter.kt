package cl.smartsolutions.ivnapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cl.smartsolutions.ivnapp.R
import cl.smartsolutions.ivnapp.model.Note

class NotesAdapter(private val notes: List<Note>) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val noteTitle: TextView = itemView.findViewById(R.id.textViewNoteTitle)
        private val noteContent: TextView = itemView.findViewById(R.id.textViewNoteContent)

        fun bind(note: Note) {
            noteTitle.text = note.title
            noteContent.text = note.content
        }
    }
}
