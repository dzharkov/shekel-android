package org.eblans.shekel

import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_purchase.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.rx1.awaitSingle
import org.jetbrains.anko.find
import java.util.concurrent.TimeUnit

class PurchaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase)

        find<Button>(R.id.purchase_view_button).setOnClickListener {
            findPurchase()
        }
    }

    private fun findPurchase() {
        showProgress(true)
        launch(UI) {
            delay(2, TimeUnit.SECONDS)
            val shekelApi = buildShekelApi()
            val purchase =
                    shekelApi
                            .findPurchase(purchase_id_edit.text.toString().toInt())
                            .awaitSingle()
            purchase_to_string.text = purchase.toString()

            showProgress(false)
        }
    }

    private fun showProgress(show: Boolean) {
        purchase_progress.visibility = if (show) View.VISIBLE else View.GONE
        purchase_view.visibility = if (show) View.GONE else View.VISIBLE
    }

}
