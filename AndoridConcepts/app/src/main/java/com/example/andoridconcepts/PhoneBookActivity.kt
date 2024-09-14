package com.example.andoridconcepts

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PhoneBookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_phone_book)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        var phonebookList = mutableListOf<PhoneBook>()

        for (i in 0..40) {
            phonebookList.add(
                PhoneBook("" + i + "번째 사람", "010-000-000" + i)
            )
        }

        val container = findViewById<LinearLayoutCompat>(R.id.container)
        val inflater = LayoutInflater.from(this@PhoneBookActivity)
        phonebookList.forEach { phoneBook ->
            val phoneBookItem = inflater.inflate(R.layout.phonebook_item, null)
            val image = phoneBookItem.findViewById<ImageView>(R.id.image)
            val name = phoneBookItem.findViewById<TextView>(R.id.name)
            val number = phoneBookItem.findViewById<TextView>(R.id.number)

            image.setImageDrawable(resources.getDrawable(R.drawable.logo_kt, this.theme))
            name.text = phoneBook.name
            number.text = phoneBook.number

            container.addView(phoneBookItem)

            phoneBookItem.setOnClickListener {
                startActivity(
                    Intent(this, PhoneBookDetailActivity::class.java).apply {

                        this.putExtra("name", phoneBook.name)
                        this.putExtra("number", phoneBook.number)
                    }
                )
            }
        }

    }
}

class PhoneBook(val name: String, val number: String)