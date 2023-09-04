package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.entity.User

class EditorActivity : AppCompatActivity() {
    private lateinit var nama: EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var saveBtn: Button
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        nama = findViewById(R.id.nama)
        email = findViewById(R.id.email)
        phone = findViewById(R.id.phone)
        saveBtn = findViewById(R.id.btn_save)

        database = AppDatabase.getInstance(applicationContext)

        var intent = intent.extras
        if (intent!=null){
            val id = intent.getInt("id")
            var user = database.userDao().get(id)

            nama.setText(user.fullName)
            email.setText(user.email)
            phone.setText(user.phone)
        }

        saveBtn.setOnClickListener {
            if (nama.text.isNotEmpty() && email.text.isNotEmpty() && phone.text.isNotEmpty()) {
                if (intent!=null){
                    //coding edti data
                    database.userDao().update(
                        User(
                            intent.getInt("id",0),
                            nama.text.toString(),
                            email.text.toString(),
                            phone.text.toString()
                        )
                    )

                }else{
                    //coding tambah data
                    database.userDao().insertAll(
                        User(
                            null,
                            nama.text.toString(),
                            email.text.toString(),
                            phone.text.toString()
                        )
                    )
                }
                finish()
            } else {
                Toast.makeText(
                    applicationContext,
                    "silahkan isi semua data dengan valid",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}