package co.edu.usbcali.simulador.products

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import co.edu.usbcali.simulador.R
import co.edu.usbcali.simulador.database.AppDatabase
import co.edu.usbcali.simulador.database.account.Account
import co.edu.usbcali.simulador.database.user.User

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ProductsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ProductsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductsFragment : Fragment(), AdapterView.OnItemClickListener {


    private var DB: AppDatabase? = null
    private var loggedUser: User? = null

    private var listView: ListView? = null
    private var listItems: List<Account>? = null
    private var fab: FloatingActionButton? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB = AppDatabase.getAppDatabase(context)
        if (arguments != null) {
            loggedUser = arguments.getParcelable("loggedUser")
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val accountDao = DB!!.accountDao()
        this.listItems = accountDao.getAllByUserId(loggedUser!!.id)
        val arrayAdapter = AccountAdapter()
        this.listView = view!!.findViewById<ListView>(R.id.listViewProducts)
        this.listView!!.adapter = arrayAdapter
        listView!!.onItemClickListener = this
        fab = view!!.findViewById(R.id.floatingButtonProducts)
        fab!!.setOnClickListener({
            if (loggedUser != null){
                val bundle = Bundle()
                bundle.putParcelable("loggedUser", loggedUser)
                val createProductFragment = CreateProductFragment.Companion.newInstance(loggedUser!!)
                val fragmentManager = activity.supportFragmentManager
                val transaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainer, createProductFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        })
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            //throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Toast.makeText(context, listItems!![p2].id.toString(), Toast.LENGTH_SHORT).show()
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private var loggedUser = null

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param loggedUser Parameter 1.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(loggedUser: User): ProductsFragment {
            val fragment = ProductsFragment()
            val args = Bundle()
            args.putParcelable("loggedUser", loggedUser)
            fragment.arguments = args
            return fragment
        }
    }

    inner class AccountAdapter : ArrayAdapter<Account>(this.context,
            android.R.layout.simple_expandable_list_item_2, android.R.id.text1, listItems) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            val view = super.getView(position, convertView, parent)
            val tv1 = view!!.findViewById<TextView>(android.R.id.text1)
            val tv2 = view!!.findViewById<TextView>(android.R.id.text2)
            val account = getItem(position)
            val accountType = if (account.type == 1) "Ahorros" else "Corriente"
            tv1.text = "Cuenta " + accountType + " #" + account.id.toString()
            tv2.text = "Saldo neto: $" + account.netBalance.toString()
            return view
        }
    }
}

