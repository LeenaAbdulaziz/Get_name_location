package com.example.get_name_location

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
   lateinit var name_ed:EditText
   lateinit var location_ed:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val name = findViewById<View>(R.id.edname) as EditText
        val location = findViewById<View>(R.id.edlocation) as EditText
         name_ed = findViewById<View>(R.id.edname_edited) as EditText
         location_ed = findViewById<View>(R.id.tvlocation) as TextView
        val savebtn = findViewById<View>(R.id.button) as Button
        val getbtn = findViewById<View>(R.id.button2) as Button



        savebtn.setOnClickListener {

            var f = Users(name.text.toString(), location.text.toString())
            Log.e("sfdsdsdgfd", "onCreate: "+name.text.toString()+ location.text.toString(), )

            addSingleuser(f, onResult = {
                name.setText("")
                location.setText("")
                Toast.makeText(applicationContext, "Save Success!", Toast.LENGTH_SHORT).show();
            })
        }
        name.setText("df")
        location.setText("sdg")
        savebtn.performClick()
        getbtn.setOnClickListener {

            var f = Users(name.text.toString(), location.text.toString())

            updateuser(f, onResult = {

                location_ed.setText("")
            })
        }
    }

    private fun addSingleuser(f: Users, onResult: () -> Unit) {

        val apiInterface = APIClient().getClient()?.create(APIinterface::class.java)

        var st =""
        apiInterface?.addUser(f)?.enqueue(object : Callback<Users> {
            override fun onResponse(
                call: Call<Users>,
                response: Response<Users>
            ) {

                onResult()
                Toast.makeText(applicationContext, "onresult"+response.code(), Toast.LENGTH_SHORT).show();
                Log.e("TAGqew", "onResponse: "+response.code()+"\n"+response.errorBody()+""+response.message() )

            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                onResult()
                Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show();


            }
        })
    }

    private fun updateuser(f: Users, onResult: () -> Unit) {

        val apiInterface = APIClient().getClient()?.create(APIinterface::class.java)

        if (apiInterface != null) {
            apiInterface.getUser().enqueue(object : Callback<Array<Users>> {
                override fun onResponse(
                    call: Call<Array<Users>>,
                    response: Response<Array<Users>>
                ) {

                 var namefound=false
                    for(name in response.body()!!){
                        if(name.name==name_ed.text.toString()) {
                            location_ed.text = name.location
                            namefound = true
                        }
                    }
                    if(!namefound){
                        location_ed.text=""
                        Toast.makeText(applicationContext,"name does not found",Toast.LENGTH_SHORT).show()
                    }
                }


                override fun onFailure(call: Call<Array<Users>>, t: Throwable) {
                    onResult()
                    Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show();


                }
            })
        }
    }

}
