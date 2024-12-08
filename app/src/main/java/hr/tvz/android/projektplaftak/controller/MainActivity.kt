package hr.tvz.android.projektplaftak.controller

//Za potrebe dobivanja IP adrese web api-a koristi se ekstenzija Visual Studia (Conveyor)
//U slučaju testiranja aplikacije moguća je potrebna promjena IP adrese web api-a, koja je spremljena u res/values/strings direktoriju

import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import hr.tvz.android.projektplaftak.model.Jersey
import hr.tvz.android.projektplaftak.R
import hr.tvz.android.projektplaftak.model.RetrofitServiceInterface
import hr.tvz.android.projektplaftak.model.ServiceGenerator
import hr.tvz.android.projektplaftak.databinding.ActivityMainBinding
import hr.tvz.android.projektplaftak.view.MissingPermissionView
import hr.tvz.android.projektplaftak.view.RetrofitFailureView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), RetrofitFailureView, MissingPermissionView {

    lateinit var binding: ActivityMainBinding

    lateinit var orientation: String

    var jerseys: List<Jersey?> = listOf(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val appPromoChannel = NotificationChannel(resources.getString(R.string.appPromoChannel), resources.getString(R.string.promoNotificationChannel), NotificationManager.IMPORTANCE_DEFAULT)
        val appOrderChannel = NotificationChannel(resources.getString(R.string.appOrderChannel), resources.getString(R.string.orderNotificationChannel), NotificationManager.IMPORTANCE_DEFAULT)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(appPromoChannel)
        notificationManager.createNotificationChannel(appOrderChannel)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->

            val token = task.result

            Log.d(resources.getString(R.string.messagingToken), token)

        })

        if(binding.appName == null){
            orientation = resources.getString(R.string.landOrientation)
        }
        else{
            orientation = resources.getString(R.string.portOrientation)
        }

        val client: RetrofitServiceInterface = ServiceGenerator().createService(RetrofitServiceInterface::class.java, resources.getString(R.string.apiUrl))

        val jerseyCollect: Call<List<Jersey>> = client.collectJerseys()

        jerseyCollect.enqueue(object: Callback<List<Jersey>> {
            override fun onResponse(call: Call<List<Jersey>>, response: Response<List<Jersey>>) {
                if(response.isSuccessful){
                    jerseys = response.body()!!

                    val adapter: RecyclerView.Adapter<*> = ListAdapter(jerseys, orientation)
                    val layoutManager: RecyclerView.LayoutManager

                    if(orientation == resources.getString(R.string.portOrientation)){
                        layoutManager = LinearLayoutManager(applicationContext)
                    }
                    else{
                        layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
                    }

                    binding.recyclerView.layoutManager = layoutManager
                    binding.recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<Jersey>>, t: Throwable) {
                retrofitFailure()
            }
        })

        registerReceiver(OrderBroadcastReceiver(), IntentFilter(resources.getString(R.string.orderedJersey)), RECEIVER_EXPORTED)
        registerReceiver(WidgetProvider(), IntentFilter(resources.getString(R.string.widgetUpdate)), RECEIVER_EXPORTED)

        val handler = Handler(Looper.getMainLooper())

        handler.post(object: Runnable{
            override fun run() {
                widgetBroadcast()
                handler.postDelayed(this, 5000)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_options_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.callButton -> {

                val dialog = Dialog(this)

                dialog.setContentView(R.layout.dialog2)

                val positiveDialogButton = dialog.findViewById<Button>(R.id.positiveDialog2Button)
                val negativeDialogButton = dialog.findViewById<Button>(R.id.negativeDialog2Button)

                positiveDialogButton.setOnClickListener{

                    if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                        val intent = Intent(Intent.ACTION_CALL, Uri.parse(resources.getString(R.string.phoneNumber)))

                        startActivity(intent)
                    }
                    else{
                        missingCallPermission()
                    }

                    dialog.dismiss()

                }

                negativeDialogButton.setOnClickListener{dialog.dismiss()}

                dialog.show()

                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    fun widgetBroadcast(){
        val randomNumber = (1..15).random()

        var widgetJersey: Jersey? = null

        for(jersey in jerseys){
            if(jersey?.id == randomNumber){
                widgetJersey = jersey
            }
        }

        val intent = Intent().setAction(resources.getString(R.string.widgetUpdate))

        intent.putExtra(resources.getString(R.string.widgetJersey), widgetJersey)

        sendBroadcast(intent)
    }

    override fun retrofitFailure() {
        Toast.makeText(applicationContext, resources.getString(R.string.accessingError), Toast.LENGTH_LONG).show()
    }

    override fun missingCallPermission() {
        Toast.makeText(this, resources.getString(R.string.callPermission), Toast.LENGTH_LONG).show()
    }
}