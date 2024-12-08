package hr.tvz.android.projektplaftak.controller

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import hr.tvz.android.projektplaftak.model.Jersey
import hr.tvz.android.projektplaftak.R
import hr.tvz.android.projektplaftak.databinding.ActivityJerseyBinding
import hr.tvz.android.projektplaftak.view.MissingPermissionView

class JerseyActivity: AppCompatActivity(), MissingPermissionView {

    lateinit var binding: ActivityJerseyBinding

    lateinit var mediaPlayer: MediaPlayer

    var jersey: Jersey? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        Fresco.initialize(this)

        binding = ActivityJerseyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.jerseySize.setTextColor(resources.getColor(R.color.black))
        binding.smallJerseySize.setTextColor(resources.getColor(R.color.black))
        binding.mediumJerseySize.setTextColor(resources.getColor(R.color.black))
        binding.largeJerseySize.setTextColor(resources.getColor(R.color.black))

        jersey = intent.extras?.getParcelable(resources.getString(R.string.jersey), Jersey::class.java)

        val draweeView = findViewById<View>(R.id.jerseyImage) as SimpleDraweeView

        val imageUri = Uri.parse(jersey?.imageUrl)

        draweeView.setImageURI(imageUri)

        binding.jerseyTeam.text = jersey?.team
        binding.jerseyLeagueValue.text = jersey?.league
        binding.jerseyGenderValue.text = jersey?.gender
        binding.jerseyMaterialValue.text = jersey?.material
        binding.jerseyManufacturerValue.text = jersey?.manufacturer

        val jerseyPrice = jersey?.price.toString() + resources.getString(R.string.euroSymbol)

        binding.jerseyPriceValue.text = jerseyPrice

        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.cashregistersound)
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

    fun sizeCheckup(view: android.view.View){

        if(binding.smallJerseySize.isChecked || binding.mediumJerseySize.isChecked || binding.largeJerseySize.isChecked){

            val dialog = Dialog(this)

            dialog.setContentView(R.layout.dialog2)

            val positiveDialogButton = dialog.findViewById<Button>(R.id.positiveDialog2Button)
            val negativeDialogButton = dialog.findViewById<Button>(R.id.negativeDialog2Button)

            positiveDialogButton.setOnClickListener{

                mediaPlayer.start()

                binding.jerseySize.setTextColor(resources.getColor(R.color.black))
                binding.smallJerseySize.setTextColor(resources.getColor(R.color.black))
                binding.mediumJerseySize.setTextColor(resources.getColor(R.color.black))
                binding.largeJerseySize.setTextColor(resources.getColor(R.color.black))

                if(binding.smallJerseySize.isChecked){
                    binding.smallJerseySize.setChecked(false)
                }else if(binding.mediumJerseySize.isChecked){
                    binding.mediumJerseySize.setChecked(false)
                }
                else{
                    binding.largeJerseySize.setChecked(false)
                }

                createConfirmationDialog()

                orderBroadcast()

                dialog.dismiss()
            }

            negativeDialogButton.setOnClickListener{dialog.dismiss()}

            dialog.show()
        }
        else {
            val animation: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.animation1)

            binding.orderButton.startAnimation(animation)

            binding.jerseySize.setTextColor(resources.getColor(R.color.red))
            binding.smallJerseySize.setTextColor(resources.getColor(R.color.red))
            binding.mediumJerseySize.setTextColor(resources.getColor(R.color.red))
            binding.largeJerseySize.setTextColor(resources.getColor(R.color.red))
        }

    }

    fun createConfirmationDialog(){

        val dialog = Dialog(this)

        dialog.setContentView(R.layout.dialog1)

        val dialogMessage = dialog.findViewById<TextView>(R.id.dialog1Message)

        dialogMessage.text = resources.getString(R.string.successfulOrder) + resources.getString(R.string.exclamationSymbol)

        val dialogButton = dialog.findViewById<Button>(R.id.dialog1Button)

        dialogButton.setOnClickListener{dialog.dismiss()}

        dialog.show()
    }

    fun orderBroadcast(){

        val broadcastIntent = Intent().setAction(resources.getString(R.string.orderedJersey))

        broadcastIntent.putExtra(resources.getString(R.string.broadcastJersey), jersey)

        sendBroadcast(broadcastIntent)

    }

    override fun missingCallPermission() {
        Toast.makeText(this, resources.getString(R.string.callPermission), Toast.LENGTH_LONG).show()
    }

}