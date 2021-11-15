package com.example.gorentapps

import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gorentapps.adapter.CardViewCarAdapter
import com.example.gorentapps.databinding.ActivityMainBinding
import com.example.gorentapps.model.Car
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvCars: RecyclerView

    private var list: ArrayList<Car> = arrayListOf()
    private lateinit var adapter: CardViewCarAdapter

    private lateinit var dataCarName: Array<String>
    private lateinit var dataRentalPrice: Array<String>
    private lateinit var dataPassenger: Array<String>
    private lateinit var dataImage: TypedArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        rvCars = binding.rvCars
        rvCars.setHasFixedSize(true)

        prepare()

        showRecyclerCardView()

        addItem()

        adapter.setOnItemClickListener(object : CardViewCarAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val carData = Car(
                    list[position].name,
                    list[position].rentalPrice,
                    list[position].passenger,
                    list[position].image
                )
                val intent = Intent(this@MainActivity, FormOrderActivity::class.java)
                intent.putExtra(FormOrderActivity.EXTRA_CAR, carData)
                startActivity(intent)
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                auth.signOut()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun prepare() {
        dataCarName = resources.getStringArray(R.array.car_name)
        dataPassenger = resources.getStringArray(R.array.passenger)
        dataRentalPrice = resources.getStringArray(R.array.rental_price)
        dataImage = resources.obtainTypedArray(R.array.image)
    }

    private fun showRecyclerCardView() {
        rvCars.layoutManager = LinearLayoutManager(this)
        adapter = CardViewCarAdapter(this)
        rvCars.adapter = adapter
    }

    private fun addItem() {
        for (position in dataCarName.indices) {
            val car = Car(
                dataCarName[position],
                dataRentalPrice[position],
                dataPassenger[position],
                dataImage.getResourceId(position, -1)
            )
            list.add(car)
        }
        adapter.listCars = list
    }
}