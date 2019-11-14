package com.example.insurancecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedItem = spinnerAge.getItemAtPosition(position)
        Toast.makeText(this, "Selected Item = " + selectedItem, Toast.LENGTH_LONG).show()
    }

    /*override fun onClick(v: View?) {
        when(v) {
            buttonCalculate -> calculatePremium()
            buttonReset -> resetOutput()

            else-> //TODO something here
        }
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Handling item selected listener for the spinner
        spinnerAge.onItemSelectedListener = this
        buttonCalculate.setOnClickListener(){
            calculatePremium()
        }

        buttonReset.setOnClickListener {
            resetOutput(buttonReset)
        }

        /*val buttonThird: Button
        buttonThird.setOnClickListener(this)*/
    }

    private fun calculatePremium() {
        //Get the age group
        val age: Int = spinnerAge.selectedItemPosition

        val premium = when (age) {
            0 -> 60 //Less than 17
            1 -> 70 // 17 to 25
            2 -> 90 //26 to 30
            3 -> 120  //31 to 40
            else -> 150  //41 to 55 OR > 55
        }

        // Get the gender selection. ID of radio button
        val gender: Int = radioGroupGender.checkedRadioButtonId
        var extraPayment = 0
        if (gender == R.id.radioButtonMale) {
            extraPayment = when (age) {
                0 -> 0 //Less than 17
                1 -> 50 // 17 to 25
                2 -> 100 //26 to 30
                3 -> 150  //31 to 40
                else -> 200  //41 to 55 OR > 55
            }
        }

        val smoker: Boolean = checkBoxSmoker.isChecked
        if (smoker) {
            extraPayment += when (age) {
                0 -> 0
                1 -> 100 // 17 to 25
                2 -> 150 //26 to 30
                3 -> 200  //31 to 40
                4 -> 250//41 to 55
                else -> 300   //> 55
            }
        }

        var totalPayment = premium + extraPayment
        var currency = Currency.getInstance(Locale.getDefault()).symbol
        textViewPremium.text = getString(R.string.insurance_premium) + currency + totalPayment
    }

    fun resetOutput(view: View) {
        spinnerAge.setSelection(0)
        radioGroupGender.clearCheck()
        checkBoxSmoker.setChecked(false)
        textViewPremium.text = getString(R.string.insurance_premium)
    }
}


