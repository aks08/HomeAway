package kot.akshay.com.homeaway.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import kot.akshay.com.homeaway.utils.Constants
import kot.akshay.com.homeaway.utils.hideAndShowKeyboard
import kot.akshay.com.homeaway.utils.isInternetOn
import kot.akshay.com.homeaway.utils.showSnackbar
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Activity to take input from user
 */

class SearchActivity : AppCompatActivity(), View.OnClickListener, SearchView.OnQueryTextListener {

    private var searchQuery: String? = null  // input from user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(kot.akshay.com.homeaway.R.layout.activity_main)
       initUiComponents()
    }

    private fun initUiComponents(){
        id_bt_search?.setOnClickListener(this)
        id_sv_search?.setOnQueryTextListener(this)
        id_bt_search?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                id_bt_search?.performClick()

            }
            return@setOnEditorActionListener false
        }
    }

    override fun onResume() {
        super.onResume()
        id_sv_search?.setQuery("", false)
        hideAndShowKeyboard(this)
       }

    override fun onClick(v: View?) {
        submitQuery((id_sv_search?.query).toString())
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        submitQuery(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    /**
     * query : user input to be submitted
     */
    private fun submitQuery(query: String?) {
        if (isInternetOn(this)) {
            if (!TextUtils.isEmpty(query))
                if (query?.length!! > 1) {
                    searchQuery = query
                    id_sv_search?.clearFocus()
                    val i = Intent(this, PlaceListingActivity::class.java)
                    i.putExtra(Constants.SEARCH_QUERY, searchQuery)
                    startActivity(i)
                } else {
                    showSnackbar(id_cl_container,Constants.SEARCH_PLACE)
                }
        }else{
           showSnackbar(id_cl_container,Constants.NO_INTERNET)
        }
    }
}
