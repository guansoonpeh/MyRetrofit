package com.example.myretrofit

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myretrofit.API.RetrofitInstance
import com.example.myretrofit.Models.MyRespond
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream



class MainActivity : AppCompatActivity() {

    private var imgUri: Uri? = null
    private lateinit var img :ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        img = findViewById(R.id.imageView)

        val btnUpload:Button = findViewById(R.id.btnUpload)
        val btnBrowse:Button = findViewById(R.id.btnBrowse)

        btnUpload.setOnClickListener(){

            val bitmap = (img.getDrawable() as BitmapDrawable).bitmap
            val byteArray: ByteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray)
            val img: String = Base64.encodeToString(byteArray.toByteArray(), Base64.DEFAULT)
            val name :String = "TEST.jpg"

            val call : Call<MyRespond> = RetrofitInstance.api.UploadImage(img, name)

            call.enqueue(object : Callback<MyRespond>{
                override fun onResponse(call: Call<MyRespond>, response: Response<MyRespond>) {
                    if (response.isSuccessful()){
                        Toast.makeText(applicationContext,  response.body()!!.message, Toast.LENGTH_LONG).show()
                    }

                }

                override fun onFailure(call: Call<MyRespond>, t: Throwable) {
                    Toast.makeText(applicationContext,  t.message, Toast.LENGTH_LONG).show()
                }

            })
        }

        btnBrowse.setOnClickListener(){
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"

            launchSomeActivity.launch(intent)
        }
    }


    var launchSomeActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data

            imgUri  = data?.data
            img.setImageURI(data?.data)
        }
    }

}