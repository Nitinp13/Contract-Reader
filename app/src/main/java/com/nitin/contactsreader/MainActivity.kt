package com.nitin.contactsreader

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btncontacts : Button = findViewById<Button>(R.id.btnContacts)
        btncontacts.setOnClickListener{
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CONTACTS),0)

            }

            val contacts = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null)

            val buffer = StringBuilder()

            while (contacts != null && contacts!!.moveToNext()){
                val name = contacts.getString(contacts.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
                val number = contacts.getString(contacts.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))

                buffer.append("Name : "+name+"\n")
                buffer.append("Number : "+number+"\n\n")

            }
            contacts?.close()
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Contacts")
            builder.setMessage(buffer.toString())
            builder.setCancelable(true)
            builder.show()



        }
    }


}