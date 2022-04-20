package au.edu.utas.daih.strokerehabllitation

import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import au.edu.utas.daih.strokerehabllitation.databinding.ActivityMainBinding
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.concurrent.timerTask


class MainActivity : AppCompatActivity() {

    private lateinit var ui: ActivityMainBinding
    //existed button coordinates stack
    private var btnStack = mutableListOf<Coordinate>()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)
        //var and val
        var nextClickNumber = 1
        var attempt = PrescribedGameClass()
        //settings
        var totalRound = 2
        var currentRound = 1
        var btnNumber = 3
        var totalTime = 60
        var currentTime = 60
        var timing = true
        ui.nextNumberText.text = nextClickNumber.toString()
        //pile of local functions
        //fun: random place buttons
        fun randomButtons(){
            val position1 = getAnUsefulCoordinate()
            val position2 = getAnUsefulCoordinate()
            val position3 = getAnUsefulCoordinate()
            val position4 = getAnUsefulCoordinate()
            val position5 = getAnUsefulCoordinate()
            ui.clickBtn1.x = position1.axisX.toFloat()
            ui.clickBtn1.y = position1.axisY.toFloat()
            ui.clickBtn2.x = position2.axisX.toFloat()
            ui.clickBtn2.y = position2.axisY.toFloat()
            ui.clickBtn3.x = position3.axisX.toFloat()
            ui.clickBtn3.y = position3.axisY.toFloat()
            ui.clickBtn4.x = position4.axisX.toFloat()
            ui.clickBtn4.y = position4.axisY.toFloat()
            ui.clickBtn5.x = position5.axisX.toFloat()
            ui.clickBtn5.y = position5.axisY.toFloat()
            if (btnNumber < 5){
                ui.clickBtn5.visibility = View.INVISIBLE
            }
            if (btnNumber < 4){
                ui.clickBtn4.visibility = View.INVISIBLE
            }
            btnStack.clear()
        }
        //fun: change "round 0/0" text
        fun changeRoundText(){
            ui.roundStateText.text = "$currentRound / $totalRound"
        }
        //fun: change "time(s): 0/0" text
        fun changeTimeText(){
            ui.timeStateText.text = "$currentTime / $totalTime"
            if (timing){
                currentTime -= 1
            } else {

            }
        }
        // timer test
        Timer().scheduleAtFixedRate(
            timerTask {
                runOnUiThread{
                    changeTimeText()
                }
            },2,1000)
        randomButtons()
        changeRoundText()
        //highlight will-be-clicked button
        ui.clickBtn1.textSize = 55F
        //back button
        ui.BackFrom1GameBtn.setOnClickListener {
            randomButtons()
        }
        //history button
        ui.historyBtn.setOnClickListener {

        }
        //set button
        ui.setBtn.setOnClickListener {

        }
        //click event area
        ui.clickBtn1.setOnClickListener {
            val attemptDate = LocalDate.now(ZoneId.of("AET"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            val attemptTime = LocalTime.now(ZoneId.of("AET"))
//                    .format(DateTimeFormatter.ofPattern("HH:mm:ss"))
            attempt.attemptDate = attemptDate
            attempt.btnOrderArray?.add(1)
            attempt.btnTimeArray?.add(attemptTime.toString())
            if (nextClickNumber == 1){
                nextClickNumber = 2
                attempt.btnCorrectArray?.add(true)
                ui.clickBtn1.textSize = 30F
                ui.clickBtn2.textSize = 55F
                ui.nextNumberText.text = "2"
            }
            else{
                attempt.btnCorrectArray?.add(false)
                val alert = AlertDialog.Builder(this)
                alert.setTitle("Wrong Click!")
                alert.setMessage("You clicked button ${ui.clickBtn1.text}, but it's wrong. Please keep clicking button $nextClickNumber")
                alert.show()
            }
        }
        ui.clickBtn2.setOnClickListener {
            val attemptDate = LocalDate.now(ZoneId.of("AET"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            val attemptTime = LocalTime.now(ZoneId.of("AET"))
//                    .format(DateTimeFormatter.ofPattern("HH:mm:ss"))
            attempt.attemptDate = attemptDate
            attempt.btnOrderArray?.add(2)
            attempt.btnTimeArray?.add(attemptTime.toString())
            if (nextClickNumber == 2){
                nextClickNumber = 3
                attempt.btnCorrectArray?.add(true)
                ui.clickBtn2.textSize = 30F
                ui.clickBtn3.textSize = 55F
                ui.nextNumberText.text = "3"
            }
            else{
                attempt.btnCorrectArray?.add(false)
                val alert = AlertDialog.Builder(this)
                alert.setTitle("Wrong Click!")
                alert.setMessage("You clicked button ${ui.clickBtn2.text}, but it's wrong. Please keep clicking button $nextClickNumber")
                alert.show()
            }
        }
        ui.clickBtn3.setOnClickListener {
            val attemptDate = LocalDate.now(ZoneId.of("AET"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            val attemptTime = LocalTime.now(ZoneId.of("AET"))
//                    .format(DateTimeFormatter.ofPattern("HH:mm:ss"))
            attempt.attemptDate = attemptDate
            attempt.btnOrderArray?.add(3)
            attempt.btnTimeArray?.add(attemptTime.toString())
            //if it's correct click
            if (nextClickNumber == 3){
                attempt.btnCorrectArray?.add(true)
                ui.clickBtn3.textSize = 30F
                //if more than 3 buttons
                if (btnNumber > 3){
                    nextClickNumber = 4
                    ui.clickBtn4.textSize = 55F
                    ui.nextNumberText.text = "4"
                }
                // if only three buttons
                else{
                    // if it is final round
                    if (currentRound == totalRound ){
                        currentRound = 1
                        val alert = AlertDialog.Builder(this)
                        alert.setTitle("Congratulations!")
                            .setMessage("You've completed all rounds!")
                            // TODO: give 3 buttons for users, go to history page,  go to next same-setting-round, or/and change settings
                            .show()
                    }
                    // if it is not final round
                    else{
                        val alert = AlertDialog.Builder(this)
                        alert.setTitle("Congratulations!")
                            .setMessage("you've completed $currentRound round(s)! Click OK start the next round.")
                            .setPositiveButton("OOK") { _: DialogInterface, _: Int
                                ->
                                currentRound += 1
                                nextClickNumber = 1
                                ui.nextNumberText.text = "1"
                                ui.clickBtn3.textSize = 30F
                                ui.clickBtn1.textSize = 55F
                                changeRoundText()
                            }
                            .setCancelable(false)
                            .show()
                    }
                }
            }
            //if this click(button 3) is wrong
            else{
                attempt.btnCorrectArray?.add(false)
                val alert = AlertDialog.Builder(this)
                alert.setTitle("Wrong Click!")
                alert.setMessage("You clicked button ${ui.clickBtn3.text}, but it's wrong. Please keep clicking button $nextClickNumber")
                alert.show()
            }
        }
        //button 4
        ui.clickBtn4.setOnClickListener {
            val attemptDate = LocalDate.now(ZoneId.of("AET"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            val attemptTime = LocalTime.now(ZoneId.of("AET"))
            attempt.attemptDate = attemptDate
            attempt.btnOrderArray?.add(4)
            attempt.btnTimeArray?.add(attemptTime.toString())
            //if it's correct click
            if (nextClickNumber == 4){
                attempt.btnCorrectArray?.add(true)
                ui.clickBtn4.textSize = 30F
                //if more than 4 buttons
                if (btnNumber > 4){
                    nextClickNumber = 5
                    ui.clickBtn5.textSize = 55F
                    ui.nextNumberText.text = "5"
                }
                // if only 4 buttons
                else{
                    // if it is final round
                    if (currentRound == totalRound ){
                        currentRound = 1
                        val alert = AlertDialog.Builder(this)
                        alert.setTitle("Congratulations!")
                            .setMessage("You've completed all rounds!")
                            // TODO: give 3 buttons for users, go to history page,  go to next same-setting-round, or/and change settings
                            .show()
                    }
                    // if it is not final round
                    else{
                        val alert = AlertDialog.Builder(this)
                        alert.setTitle("Congratulations!")
                            .setMessage("you've completed $currentRound round(s)! Click OK start the next round.")
                            .setPositiveButton("OOK") { _: DialogInterface, _: Int
                                ->
                                currentRound += 1
                                nextClickNumber = 1
                                ui.nextNumberText.text = "1"
                                ui.clickBtn4.textSize = 30F
                                ui.clickBtn1.textSize = 55F
                                changeRoundText()
                            }
                            .setCancelable(false)
                            .show()
                    }
                }
            }
            //if this click(button 3) is wrong
            else{
                attempt.btnCorrectArray?.add(false)
                val alert = AlertDialog.Builder(this)
                alert.setTitle("Wrong Click!")
                alert.setMessage("You clicked button ${ui.clickBtn4.text}, but it's wrong. Please keep clicking button $nextClickNumber")
                alert.show()
            }
        }
        //click button 5
        ui.clickBtn5.setOnClickListener {
            val attemptDate = LocalDate.now(ZoneId.of("AET"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            val attemptTime = LocalTime.now(ZoneId.of("AET"))
//                    .format(DateTimeFormatter.ofPattern("HH:mm:ss"))
            attempt.attemptDate = attemptDate
            attempt.btnOrderArray?.add(5)
            attempt.btnTimeArray?.add(attemptTime.toString())
            //if it's correct click
            if (nextClickNumber == 5){
                attempt.btnCorrectArray?.add(true)
                ui.clickBtn5.textSize = 30F

                // if it is final round
                if (currentRound == totalRound ){
                    currentRound = 1
                    val alert = AlertDialog.Builder(this)
                    alert.setTitle("Congratulations!")
                        .setMessage("You've completed all rounds!")
                        // TODO: give 3 buttons for users, go to history page,  go to next same-setting-round, or/and change settings
                        .show()
                }
                // if it is not final round
                else{
                    val alert = AlertDialog.Builder(this)
                    alert.setTitle("Congratulations!")
                        .setMessage("you've completed $currentRound round(s)! Click OK start the next round.")
                        .setPositiveButton("OOK") { _: DialogInterface, _: Int
                            ->
                            currentRound += 1
                            nextClickNumber = 1
                            ui.nextNumberText.text = "1"
                            ui.clickBtn5.textSize = 30F
                            ui.clickBtn1.textSize = 55F
                            changeRoundText()
                        }
                        .setCancelable(false)
                        .show()
                }

            }
            //if this click(button 5) is wrong
            else{
                attempt.btnCorrectArray?.add(false)
                val alert = AlertDialog.Builder(this)
                alert.setTitle("Wrong Click!")
                alert.setMessage("You clicked button ${ui.clickBtn5.text}, but it's wrong. Please keep clicking button $nextClickNumber")
                alert.show()
            }
        }
    } // OnCreate End
// pile of functions
    //fun: get a coordinate for a button
    private fun getAnUsefulCoordinate():Coordinate{
        var result = Coordinate(-1, -1)
        var a0 : Int
        var b0 : Int
        fun getARandomNumber(){
            a0 = (Math.random() * 700).toInt()
            b0 = (Math.random() * 700).toInt()
            val ab = Coordinate(a0, b0)
            if (checkOverlap(ab)){
                btnStack.add(ab)
                result = ab
            }
            else{
                getARandomNumber()
            }
        }
        getARandomNumber()
        return result
    }
    //fun: check whether button coordinates are overlaping
    private fun checkOverlap(co : Coordinate):Boolean{
        var check = true
        for (index in btnStack){
            val dx = co.axisX - index.axisX
            val dy = co.axisY - index.axisY
            if (dx * dx + dy * dy <= 30000){
                check = false
            }
        }
        return check
    }



    //pile of inner classes
    inner class Coordinate(
        var axisX : Int,
        var axisY : Int,
    )
}
//public functions
//fun: alert template
fun alert(){

}


