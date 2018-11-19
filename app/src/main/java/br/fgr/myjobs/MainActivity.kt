package br.fgr.myjobs

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var customAdapter: CustomAdapter
    private lateinit var list: MutableList<CustomItem>
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val settings: FirebaseFirestoreSettings = FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build()
        db.firestoreSettings = settings

        val viewManager = LinearLayoutManager(this)
        list = ArrayList()
        customAdapter = CustomAdapter(list)

        rv_list.apply {
            layoutManager = viewManager
            setHasFixedSize(true)
            adapter = customAdapter
        }
    }

    override fun onResume() {
        super.onResume()

        db.collection("jobs")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        list.clear()
                        for (document in task.result!!) {
                            list.add(
                                    CustomItem(document.data["timestamp"] as? Timestamp,
                                            document.data["title"] as? String,
                                            document.data["description"] as? String,
                                            document.data["result"] as? String,
                                            document.data["status"] as? Long
                                    )
                            )
                            Log.d(TAG, document.toString())
                        }
                        customAdapter.notifyDataSetChanged()
                    } else {
                        Log.w(TAG, "Error", task.exception)
                    }
                }
    }

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }
}
